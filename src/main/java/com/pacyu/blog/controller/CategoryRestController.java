package com.pacyu.blog.controller;

import com.pacyu.blog.repositories.*;

import java.util.Map;

import com.pacyu.blog.models.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import java.util.*;
import java.util.stream.*;


@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(value="{id}")
    public Category one(@PathVariable String id) {
        return categoryRepository.findById(id).get();
    }

    @PostMapping()
    public Map<String, Object> create(@RequestBody Map<String, String> postData) {
        String kindName = postData.get("kindName");
        String bgImage = postData.get("bgImage");
        categoryRepository.save(new Category(kindName, bgImage));

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Sucessfully!");
        resp.put("status", 200);
        return resp;
    }

    @PutMapping(value="{id}")
    public Map<String, Object> update(@PathVariable String id, @RequestBody Map<String, String> postData) {
        Category category = categoryRepository.findById(id).get();
        String kindName = postData.get("kindName");
        String bgImage = postData.get("bgImage");
        category.setKind(kindName);
        category.setBgImage(bgImage);
        categoryRepository.save(category);

        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Update sucessfully!");
        resp.put("status", 200);
        return resp;
    }

    @DeleteMapping(value="{id}")
    public Map<String, Object> delete(@PathVariable String id) {
        categoryRepository.deleteById(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Delete sucessfully!");
        resp.put("status", 200);
        return resp;
    }
}