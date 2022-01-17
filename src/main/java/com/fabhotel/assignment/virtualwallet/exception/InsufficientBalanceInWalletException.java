package com.fabhotel.assignment.virtualwallet.exception;

public class InsufficientBalanceInWalletException extends Exception {

    public InsufficientBalanceInWalletException(String walletId) {
        super("Wallet with walletId : "+ walletId +" does not have sufficient balance");
    }
}
