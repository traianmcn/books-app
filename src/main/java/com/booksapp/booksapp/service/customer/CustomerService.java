package com.booksapp.booksapp.service.customer;

import com.booksapp.booksapp.model.persistence.CustomerEntity;
import com.booksapp.booksapp.model.persistence.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    CustomerEntity createCustomer(CustomerEntity newCustomer);
    List<CustomerEntity> getAllCustomers();
    CustomerEntity getCustomerById(long id);
    void deleteCustomer(long id);
    CustomerEntity updateCustomer(long id, CustomerEntity updatedCustomer);
    CustomerEntity getCustomerByEmail(String email);
}
