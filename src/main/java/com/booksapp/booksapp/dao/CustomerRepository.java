package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "SELECT CustomerEntity c FROM books_app.customers cust WHERE c.email = :userEmail", nativeQuery = true)
    Optional<CustomerEntity> findByEmail(String userEmail);
}
