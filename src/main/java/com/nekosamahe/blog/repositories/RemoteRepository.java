package com.nekosamahe.blog.repositories;

import com.nekosamahe.blog.models.Remote;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RemoteRepository extends MongoRepository<Remote, String> {
    
    public Remote findByAddress(String address);

}