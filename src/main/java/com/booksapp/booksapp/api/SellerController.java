package com.booksapp.booksapp.api;

import com.booksapp.booksapp.model.dto.SellerDTO;
import com.booksapp.booksapp.model.persistence.Role;
import com.booksapp.booksapp.model.persistence.SellerEntity;
import com.booksapp.booksapp.model.persistence.UserEntity;
import com.booksapp.booksapp.service.SellerService;
import com.booksapp.booksapp.service.SellerServiceImpl;
import com.booksapp.booksapp.service.UserServiceImpl;
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

    @Autowired
    public SellerController(SellerServiceImpl sellerService, UserServiceImpl userService) {
        this.sellerService = sellerService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<SellerEntity> register (@RequestBody SellerDTO sellerDTO) {

        UserEntity userEntity = new UserEntity(sellerDTO.getEmail(), sellerDTO.getPassword(), Role.SELLER);
        userService.createUser(userEntity);

        SellerEntity sellerEntity = new SellerEntity(sellerDTO.getName(), sellerDTO.getAddress(),
                sellerDTO.getPhone(), userEntity);
        sellerService.createSeller(sellerEntity);

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

    @PostMapping("/{id}")
    public ResponseEntity<SellerEntity> updateSeller(@PathVariable long id, @RequestBody SellerEntity sellerEntity) {
        sellerService.updateSeller(id, sellerEntity);

        return new ResponseEntity<>(sellerEntity, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteSeller(@PathVariable long id) {
        sellerService.deleteSeller(id);

        return ResponseEntity.accepted().build();
    }
}
