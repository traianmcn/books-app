package com.booksapp.booksapp.service.user;

import com.booksapp.booksapp.dao.UserRepository;
import com.booksapp.booksapp.exceptions.InvalidPasswordException;
import com.booksapp.booksapp.exceptions.userException.InvalidEmailFormatException;
import com.booksapp.booksapp.exceptions.userException.UserAlreadyExistException;
import com.booksapp.booksapp.exceptions.userException.UserNotFoundException;
import com.booksapp.booksapp.model.persistence.UserEntity;
import com.booksapp.booksapp.security.PasswordConfig;
import com.booksapp.booksapp.service.email.EmailValidator;
import com.booksapp.booksapp.service.user.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private EmailValidator emailValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordConfig passwordConfig, EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.passwordConfig = passwordConfig;
        this.emailValidator = emailValidator;
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

        if (!emailValidator.isValid(newUser.getEmail())) {
            throw new InvalidEmailFormatException("Incorrect email format!");
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
            userRepository.deleteById(id);

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
