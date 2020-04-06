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
@RequestMapping("/api/user")
public class UserRestController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(value="{id}")
    public User one(@PathVariable String id) {
        return userRepository.findById(id).get();
    }

    @PostMapping()
    public Map<String, Object> create(@RequestBody Map<String, String> postData) {
        String user = postData.get("user");
        String email = postData.get("email");
        Map<String, Object> resp = new HashMap<>();
        
        if (userRepository.findByUsername(user) != null) {
            resp.put("message", user + " add failed!");
            resp.put("status", 401);
        }
        else {
            userRepository.save(new User(user, email));
            resp.put("message", user + " add sucessfully!");
            resp.put("status", 401);
        }
        
        return resp;
    }

    @DeleteMapping(value="{id}")
    public Map<String, Object> delete(@PathVariable String id) {
        userRepository.deleteById(id);
        List<Comment> comments = commentRepository.findByUid(id);
        for (Comment comment : comments) {
            commentRepository.deleteById(comment.getId());
        }
        
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Delete sucessfully!");
        resp.put("status", 401);
        return resp;
    }
}