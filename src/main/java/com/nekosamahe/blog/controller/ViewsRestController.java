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

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.*;


@RestController
@RequestMapping("/api/viewed")
public class ViewsRestController {
    @Autowired
    private RemoteRepository remoteRepository;
    @Autowired
	private ArchiveRepository archiveRepository;

    @PostMapping(value="{id}")
    public Map<String, Object> viewed(HttpServletRequest request, @PathVariable String id) {
        String remoteAddr = request.getRemoteAddr();
        Map<String, Object> resp = new HashMap<>();
        if (remoteRepository.findByAddress(remoteAddr) == null) {
            Archive archive = archiveRepository.findById(id).get();
            archive.setViews(archive.getViews() + 1L);
            archiveRepository.save(archive);
            resp.put("code", 200);
            resp.put("message", "Ok!");
        }
        else {
            resp.put("code", 201);
            resp.put("message", "Address " + remoteAddr + " already existed!");
        }
        return resp;
    }
}