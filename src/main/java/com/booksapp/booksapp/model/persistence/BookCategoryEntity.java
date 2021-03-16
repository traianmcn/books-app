package com.booksapp.booksapp.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "categories", schema = "books_app")
public class BookCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    private Timestamp created_at;

    private Category name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    @JsonIgnore
    private SellerEntity sellerEntity;

    public BookCategoryEntity(Category name, String description) {
        this.name = name;
        this.description = description;
    }

    public BookCategoryEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Category getName() {
        return name;
    }

    public void setName(Category name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SellerEntity getSellerEntity() {
        return sellerEntity;
    }

    public void setSellerEntity(SellerEntity sellerEntity) {
        this.sellerEntity = sellerEntity;
    }

    @Override
    public String toString() {
        return "BookCategoryEntity{" +
                "id=" + id +
                ", created_at=" + created_at +
                ", name=" + name +
                ", description='" + description + '\'' +
                ", sellerEntity=" + sellerEntity +
                '}';
    }
}
