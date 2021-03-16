package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

    @Query(value = "SELECT SellerEntity s FROM books_app.sellers sel WHERE s.email = :sellerEmail", nativeQuery = true)
    SellerEntity findSellerByEmail(String sellerEmail);
}
