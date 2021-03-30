package com.booksapp.booksapp.service.book;

import com.booksapp.booksapp.model.persistence.BookCategoryEntity;
import com.booksapp.booksapp.model.persistence.BookEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    BookEntity createBook(long sellerId, long categoryId, BookEntity newBook);
    List<BookEntity> getAllBooksByCategory(long sellerId, long categoryId);
    BookEntity getBookById(long sellerId, long categoryId, long bookId);
    void deleteBook(long sellerId, long categoryId, long bookId);
    BookEntity updateBook(long sellerId, long categoryId, long bookId, BookEntity updatedBook);
}
