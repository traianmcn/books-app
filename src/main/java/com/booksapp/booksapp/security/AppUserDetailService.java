package com.booksapp.booksapp.security;

import com.booksapp.booksapp.dao.UserRepository;
import com.booksapp.booksapp.model.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("The user does not exist");
        }
        return CurrentUser.create(user.get());
    }

    public UserDetails loadUserById(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User not found for id "+ userId);
        }
        return CurrentUser.create(userEntity.get());
    }
}

