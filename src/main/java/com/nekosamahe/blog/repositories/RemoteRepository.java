package com.nekosamahe.blog.repositories;

import com.nekosamahe.blog.models.Remote;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RemoteRepository extends MongoRepository<Remote, String> {
    
    @Query("{'aid': ?0, 'address': ?1}")
    public Remote findRemoteByMuilFields(String aid, String address);

    public Remote findByAddress(String address);

}