package com.nekosamahe.blog.repositories;

import com.nekosamahe.blog.models.Comment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.*;


public interface CommentRepository extends MongoRepository<Comment, String> {
    
    public List<Comment> findByAid(String aid);

    public Page<Comment> findByAid(String aid, Pageable pageable);

    public List<Comment> findByUid(String uid);

    public Comment findByToUid(String toUid);

    public List<Comment> findByRelateId(String relateId);

    @Query("{'aid': ?0, 'uid': ?1, 'floor': ?2, 'layer': ?3}")
    public Comment findCommentByMuilField(String aid, String uid, long floor, long layer);

    @Query("{'aid': ?0, 'layer': 0}")
    public List<Comment> findCommentByLayerZero(String aid);

    @Query("{'aid': ?0, 'layer': 0}")
    public Page<Comment> findCommentByLayerZero(String aid, Pageable pageable);

    @Query("{'aid': ?0, 'floor': ?1, 'layer':{$gt: 0}}")
    public List<Comment> findCommentByLayerThanZero(String aid, long floor);

}