package com.fabhotel.assignment.virtualwallet.exception;

public class PasswordDoesNotMatchException extends Exception {

    public PasswordDoesNotMatchException(String email) {
        super("Password do not match for user " + email);
    }
}
