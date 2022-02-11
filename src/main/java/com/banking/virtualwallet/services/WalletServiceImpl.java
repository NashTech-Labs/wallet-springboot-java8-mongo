package com.banking.virtualwallet.services;

import com.banking.virtualwallet.exception.CustomerAlreadyHasWalletException;
import com.banking.virtualwallet.exception.CustomerDoesNotExistException;
import com.banking.virtualwallet.exception.InsufficientBalanceInWalletException;
import com.banking.virtualwallet.constant.Constants;
import com.banking.virtualwallet.exception.WalletIdDoesNotExistException;
import com.banking.virtualwallet.models.Customer;
import com.banking.virtualwallet.models.Wallet;
import com.banking.virtualwallet.models.WalletTransaction;
import com.banking.virtualwallet.repository.CustomerRepository;
import com.banking.virtualwallet.repository.WalletRepository;
import com.banking.virtualwallet.repository.WalletTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("walletService")
public class WalletServiceImpl implements WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    WalletRepository walletRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    WalletTransactionRepository walletTransactionRepository;

    @Override
    public Wallet createWallet(String customerId) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException, WalletIdDoesNotExistException {

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerDoesNotExistException(customerId));

        if (!walletRepository.findById(customerId).isPresent()) {
            Wallet newWallet = new Wallet();
            newWallet.setWalletId(customerId);
            newWallet.setBalance(0);
            logger.info("Creating wallet for customer {}", customerId);
            return walletRepository.save(newWallet);

        } else {
            throw new CustomerAlreadyHasWalletException(customer);
        }

    }

    /**
     * Method is used to make entry into BankTransaction table for the appropriate transaction - deposit, withdrawl or transfer
     *
     * @param amount      : Amount to be deposited || withdrawn || transferred
     * @param postBalance : Balance in account after transaction has occurred
     * @param description : Custom String description associated with deposit || withdrawl || transfer
     * @param wallet
     */
    private void makeEntryInTransaction(String typeOfTransaction, float amount, float postBalance, String description, Wallet wallet) {
        WalletTransaction walletTransaction = new WalletTransaction(typeOfTransaction, new Date(), amount, postBalance, description, wallet.getWalletId());

        walletTransactionRepository.save(walletTransaction);
        logger.info("Transaction statement added for walletId: {} ", wallet.getWalletId());
    }

    @Override
    public Wallet depositToWallet(String walletId, Float amount, String type) throws WalletIdDoesNotExistException {

        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletIdDoesNotExistException(walletId));

        float currentBalance = wallet.getBalance();
        wallet.setBalance(currentBalance + amount);
        walletRepository.save(wallet);
        logger.info("Deposit the amount: {} succesfully to wallet: {}", amount, walletId);
        makeEntryInTransaction(Constants.DEPOSIT, amount, wallet.getBalance(), Constants.DEPOSIT_DESCRIPTION, wallet);
        return wallet;
    }

    @Override
    public void transferToWallet(String fromWalletId, String toWalletId, Float amount) throws WalletIdDoesNotExistException, InsufficientBalanceInWalletException {

        Wallet fromWallet = walletRepository.findById(fromWalletId).orElseThrow(() -> new WalletIdDoesNotExistException(fromWalletId));
        Wallet toWallet = walletRepository.findById(toWalletId).orElseThrow(() -> new WalletIdDoesNotExistException(toWalletId));

        if (fromWallet.getBalance() < amount) {
            throw new InsufficientBalanceInWalletException(fromWalletId);
        }

        // Withdraw
        float currentBalance = fromWallet.getBalance();
        fromWallet.setBalance(currentBalance - amount);

        logger.info("Amount : {} debited from walletId: {}", amount, fromWallet.getWalletId());

        walletRepository.save(fromWallet);

        String stringBuilder = "$" +
                amount +
                " transferred to walletId : " +
                toWallet.getWalletId();
        makeEntryInTransaction(Constants.TRANSFER, amount, fromWallet.getBalance(), stringBuilder, fromWallet);


        // deposit
        Wallet toAccount = this.depositToWallet(toWalletId, amount, "TRANSFER");
        String description = "$" +
                amount +
                " transferred from walletId : " +
                fromWalletId;
        makeEntryInTransaction(Constants.TRANSFER, amount, toAccount.getBalance(), description, toAccount);

    }

    @Override
    public List<WalletTransaction> getStatement(String walletId) throws WalletIdDoesNotExistException {

        return walletTransactionRepository.findWalletTransactionsByCustomerId(walletId).orElseThrow(() -> new WalletIdDoesNotExistException(walletId));

    }
}
