package com.booksapp.booksapp.service;

import com.booksapp.booksapp.model.dto.OrderDTO;
import com.booksapp.booksapp.model.persistence.CustomerEntity;
import com.booksapp.booksapp.model.persistence.OrderEntity;
import com.booksapp.booksapp.model.persistence.SellerEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderEntity createOrder(OrderDTO orderDTO, CustomerEntity customerEntity);
    List<OrderEntity> getOrdersByCustomerId(long customerId);
    OrderEntity getOrderById(long customerId, long orderId);
    void cancelOrder(long customerId, long orderId);

}
