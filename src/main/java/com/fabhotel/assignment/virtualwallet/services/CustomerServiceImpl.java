package com.fabhotel.assignment.virtualwallet.services;

import com.fabhotel.assignment.virtualwallet.exception.CustomerAlreadyCreatedException;
import com.fabhotel.assignment.virtualwallet.exception.CustomerDoesNotExistException;
import com.fabhotel.assignment.virtualwallet.exception.PasswordDoesNotMatchException;
import com.fabhotel.assignment.virtualwallet.models.Customer;
import com.fabhotel.assignment.virtualwallet.models.CustomerCredentials;
import com.fabhotel.assignment.virtualwallet.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) throws CustomerAlreadyCreatedException {

        logger.info("Received a request to create new customer : {}", customer.getEmail());
        if (customerRepository.findCustomerByEmail(customer.getEmail()).isPresent())
            throw new CustomerAlreadyCreatedException(customer);
        else
            return customerRepository.save(customer);

    }

    @Override
    public Customer getCustomerEmail(String email) throws CustomerDoesNotExistException {
        logger.info("Received a request to get customer Details : {}", email);
        return customerRepository.findCustomerByEmail(email).orElseThrow(() -> new CustomerDoesNotExistException(email));
    }

    @Override
    public void signIn(CustomerCredentials credentials) throws CustomerDoesNotExistException, PasswordDoesNotMatchException {
        logger.info("Received a request for login by user : {}", credentials.getEmail());
        Customer customer = customerRepository.findCustomerByEmail(credentials.getEmail()).orElseThrow(() -> new CustomerDoesNotExistException(credentials.getEmail()));
        if (customer.getPassword().equals(credentials.getPassword()))
            logger.info("Sign in successfully");
        else
            throw new PasswordDoesNotMatchException(credentials.getEmail());
    }
}
