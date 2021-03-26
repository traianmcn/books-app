package com.booksapp.booksapp.api;

import com.booksapp.booksapp.model.dto.CustomerDTO;
import com.booksapp.booksapp.model.persistence.CustomerEntity;
import com.booksapp.booksapp.model.persistence.Role;
import com.booksapp.booksapp.model.persistence.UserEntity;
import com.booksapp.booksapp.security.JWTProvider;
import com.booksapp.booksapp.security.PasswordConfig;
import com.booksapp.booksapp.service.CustomerServiceImpl;
import com.booksapp.booksapp.service.UserServiceImpl;
import com.booksapp.booksapp.service.email.EmailSender;
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
    private PasswordConfig passwordConfig;
    private EmailSender emailSender;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService, JWTProvider jwtProvider, UserServiceImpl userService, PasswordConfig passwordConfig, EmailSender emailSender) {
        this.customerService = customerService;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.passwordConfig = passwordConfig;
        this.emailSender = emailSender;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerEntity> register (@RequestBody CustomerDTO customerDTO) {
        UserEntity userEntity = new UserEntity(customerDTO.getEmail(), customerDTO.getPassword(), Role.CUSTOMER);
        userService.createUser(userEntity);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setAddress(customerDTO.getAddress());
        customerEntity.setName(customerDTO.getName());
        customerEntity.setPhoneNumber(customerDTO.getPhoneNumber());
        customerEntity.setUserEntity(userEntity);
        customerService.createCustomer(customerEntity);

        emailSender.sendEmail(customerDTO.getEmail(), "Your account has benn created.");

        return new ResponseEntity<>(customerEntity, HttpStatus.CREATED);

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

    @DeleteMapping("{id}")
    public ResponseEntity deleteCustomer(@PathVariable long id) {
        CustomerEntity customer = customerService.getCustomerById(id);
        String email = customer.getUserEntity().getEmail();
        customerService.deleteCustomer(id);
        emailSender.sendEmail(email, "Yout account has been deleted. Have a nice day!");

        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerEntity> updateCustomer (@PathVariable long id, @RequestBody CustomerEntity newCustomerEntity) {
        customerService.updateCustomer(id, newCustomerEntity);
        String email = newCustomerEntity.getUserEntity().getEmail();
        emailSender.sendEmail("email", "Your account has been updated. If it wasn't you, please reset your password.");

        return new ResponseEntity(newCustomerEntity, HttpStatus.ACCEPTED);
    }
}
