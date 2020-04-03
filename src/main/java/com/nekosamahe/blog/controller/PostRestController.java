package com.nekosamahe.blog.controller;

import com.nekosamahe.blog.repositories.*;

import java.util.Map;

import com.nekosamahe.blog.models.*;
import com.nekosamahe.blog.helper.SmtpService;

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

import javax.mail.MessagingException;

import java.util.*;
import java.util.stream.*;


@RestController
@RequestMapping("/api/feedback")
public class PostRestController {

    @Autowired
    private SmtpService smtpService;
    @Autowired
    private UserRepository userReprository;

    @PostMapping()
    public Map<String, Object> send(
        @RequestBody Map<String, String> postData,
        final Locale locale)
    {
        String senderEmail = postData.get("senderEmail");
        String content = postData.get("content");
        User user = userReprository.findByEmail(senderEmail);
        Map<String, Object> resp = new HashMap<>();
        try {
            if (user != null) {
                smtpService.sendTextMail(user.getUsername(), senderEmail, "", content, "Nekosama", "darkchii@qq.com", locale);
                resp.put("message", "邮件发送成功!");
                resp.put("status", 200);
                resp.put("content", content);
            } else if (senderEmail != "") {
                userReprository.save(new User(senderEmail));
                smtpService.sendTextMail("", senderEmail,  "", content, "Nekosama", "darkchii@qq.com", locale);
                resp.put("message", "邮件发送成功!");
                resp.put("status", 200);
                resp.put("content", content);
            } else {
                resp.put("message", "邮件发送失败!");
                resp.put("status", 400);
                resp.put("content", "邮件为空！");
            }
        }
        catch (MessagingException e) {
            resp.put("message", e.getMessage() + "邮件发送失败!");
            resp.put("status", 400);
            resp.put("content", content);
        }
        
        return resp;
    }

}