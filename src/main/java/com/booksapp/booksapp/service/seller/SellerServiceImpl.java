package com.booksapp.booksapp.service.seller;

import com.booksapp.booksapp.dao.SellerRepository;
import com.booksapp.booksapp.exceptions.sellerException.SellerNotFoundException;
import com.booksapp.booksapp.model.persistence.SellerEntity;
import com.booksapp.booksapp.model.persistence.UserEntity;
import com.booksapp.booksapp.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;
    private UserServiceImpl userService;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, UserServiceImpl userService) {
        this.sellerRepository = sellerRepository;
        this.userService = userService;
    }

    @Override
    public SellerEntity createSeller(SellerEntity newSeller) {
        return sellerRepository.save(newSeller);
    }

    @Override
    public List<SellerEntity> getAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public SellerEntity getSellerById(long id) {
        if (sellerRepository.findById(id).isEmpty()) {
            throw new SellerNotFoundException("The seller with id " + id + " does not exist.");
        }
        return sellerRepository.findById(id).get();
    }

    @Override
    public void deleteSeller(long id) {
        SellerEntity sellerEntity = getSellerById(id);
        UserEntity userEntity = sellerEntity.getUserEntity();
        userService.deleteUser(userEntity.getId());

    }

    @Override
    public SellerEntity updateSeller(long id, SellerEntity updatedSeller) {
        SellerEntity seller = getSellerById(id);
        UserEntity userEntity = seller.getUserEntity();
        sellerRepository.deleteById(id);
        updatedSeller.setUserEntity(userEntity);

        return sellerRepository.save(updatedSeller);
    }

    @Override
    public SellerEntity getSellerByEmail(String email) {
        SellerEntity seller = sellerRepository.findSellerByEmail(email);

        if (seller == null) {
            throw new SellerNotFoundException("The seller with email " + email + " does not exist");
        }

        return seller;
    }
}
