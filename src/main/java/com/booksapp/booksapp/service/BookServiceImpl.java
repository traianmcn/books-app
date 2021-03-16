package com.booksapp.booksapp.service;

import com.booksapp.booksapp.dao.BookRepository;
import com.booksapp.booksapp.exceptions.BookNotFoundException;
import com.booksapp.booksapp.exceptions.bookCategoryExceptions.BookCategoryNotFoundException;
import com.booksapp.booksapp.model.persistence.BookCategoryEntity;
import com.booksapp.booksapp.model.persistence.BookEntity;
import com.booksapp.booksapp.model.persistence.SellerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private SellerServiceImpl sellerService;
    private BookCategoryServiceImpl bookCategoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, SellerServiceImpl sellerService, BookCategoryServiceImpl bookCategoryService) {
        this.bookRepository = bookRepository;
        this.sellerService = sellerService;
        this.bookCategoryService = bookCategoryService;
    }

    @Override
    public BookEntity createBook(long sellerId, long categoryId, BookEntity newBook) {
        SellerEntity seller = sellerService.getSellerById(sellerId);
        for (BookCategoryEntity category : seller.getBookCategories()) {
            if (category.getId() != categoryId) {
                throw new BookCategoryNotFoundException("The category with id " + categoryId + " doest not exist.");
            }
            else {
                newBook.setBookCategoryEntity(category);
                bookRepository.save(newBook);
            }
        }
        return newBook;

    }

    @Override
    public  List<BookEntity> getAllBooksByCategory(long sellerId, long categoryId) {
        BookCategoryEntity category = bookCategoryService.getCategoryById(sellerId, categoryId);

        return category.getBookEntities();
    }

    @Override
    public BookEntity getBookById(long sellerId, long categoryId, long bookId) {
        List<BookEntity> books = getAllBooksByCategory(sellerId, categoryId);
        BookEntity bookEntity = new BookEntity();
        for (BookEntity book : books) {
            if (book.getId() == bookId) {
                bookEntity = book;
            }
            else {
                throw new BookNotFoundException("The book with id " + bookId + " does not exist");
            }
        }
        return bookEntity;
    }

    @Override
    public void deleteBook(long seller, long id) {

    }

    @Override
    public BookEntity updateBook(long sellerId, long id, BookEntity updatedBook) {
        return null;
    }
}
