package com.booksapp.booksapp.service.bookCategory;

import com.booksapp.booksapp.dao.BookCategoryRepository;
import com.booksapp.booksapp.exceptions.bookCategoryExceptions.BookCategoryNotFoundException;
import com.booksapp.booksapp.model.persistence.BookCategoryEntity;
import com.booksapp.booksapp.model.persistence.SellerEntity;
import com.booksapp.booksapp.service.seller.SellerServiceImpl;
import com.booksapp.booksapp.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    private UserServiceImpl userService;
    private BookCategoryRepository bookCategoryRepository;
    private SellerServiceImpl sellerService;

    @Autowired
    public BookCategoryServiceImpl(UserServiceImpl userService, BookCategoryRepository bookCategoryRepository, SellerServiceImpl sellerService) {
        this.userService = userService;
        this.bookCategoryRepository = bookCategoryRepository;
        this.sellerService = sellerService;
    }

    @Override
    public BookCategoryEntity createCategory(long sellerId, BookCategoryEntity newBookCategoryEntity) {
        SellerEntity seller = sellerService.getSellerById(sellerId);
        newBookCategoryEntity.setSellerEntity(seller);
        return bookCategoryRepository.save(newBookCategoryEntity);
    }

    @Override
    public List<BookCategoryEntity> getAllCategories(long sellerId) {
        SellerEntity seller = sellerService.getSellerById(sellerId);
        return seller.getBookCategories();
    }

    @Override
    public BookCategoryEntity getCategoryById(long sellerId, long categoryId) {

        sellerService.getSellerById(sellerId);
        Optional<BookCategoryEntity> category = this.bookCategoryRepository.findCategoryById(sellerId, categoryId);

        if (category.isEmpty()) {
            throw new BookCategoryNotFoundException("The book category with id " + categoryId + " does not exist");
        }
        return category.get();
    }

    @Override
    public void deleteCategory(long  sellerId, long id) {

        sellerService.getSellerById(sellerId);
        BookCategoryEntity category = getCategoryById(sellerId, id);
        if (category == null) {
            throw new BookCategoryNotFoundException("The category with id " + id + " doest not exist");
        }
        bookCategoryRepository.deleteById(id);


    }

    @Override
    public BookCategoryEntity updateCategory(long sellerId, long id, BookCategoryEntity updatedCategory) {
        sellerService.getSellerById(sellerId);
        Optional<BookCategoryEntity> bookCategoryEntity = bookCategoryRepository.findById(id);
        if (bookCategoryEntity.isEmpty()) {
            throw new BookCategoryNotFoundException("You can't update the category with id " + id + " because it doesn't exist");
        }

        updatedCategory.setSellerEntity(sellerService.getSellerById(sellerId));
        bookCategoryRepository.deleteById(id);
        return bookCategoryRepository.save(updatedCategory);
    }
}
