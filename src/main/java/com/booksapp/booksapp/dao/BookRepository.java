package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.BookCategoryEntity;
import com.booksapp.booksapp.model.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query(value = "SELECT * FROM books_app.books b " +
            "inner join books_app.categories c on c.id = b.category_id " +
            "where b.id = :bookId and c.seller_id = :sellerId and c.id = :categoryId", nativeQuery = true)
    Optional<BookEntity> findBookById(long sellerId, long categoryId, long bookId);
}
