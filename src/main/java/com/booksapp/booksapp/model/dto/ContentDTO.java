package com.booksapp.booksapp.model.dto;

public class ContentDTO {
    private long sellerId;
    private long categoryId;
    private long bookId;

    public ContentDTO(long sellerId, long categoryId, long bookId) {
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.bookId = bookId;
    }

    public ContentDTO() {
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
