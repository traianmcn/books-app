package com.booksapp.booksapp.service.seller;

import com.booksapp.booksapp.model.persistence.SellerEntity;
import com.booksapp.booksapp.model.persistence.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {

    SellerEntity createSeller(SellerEntity newSeller);
    List<SellerEntity> getAllSellers();
    SellerEntity getSellerById(long id);
    void deleteSeller(long id);
    SellerEntity updateSeller(long id, SellerEntity updatedSeller);
    SellerEntity getSellerByEmail(String email);
}
