package com.fabhotel.assignment.virtualwallet.controller;

import com.fabhotel.assignment.virtualwallet.exception.CustomerAlreadyHasWalletException;
import com.fabhotel.assignment.virtualwallet.exception.CustomerDoesNotExistException;
import com.fabhotel.assignment.virtualwallet.exception.InsufficientBalanceInWalletException;
import com.fabhotel.assignment.virtualwallet.models.ServiceResponse;
import com.fabhotel.assignment.virtualwallet.models.Wallet;
import com.fabhotel.assignment.virtualwallet.models.WalletTransaction;
import com.fabhotel.assignment.virtualwallet.services.WalletService;
import com.fabhotel.assignment.virtualwallet.exception.WalletIdDoesNotExistException;
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

        walletService.createWallet(customerId);
        response.setDescription("Wallet created successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/wallet/{customerId}/deposit/{amount}")
    public ResponseEntity<ServiceResponse> deposit(@PathVariable("customerId") String customerId,
                                                   @PathVariable("amount") float amount) throws WalletIdDoesNotExistException {


        ServiceResponse response = new ServiceResponse();
        logger.info("Received request to deposit amount to wallet by : {}", customerId);
        Wallet ac = walletService.depositToWallet(customerId, amount, "DEPOSIT");
        response.setDescription("Amount " + amount + " deposited successfully!!");
        response.setData(ac);
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
        List<WalletTransaction> lb = walletService.getStatement(walletId);
        response.setDescription("Statement fetched successfully!!");
        logger.info("object: {}", lb.get(0));
        response.setData(lb);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
