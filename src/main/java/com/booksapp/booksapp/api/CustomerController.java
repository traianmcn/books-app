package com.booksapp.booksapp.api;

import com.booksapp.booksapp.exceptions.customerException.CustomerNotFoundException;
import com.booksapp.booksapp.model.persistence.CustomerEntity;
import com.booksapp.booksapp.model.persistence.UserEntity;
import com.booksapp.booksapp.security.JWTProvider;
import com.booksapp.booksapp.service.CustomerServiceImpl;
import com.booksapp.booksapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private CustomerServiceImpl customerService;
    private JWTProvider jwtProvider;
    private UserServiceImpl userService;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService, JWTProvider jwtProvider, UserServiceImpl userService) {
        this.customerService = customerService;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerEntity> register (@RequestBody CustomerDTO customerDTO) {

    }

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
        List<CustomerEntity> customers = customerService.getAllCustomers();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable long id) {
        CustomerEntity customer = customerService.getCustomerById(id);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
