package com.booksapp.booksapp.api;

import com.booksapp.booksapp.dao.BookCategoryRepository;
import com.booksapp.booksapp.model.dto.SellerDTO;
import com.booksapp.booksapp.model.persistence.*;
import com.booksapp.booksapp.service.bookCategory.BookCategoryServiceImpl;
import com.booksapp.booksapp.service.book.BookServiceImpl;
import com.booksapp.booksapp.service.seller.SellerServiceImpl;
import com.booksapp.booksapp.service.user.UserServiceImpl;
import com.booksapp.booksapp.service.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sellers")
public class SellerController {

    private SellerServiceImpl sellerService;
    private UserServiceImpl userService;
    private BookCategoryServiceImpl bookCategoryService;
    private BookCategoryRepository bookCategoryRepository;
    private BookServiceImpl bookService;
    private EmailSender emailSender;

    @Autowired
    public SellerController(SellerServiceImpl sellerService, UserServiceImpl userService, BookCategoryServiceImpl bookCategoryService, BookCategoryRepository bookCategoryRepository, BookServiceImpl bookService, EmailSender emailSender) {
        this.sellerService = sellerService;
        this.userService = userService;
        this.bookCategoryService = bookCategoryService;
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookService = bookService;
        this.emailSender = emailSender;
    }

    @PostMapping("/register")
    public ResponseEntity<SellerEntity> register (@RequestBody SellerDTO sellerDTO) {

        UserEntity userEntity = new UserEntity(sellerDTO.getEmail(), sellerDTO.getPassword(), Role.SELLER);
        userService.createUser(userEntity);

        SellerEntity sellerEntity = new SellerEntity(sellerDTO.getName(), sellerDTO.getAddress(),
                sellerDTO.getPhone(), userEntity);
        sellerService.createSeller(sellerEntity);

        emailSender.sendEmail(sellerDTO.getEmail(), "Welcome to your account!");

        return new ResponseEntity<>(sellerEntity, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SellerEntity>> getSellers() {
        List<SellerEntity> sellers = sellerService.getAllSellers();

        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerEntity> getSellerById(@PathVariable long id) {
        SellerEntity sellerEntity = sellerService.getSellerById(id);

        return new ResponseEntity<>(sellerEntity, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerEntity> updateSeller(@PathVariable long id, @RequestBody SellerEntity sellerEntity) {
        sellerService.updateSeller(id, sellerEntity);
        String email = sellerEntity.getUserEntity().getEmail();
        emailSender.sendEmail("email", "Your account has been updated. If it wasn't you, please reset your password.");

        return new ResponseEntity<>(sellerEntity, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteSeller(@PathVariable long id) {
        SellerEntity seller = sellerService.getSellerById(id);
        String email = seller.getUserEntity().getEmail();
        sellerService.deleteSeller(id);
        emailSender.sendEmail(email, "Your account has benn deleted. Have a nice day!");

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/{sellerId}/categories")
    public ResponseEntity<BookCategoryEntity> createBookCategory(@PathVariable long sellerId, @RequestBody BookCategoryEntity bookCategoryEntity) {
        bookCategoryService.createCategory(sellerId, bookCategoryEntity);

        return new ResponseEntity<>(bookCategoryEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{sellerId}/categories/{id}")
    public ResponseEntity<BookCategoryEntity> getCategoryById(@PathVariable long sellerId, @PathVariable long id) {
        bookCategoryService.getCategoryById(sellerId, id);
        BookCategoryEntity bookCategoryEntity = bookCategoryRepository.findById(id).get();
        return new ResponseEntity<>(bookCategoryEntity, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/categories")
    public ResponseEntity<List> getAllCategories(@PathVariable long sellerId) {
        List<BookCategoryEntity> bookCategoryEntities = bookCategoryService.getAllCategories(sellerId);

        return new ResponseEntity<>(bookCategoryEntities, HttpStatus.OK);
    }

    @DeleteMapping("/{sellerId}/categories/{id}")
    public ResponseEntity deleteCategoryById(@PathVariable long sellerId, @PathVariable long id) {
        bookCategoryService.deleteCategory(sellerId, id);

        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{sellerId}/categories/{id}")
    public ResponseEntity<BookCategoryEntity> updateCategory(@PathVariable long sellerId, @PathVariable long id, @RequestBody BookCategoryEntity updatedBookCategory) {
        bookCategoryService.updateCategory(sellerId, id, updatedBookCategory);

        return new ResponseEntity<>(updatedBookCategory, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{sellerId}/categories/{id}/books")
    public ResponseEntity<BookEntity> addBook(@PathVariable long sellerId, @PathVariable long id, @RequestBody BookEntity newBook) {
        bookService.createBook(sellerId, id, newBook);

        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/{sellerId}/categories/{id}/books")
    public ResponseEntity<List<BookEntity>> getAllBooksByCategory(@PathVariable long sellerId, @PathVariable long id) {
        List<BookEntity> books = bookService.getAllBooksByCategory(sellerId, id);

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/categories/{categoryId}/books/{bookId}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable long sellerId, @PathVariable long categoryId, @PathVariable long bookId) {
        BookEntity book = bookService.getBookById(sellerId, categoryId, bookId);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("{sellerId}/categories/{categoryId}/books/{bookId}")
    public ResponseEntity<BookEntity> updateBook(@PathVariable long sellerId, @PathVariable long categoryId, @PathVariable long bookId, @RequestBody BookEntity updateBook) {
        BookEntity book = bookService.updateBook(sellerId, categoryId, bookId, updateBook);

        return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{sellerId}/categories/{categoryId}/books/{bookId}")
    public ResponseEntity deleteBook(@PathVariable long sellerId, @PathVariable long categoryId, @PathVariable long bookId) {
        bookService.deleteBook(sellerId, categoryId, bookId);

        return ResponseEntity.accepted().build();
    }
}
