package com.booksapp.booksapp.dao;

import com.booksapp.booksapp.model.persistence.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;



    @Test
    void findByEmail() {


        String email = "mocanu_traian@yahoo.com";
        Optional<UserEntity> userExpected = this.underTest.findByEmail(email);

        assertThat(userExpected).isNotEmpty();

    }
}