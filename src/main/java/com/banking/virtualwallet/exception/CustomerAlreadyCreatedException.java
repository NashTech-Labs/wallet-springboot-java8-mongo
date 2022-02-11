package com.banking.virtualwallet.exception;

import com.banking.virtualwallet.models.Customer;

public class CustomerAlreadyCreatedException extends Exception {

    public CustomerAlreadyCreatedException(Customer customer) {
        super("Customer already exists : " + customer.getEmail());
    }
}
