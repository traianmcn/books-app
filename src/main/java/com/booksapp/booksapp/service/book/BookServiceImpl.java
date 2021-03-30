package com.booksapp.booksapp.service.book;

import com.booksapp.booksapp.dao.BookRepository;
import com.booksapp.booksapp.exceptions.BookNotFoundException;
import com.booksapp.booksapp.exceptions.bookCategoryExceptions.BookCategoryNotFoundException;
import com.booksapp.booksapp.model.persistence.BookCategoryEntity;
import com.booksapp.booksapp.model.persistence.BookEntity;
import com.booksapp.booksapp.model.persistence.SellerEntity;
import com.booksapp.booksapp.service.seller.SellerServiceImpl;
import com.booksapp.booksapp.service.bookCategory.BookCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        BookCategoryEntity category = bookCategoryService.getCategoryById(sellerId, categoryId);
        if (category == null) {
                throw new BookCategoryNotFoundException("The category with id " + categoryId + " doest not exist.");
            }
            newBook.setBookCategoryEntity(category);
            bookRepository.save(newBook);
            return newBook;
    }

    @Override
    public  List<BookEntity> getAllBooksByCategory(long sellerId, long categoryId) {
        sellerService.getSellerById(sellerId);
        BookCategoryEntity category = bookCategoryService.getCategoryById(sellerId, categoryId);
        return category.getBookEntities();
    }

    @Override
    public BookEntity getBookById(long sellerId, long categoryId, long bookId) {
//        List<BookEntity> books = getAllBooksByCategory(sellerId, categoryId);
//        BookEntity bookEntity = new BookEntity();
//        for (BookEntity book : books) {
//            if (book.getId() == bookId) {
//                bookEntity = book;
//            }
//            else {
//                throw new BookNotFoundException("The book with id " + bookId + " does not exist");
//            }
//        }
//        return bookEntity;
        sellerService.getSellerById(sellerId);
        Optional<BookEntity> book = this.bookRepository.findBookById(sellerId, categoryId, bookId);
        if (book.isEmpty()) {
            throw new BookNotFoundException("The book with id " + bookId + " doest not exist");
        }
        return book.get();
    }

    @Override
    public void deleteBook(long sellerId, long categoryId, long bookId) {
        Optional<BookEntity> book = this.bookRepository.findBookById(sellerId, categoryId, bookId);
        if (book.isEmpty()) {
            throw new BookNotFoundException("The book with id " + bookId + " doest not exist");
        }

        this.bookRepository.deleteById(bookId);
    }

    @Override
    public BookEntity updateBook(long sellerId, long categoryId, long bookId, BookEntity updatedBook) {
        sellerService.getSellerById(sellerId);
        Optional<BookEntity> book = this.bookRepository.findBookById(sellerId, categoryId, bookId);

        if (book.isEmpty()) {
            throw new BookNotFoundException("The book with id " + bookId + " doest not exist");
        }

        updatedBook.setBookCategoryEntity(book.get().getBookCategoryEntity());
        this.bookRepository.delete(book.get());
        return this.bookRepository.save(updatedBook);
    }
}
