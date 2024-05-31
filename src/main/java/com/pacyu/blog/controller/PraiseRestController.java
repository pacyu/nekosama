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
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import java.util.*;
import java.util.stream.*;


@RestController
@RequestMapping("/api/liked")
public class PraiseRestController {
    @Autowired
	private ArchiveRepository archiveRepository;

    @PostMapping(value="{id}")
    public Map<String, Object> liked(@PathVariable String id) {
        Archive archive = archiveRepository.findById(id).get();
        archive.setLikes(archive.getLikes() + 1L);
        archiveRepository.save(archive);
        Map<String, Object> resp = new HashMap<>();
        resp.put("code", 200);
        resp.put("message", "Ok!");
        return resp;
    }
}