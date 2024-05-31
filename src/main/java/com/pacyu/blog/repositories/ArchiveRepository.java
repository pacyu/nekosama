package com.pacyu.blog.repositories;

import com.pacyu.blog.models.Archive;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.*;

@Service
public interface ArchiveRepository extends MongoRepository<Archive, String> {

    @Query("{$or: [{'title': {$regex: '?0', $options: 'si'}}, {'content': {$regex: '?0', $options: 'si'}}, {'tag': {$regex: '?0', $options: 'si'}}, {'category': {$regex: '?0', $options: 'si'}}, {'releaseDate': [{$toString: 'releaseDate'}, {$regex: '?0', $options: 'si'}]}]}")
    public Page<Archive> findArchivesByRegEx(String regex, Pageable pageable);

    public Archive findByReleaseDate(String releaseDate);

    public Archive findByTitle(String title);

    @Query("{'category': {$eq: '?0'}}")
    public List<Archive> findByCategory(String category);

    @Query("{'category': {$eq: '?0'}}")
    public Page<Archive> findByCategory(String category, Pageable pageable);

    @Query("{'cover': {$regex: '?0', $options: 'si'}}")
    public Page<Archive> findArchiveByCoverRegEx(String regex, Pageable pageable);
}