package com.booksapp.booksapp.service;

import com.booksapp.booksapp.dao.CustomerRepository;
import com.booksapp.booksapp.exceptions.customerException.CustomerNotFoundException;
import com.booksapp.booksapp.model.persistence.CustomerEntity;
import com.booksapp.booksapp.model.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private UserServiceImpl userService;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, UserServiceImpl userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    @Override
    public CustomerEntity createCustomer(CustomerEntity newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity getCustomerById(long id) {
        if (customerRepository.findById(id).isEmpty()) {
            throw new CustomerNotFoundException("The customer with id " + id + " doest not exist");
        }
        return customerRepository.findById(id).get();
    }

    @Override
    public void deleteCustomer(long id) {
        CustomerEntity customerEntity = getCustomerById(id);
        UserEntity userEntity = customerEntity.getUserEntity();
        userService.deleteUser(userEntity.getId());

    }

    @Override
    public CustomerEntity updateCustomer(long id, CustomerEntity updatedCustomer) {
        CustomerEntity customerEntity = getCustomerById(id);
        UserEntity userEntity = customerEntity.getUserEntity();
        customerRepository.deleteById(id);
        updatedCustomer.setUserEntity(userEntity);
         return customerRepository.save(updatedCustomer);
    }

    @Override
    public CustomerEntity getCustomerByEmail(String email) {
        if (customerRepository.findByEmail(email).isEmpty()) {
            throw new CustomerNotFoundException("The customer with email " + email + " doest not exist");
        }
        return customerRepository.findByEmail(email).get();
    }
}
