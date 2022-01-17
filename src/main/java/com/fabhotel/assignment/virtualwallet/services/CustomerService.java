package com.fabhotel.assignment.virtualwallet.services;

import com.fabhotel.assignment.virtualwallet.exception.CustomerAlreadyCreatedException;
import com.fabhotel.assignment.virtualwallet.exception.CustomerDoesNotExistException;
import com.fabhotel.assignment.virtualwallet.models.Customer;

public interface CustomerService {

    /**
     * Create a new wallet for a user - I assume user is already in the system (in my case Customer table, in H2 database)
     * @param customer : Customer which has to be created
     * @return : A newly created Customer object
     * @throws CustomerAlreadyCreatedException - if customer exists already
     */
    public Customer createCustomer (Customer customer) throws CustomerAlreadyCreatedException;

    /**
     * Create a new wallet for a user - I assume user is already in the system (in my case Customer table, in H2 database)
     * @param email : email of customer
     * @return : A newly created wallet object
     * @throws CustomerDoesNotExistException - if customer doest not exist
     */
    public Customer getCustomerEmail(String email) throws CustomerDoesNotExistException;

}
