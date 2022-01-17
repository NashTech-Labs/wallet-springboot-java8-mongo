package com.fabhotel.assignment.virtualwallet.exception;

import com.fabhotel.assignment.virtualwallet.models.Customer;

public class CustomerAlreadyCreatedException extends Exception {

    public CustomerAlreadyCreatedException(Customer customer) {
        super("Customer already exists : " + customer.getEmail());
    }
}
