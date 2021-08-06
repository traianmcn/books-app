package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "select * from books_app.customers c " +
            "inner join books_app.users u on c.user_id = u.id " +
            "where u.email = :userEmail", nativeQuery = true)
    Optional<CustomerEntity> findByEmail(String userEmail);

//    @Query( "" +
//            "select case when count(c) > 0 then " +
//            "true else false end " +
//            "from books_app.customers c " +
//            "inner join books_app.users u on c.user_id = u.id " +
//            "where u.email = ?1"
//    )
//    Boolean existByEmail(String email);

}
