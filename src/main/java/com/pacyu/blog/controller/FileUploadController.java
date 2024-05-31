package com.pacyu.blog.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Value("${file.upload.path}")
    private String pathDir;

	@PostMapping()
	public Map<String, Object> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
        String filename = file.getOriginalFilename();
        try {
            File dst = new File(pathDir + filename);
            file.transferTo(dst);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + filename + "!");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Map<String, Object> resp = new HashMap<>();

        resp.put("message", filename + " uploaded successfully!");
        resp.put("status", 200);
		return resp;
	}

}