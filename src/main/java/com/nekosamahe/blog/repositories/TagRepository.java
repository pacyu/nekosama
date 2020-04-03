package com.nekosamahe.blog.repositories;

import com.nekosamahe.blog.models.Tag;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface TagRepository extends MongoRepository<Tag, String> {
    
    public Tag findByTag(String tag);

    @Query("{'tag': {$regex: '?0', $options: 'si'}}")
    public List<Tag> findTagsByRegEx(String regex);


}