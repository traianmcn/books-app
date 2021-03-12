package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE books_app.users " +
            "SET password = :userPassword " +
            "WHERE email = :userEmail", nativeQuery = true)
    public void resetPassword(String userEmail, String userPassword);
}
