package com.booksapp.booksapp.service;

import com.booksapp.booksapp.dao.UserRepository;
import com.booksapp.booksapp.model.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserEntity createUser(UserEntity newUser);
    List<UserEntity> getAllUsers();
    UserEntity getUserById(long id);
    void deleteUser(long id);
    UserEntity updateUser(long id, UserEntity updatedUser);
    void resetPassword(String email, String newPassword, String confirmNewPassword);
    String generateCommonLangPassword();
    UserEntity getUserByEmail(String email);
}
