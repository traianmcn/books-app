package com.booksapp.booksapp.api;


import com.booksapp.booksapp.model.dto.OrderDTO;
import com.booksapp.booksapp.model.persistence.CustomerEntity;
import com.booksapp.booksapp.model.persistence.OrderEntity;
import com.booksapp.booksapp.security.JWTProvider;
import com.booksapp.booksapp.service.order.OrderServiceImpl;
import com.booksapp.booksapp.service.email.EmailSender;
import com.booksapp.booksapp.service.seller.SellerServiceImpl;
import com.booksapp.booksapp.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
//@PreAuthorize("hasRole('CUSTOMER')")
public class OrderController {

    private JWTProvider jwtProvider;
    private OrderServiceImpl orderService;
    private UserServiceImpl userService;
    private SellerServiceImpl sellerService;
    private EmailSender emailSender;

    @Autowired
    public OrderController(JWTProvider jwtProvider, OrderServiceImpl orderService, UserServiceImpl userService, SellerServiceImpl sellerService, EmailSender emailSender) {
        this.jwtProvider = jwtProvider;
        this.orderService = orderService;
        this.userService = userService;
        this.sellerService = sellerService;
        this.emailSender = emailSender;
    }

    @PostMapping("/register")
    public ResponseEntity<OrderEntity> register(@RequestBody OrderDTO orderDTO, @RequestHeader("Authorization") String jwt) {

        String email = this.jwtProvider.getSubjectFromJWT(jwt);
        CustomerEntity customerEntity = this.userService.getUserByEmail(email)
                .getCustomerEntity();

        OrderEntity orderEntity = this.orderService.createOrder(orderDTO, customerEntity);
        String message = " Your order has been placed. The value is " + orderEntity.getValue()+" lei." +
                "You have ordered: " + orderEntity.getContent().toString(); //TODO: implement a more friendly DTO for showing the order content
        emailSender.sendEmail(customerEntity.getUserEntity().getEmail(), message);

        return new ResponseEntity<>(orderEntity, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getOrdersByCustomerId(@RequestHeader("Authorization") String jwt) {
        String email = this.jwtProvider.getSubjectFromJWT(jwt);
        CustomerEntity customerEntity = this.userService.getUserByEmail(email).getCustomerEntity();
        List<OrderEntity> orders = orderService.getOrdersByCustomerId(customerEntity.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getOrderById(@RequestHeader("Authorization") String jwt, @PathVariable long orderId) {
        String email = this.jwtProvider.getSubjectFromJWT(jwt);
        CustomerEntity customerEntity = this.userService.getUserByEmail(email).getCustomerEntity();
        OrderEntity order = orderService.getOrderById(customerEntity.getId(), orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity cancelOrder(@RequestHeader("Authorization") String jwt, @PathVariable long orderId) {
        String email = this.jwtProvider.getSubjectFromJWT(jwt);
        CustomerEntity customerEntity = this.userService.getUserByEmail(email).getCustomerEntity();
        orderService.cancelOrder(customerEntity.getId(), orderId);

        return ResponseEntity.ok().build();
    }
}
