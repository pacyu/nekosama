package com.nekosamahe.blog.controller;

import com.nekosamahe.blog.repositories.*;

import java.util.Map;

import com.nekosamahe.blog.models.*;

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
@RequestMapping("/api/tag")
public class TagRestController {
    @Autowired
    private TagRepository tagRepository;

    @GetMapping(value="{id}")
    public Tag one(@PathVariable String id) {
        return tagRepository.findById(id).get();
    }

    @PostMapping()
    public Map<String, Object> create(@RequestBody Map<String, String> postData) {
        String tag = postData.get("tag");
        tagRepository.save(new Tag(tag));
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Sucessfully!");
        resp.put("status", 200);
        return resp;
    }

    @PutMapping(value="{id}")
    public Map<String, Object> update(@PathVariable String id, @RequestBody Map<String, String> postData) {
        Tag tag = tagRepository.findById(id).get();
        String tagName = postData.get("tagName");
        tag.setTag(tagName);
        tagRepository.save(tag);
        
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Update sucessfully!");
        resp.put("status", 200);
        return resp;
    }

    @DeleteMapping(value="{id}")
    public Map<String, Object> delete(@PathVariable String id) {
        tagRepository.deleteById(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Delete sucessfully!");
        resp.put("status", 200);
        return resp;
    }
}