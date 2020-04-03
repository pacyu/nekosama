package com.nekosamahe.blog.repositories;

import com.nekosamahe.blog.models.Category;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface CategoryRepository extends MongoRepository<Category, String> {
    
    public Category findByKind(String category);

    @Query("{'kind': {$regex: '?0', $options: 'si'}}")
    public List<Category> findCategoriesByRegEx(String regex);

}