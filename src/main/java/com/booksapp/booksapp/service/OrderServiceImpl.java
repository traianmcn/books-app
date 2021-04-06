package com.booksapp.booksapp.service;

import com.booksapp.booksapp.dao.BookRepository;
import com.booksapp.booksapp.dao.OrderRepository;
import com.booksapp.booksapp.exceptions.BookNotFoundException;
import com.booksapp.booksapp.exceptions.CancelOrderException;
import com.booksapp.booksapp.exceptions.OrderNotFoundException;
import com.booksapp.booksapp.exceptions.customerException.CustomerNotFoundException;
import com.booksapp.booksapp.model.dto.ContentDTO;
import com.booksapp.booksapp.model.dto.OrderDTO;
import com.booksapp.booksapp.model.persistence.BookEntity;
import com.booksapp.booksapp.model.persistence.CustomerEntity;
import com.booksapp.booksapp.model.persistence.OrderEntity;
import com.booksapp.booksapp.service.book.BookServiceImpl;
import com.booksapp.booksapp.service.customer.CustomerServiceImpl;
import com.booksapp.booksapp.service.seller.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private SellerServiceImpl sellerService;
    private BookServiceImpl bookService;
    private BookRepository bookRepository;
    private CustomerServiceImpl customerService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, SellerServiceImpl sellerService, BookServiceImpl bookService, BookRepository bookRepository, CustomerServiceImpl customerService) {
        this.orderRepository = orderRepository;
        this.sellerService = sellerService;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
        this.customerService = customerService;
    }

    @Override
    public OrderEntity createOrder(OrderDTO orderDTO, CustomerEntity customerEntity) {
        if (customerEntity == null) {
            throw new CustomerNotFoundException("Customer not found.");
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerEntity(customerEntity);
        orderEntity.setInfo(orderDTO.getInfo());

        double value = Double.MIN_VALUE;
        List<BookEntity> books = new ArrayList<>();
        for (ContentDTO content : orderDTO.getContent()) {
            Optional<BookEntity> bookEntity = bookRepository.findBookBySellerIdCategoryIdBookId(content.getSellerId(),
                    content.getCategoryId(), content.getBookId());
            if (bookEntity.isPresent()) {
                value = value + bookEntity.get().getPrice();
                books.add(bookEntity.get());
            }
            else {
                throw new BookNotFoundException("The books with sellerId " + content.getSellerId() + ", categoryId " +
                        content.getCategoryId() + ", and bookId " + content.getBookId() + " does not exist");
            }
        }
        orderEntity.setContent(books);
//        System.out.println(books.toString());
        orderEntity.setValue(value);

        return this.orderRepository.save(orderEntity);
    }

    @Override
    public List<OrderEntity> getOrdersByCustomerId(long customerId) {
        this.customerService.getCustomerById(customerId);
        return this.orderRepository.findOrdersByCustomerId(customerId).get();
    }

    @Override
    public OrderEntity getOrderById(long customerId, long orderId) {
        Optional<OrderEntity> order = this.orderRepository.getOrderById(customerId, orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("The order with id " + orderId + " cannot be found.");
        }
        return order.get();
    }

    @Override
    public void cancelOrder(long customerId, long orderId) {
        OrderEntity order = getOrderById(customerId, orderId);
        long orderTime = order.getCreated_at().getTime();
        long now = System.currentTimeMillis();

        long diffMinutes = (now - orderTime)/ (60*1000);
        if (diffMinutes > 5) {
            throw new CancelOrderException("The order cannot be canceled because it has been created more than five minutes ago.");
        }
        this.orderRepository.delete(order);
    }
}
