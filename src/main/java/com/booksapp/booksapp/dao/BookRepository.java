package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
