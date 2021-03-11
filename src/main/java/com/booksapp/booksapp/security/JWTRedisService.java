package com.booksapp.booksapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.booksapp.booksapp.security.SecurityConstants.JWT;

@Service
public class JWTRedisService {

    private HashOperations<String, String, String> hashOperations;

    @Autowired
    public JWTRedisService(RedisTemplate<String, String> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void invalidateJWT(String jwt, String userEmail) {
        this.hashOperations.put(JWT, jwt, userEmail); // put jwt in JWT cache category, with the jwt's email(value and key)
    }

    public boolean isJWTBlackListed(String jwt) {
        if (this.hashOperations.get(JWT,jwt) != null) {
            return true;
        }
        return false;
    }
}
