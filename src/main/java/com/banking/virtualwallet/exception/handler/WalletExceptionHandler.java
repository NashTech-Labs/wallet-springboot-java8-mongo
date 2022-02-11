package com.banking.virtualwallet.exception.handler;

import com.banking.virtualwallet.exception.*;
import com.fabhotel.assignment.virtualwallet.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class WalletExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(WalletExceptionHandler.class);

    @ExceptionHandler(CustomerAlreadyHasWalletException.class)
    public ResponseEntity<String> handle(CustomerAlreadyHasWalletException ex, HttpServletRequest request, HttpServletResponse response) {
        logger.info("Customer already wallet");

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(WalletIdDoesNotExistException.class)
    public ResponseEntity<String> handle(WalletIdDoesNotExistException ex, HttpServletRequest request, HttpServletResponse response) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CustomerDoesNotExistException.class)
    public ResponseEntity<String> handle(CustomerDoesNotExistException ex, HttpServletRequest request, HttpServletResponse response) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InsufficientBalanceInWalletException.class)
    public ResponseEntity<String> handle(InsufficientBalanceInWalletException ex, HttpServletRequest request, HttpServletResponse response) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CustomerAlreadyCreatedException.class)
    public ResponseEntity<String> handle(CustomerAlreadyCreatedException ex, HttpServletRequest request, HttpServletResponse response) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handle(ConstraintViolationException ex, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public ResponseEntity<String> handle(PasswordDoesNotMatchException ex, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);

    }

}
