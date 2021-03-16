package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.BookCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategoryEntity, Long> {

//    @Query(value = "SELECT BookCategoryEntity bookCateg FROM books_app.categories c WHERE bookCateg.seller_id = :id", nativeQuery = true)
//    Optional<BookCategoryEntity> findCategoryById(long id);
}
