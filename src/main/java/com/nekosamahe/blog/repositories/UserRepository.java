package com.nekosamahe.blog.repositories;

import com.nekosamahe.blog.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends MongoRepository<User, String> {
    
    public User findByUsername(String username);

    public User findByEmail(String email);

}