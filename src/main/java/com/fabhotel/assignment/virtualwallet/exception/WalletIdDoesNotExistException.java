package com.fabhotel.assignment.virtualwallet.exception;

public class WalletIdDoesNotExistException extends  Exception {
    public WalletIdDoesNotExistException(String walletId) {
        super("Wallet with walletId : " +  walletId +" does not exist");
    }
}
