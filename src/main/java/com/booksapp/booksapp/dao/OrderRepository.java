package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.CustomerEntity;
import com.booksapp.booksapp.model.persistence.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT * FROM books_app.orders o WHERE o.customer_id = :customerId", nativeQuery = true)
    Optional<List<OrderEntity>> findOrdersByCustomerId(long customerId);

}
