package com.booksapp.booksapp.service;

import com.booksapp.booksapp.dao.UserRepository;
import com.booksapp.booksapp.exceptions.InvalidPasswordException;
import com.booksapp.booksapp.exceptions.UserAlreadyExistException;
import com.booksapp.booksapp.exceptions.UserNotFoundException;
import com.booksapp.booksapp.model.persistence.UserEntity;
import com.booksapp.booksapp.security.PasswordConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordConfig passwordConfig;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordConfig passwordConfig) {
        this.userRepository = userRepository;
        this.passwordConfig = passwordConfig;
    }

    public boolean checkPassword(String password) {
        boolean isDigit = false;
        boolean isLetter = false;

        for (int i=0; i<password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                isDigit = true;
            }
            else if (Character.isLetter(password.charAt(i))) {
                isLetter = true;
            }
        }
        return (isDigit && isLetter && password.length()>8);
    }

    @Override
    public UserEntity createUser(UserEntity newUser) {
        if (!checkPassword(newUser.getPassword())) {
            throw new InvalidPasswordException("The password doesn't meet the requirements.");
        }
        for (UserEntity userEntity : userRepository.findAll()) {
            if (userEntity.getEmail(). equals(newUser.getEmail())) {
                throw new UserAlreadyExistException("This email is already used.");
            }
        }
        for (UserEntity users : userRepository.findAll()) {
            System.out.println(users.getEmail());
        }

        newUser.setPassword(passwordConfig.passwordEncoder().encode(newUser.getPassword()));
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getUserById(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("The user with id " + id + " does not exist.");
        }
        return userRepository.findById(id).get();
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new UserNotFoundException("The user with email " + email + " doest not exist");
        }
        return userRepository.findByEmail(email).get();
    }

    @Override
    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("The user with id " + id + " does not exist.");
        }
        else {
            userRepository.deleteById(id);
        }

    }

    @Override
    public UserEntity updateUser(long id, UserEntity updatedUser) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("The user with id " + id + " does not exist.");
        }
        if (!checkPassword(updatedUser.getPassword())) {
            throw new InvalidPasswordException("The password doesn't meet the requirements.");
        }
        userRepository.deleteById(id);
        updatedUser.setPassword(passwordConfig.passwordEncoder().encode(updatedUser.getPassword()));
        userRepository.save(updatedUser);
        return updatedUser;
    }

    @Override
    public void resetPassword(String email, String newPassword, String confirmNewPassword) {
        if (!newPassword.equals(confirmNewPassword)) {
            throw new InvalidPasswordException("The passwords must be the same.");
        }
        if (!checkPassword(newPassword)) {
            throw new InvalidPasswordException("The password doesn't meet the requirements.");
        }

        this.userRepository.resetPassword(email, newPassword);
    }

    public String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }
}
