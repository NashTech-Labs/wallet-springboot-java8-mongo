package com.banking.virtualwallet.services;

import com.banking.virtualwallet.exception.CustomerAlreadyHasWalletException;
import com.banking.virtualwallet.exception.CustomerDoesNotExistException;
import com.banking.virtualwallet.exception.InsufficientBalanceInWalletException;
import com.banking.virtualwallet.exception.WalletIdDoesNotExistException;
import com.banking.virtualwallet.models.WalletTransaction;
import com.banking.virtualwallet.models.Wallet;

import java.util.List;

public interface WalletService {

    /**
     * Create a new wallet for a user - I assume user is already in the system
     * @param customerId : Customer which has requested creation of new wallet
     * @return : A newly created wallet object
     * @throws CustomerDoesNotExistException - if customer does not exist in the databse
     * @throws CustomerAlreadyHasWalletException - if customer already has a wallet. In our case one customer can only have one wallet
     */
    public Wallet createWallet(String customerId) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException, WalletIdDoesNotExistException;

    /**
     * A deposit transaction is one that increases the account balance with the given transaction amount.
     * @param walletId : Wallet to which deposit is to be made
     * @param amount : Amount to be deposited
     * @param type : Can be "DEPOSIT" || "WITHDRAW" || "TRANSFER"
     * @return Wallet details from which transaction was made
     * @throws WalletIdDoesNotExistException - if wallet does not exist
     */
    public Wallet depositToWallet(String walletId,Float amount, String type) throws WalletIdDoesNotExistException;

    /**
     * Method to Perform a transfer from one account to another account
     * @param fromWalletId : Wallet from which amount is to be transferred
     * @param toWalletId : Wallet to which amount is to transferred
     * @param amount : Amount to be transferred
     * @throws WalletIdDoesNotExistException - if wallet does not exist
     * @throws InsufficientBalanceInWalletException - if amount to be withdrawn > balance (Assumes you can also withdraw 1 cents)
     */
    public void transferToWallet(String fromWalletId, String toWalletId, Float amount) throws WalletIdDoesNotExistException, InsufficientBalanceInWalletException;

    /**
     * Method Return all transactions for an wallet
     * @param walletId : Wallet from which statement balance is requested
     * @return List of all bank transactions made
     * @throws WalletIdDoesNotExistException - if wallet does not exist
     */
    public List<WalletTransaction> getStatement(String walletId) throws WalletIdDoesNotExistException;

}
