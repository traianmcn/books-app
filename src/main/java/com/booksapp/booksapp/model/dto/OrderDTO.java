package com.booksapp.booksapp.model.dto;

import com.booksapp.booksapp.model.persistence.BookEntity;

import java.util.List;

public class OrderDTO {
    private String info;
    private double value;
    private List<ContentDTO> content;

    public OrderDTO(String info, double value, List<ContentDTO> content, long sellerId) {
        this.info = info;
        this.value = value;
        this.content = content;
    }

    public OrderDTO() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<ContentDTO> getContent() {
        return content;
    }

    public void setContent(List<ContentDTO> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "info='" + info + '\'' +
                ", value=" + value +
                ", content=" + content +
                '}';
    }
}
