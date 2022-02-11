package com.banking.virtualwallet.controller;

import com.banking.virtualwallet.exception.CustomerAlreadyHasWalletException;
import com.banking.virtualwallet.exception.CustomerDoesNotExistException;
import com.banking.virtualwallet.exception.InsufficientBalanceInWalletException;
import com.banking.virtualwallet.models.ServiceResponse;
import com.banking.virtualwallet.models.Wallet;
import com.banking.virtualwallet.models.WalletTransaction;
import com.banking.virtualwallet.services.WalletService;
import com.banking.virtualwallet.exception.WalletIdDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseStatus(value = HttpStatus.OK)
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    WalletService walletService;

    // Create a new wallet for a user. Constraint : A user can have only one wallet
    @PostMapping("/api/wallet/{customerId}")
    public ResponseEntity<ServiceResponse> createWallet(@PathVariable("customerId") String customerId) throws CustomerAlreadyHasWalletException, CustomerDoesNotExistException, WalletIdDoesNotExistException {

        logger.info("Received request to create new wallet by customer : {}", customerId);
        ServiceResponse response = new ServiceResponse();

        Wallet wallet = walletService.createWallet(customerId);
        response.setDescription("Wallet created successfully!");
        response.setData("walletId " + wallet.getWalletId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/api/wallet/{customerId}/deposit/{amount}")
    public ResponseEntity<ServiceResponse> deposit(@PathVariable("customerId") String customerId,
                                                   @PathVariable("amount") float amount) throws WalletIdDoesNotExistException {


        ServiceResponse response = new ServiceResponse();
        logger.info("Received request to deposit amount to wallet by : {}", customerId);
        Wallet wallet = walletService.depositToWallet(customerId, amount, "DEPOSIT");
        response.setDescription("Amount " + amount + " deposited successfully!!");
        response.setData(wallet);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/api/wallet/{fromWalletId}/transfer/{toWalletId}/amount/{amount}")
    public ResponseEntity<ServiceResponse> transfer(@PathVariable("fromWalletId") String fromWalletId,
                                                    @PathVariable("toWalletId") String toWalletId,
                                                    @PathVariable("amount") float amount) throws InsufficientBalanceInWalletException, WalletIdDoesNotExistException {

        ServiceResponse response = new ServiceResponse();
        logger.info("Received request to transfer amount to walletId {} by : {}", toWalletId, fromWalletId);
        walletService.transferToWallet(fromWalletId, toWalletId, amount);
        response.setDescription("Amount " + amount + " transferred successfully!!");
        response.setData(amount);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/api/wallet/{walletId}/transactions")
    public ResponseEntity<ServiceResponse> getStatement(@PathVariable("walletId") String walletId
    ) throws WalletIdDoesNotExistException {

        ServiceResponse response = new ServiceResponse();
        logger.info("Received request to view transaction statement for walletId : {}", walletId);
        List<WalletTransaction> walletTransactions = walletService.getStatement(walletId);
        response.setDescription("Statement fetched successfully!!");
        response.setData(walletTransactions);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
