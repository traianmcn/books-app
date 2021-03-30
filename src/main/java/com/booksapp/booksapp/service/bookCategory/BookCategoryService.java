package com.booksapp.booksapp.service.bookCategory;

import com.booksapp.booksapp.model.persistence.BookCategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookCategoryService {

    BookCategoryEntity createCategory(long userId, BookCategoryEntity newBookCategoryEntity);
    List<BookCategoryEntity> getAllCategories(long sellerId);
    BookCategoryEntity getCategoryById(long sellerId, long categoryId);
    void deleteCategory(long seller, long id);
    BookCategoryEntity updateCategory(long sellerId, long id, BookCategoryEntity updatedCategory);

}
