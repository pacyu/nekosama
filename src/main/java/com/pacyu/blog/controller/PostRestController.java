package com.pacyu.blog.controller;

import com.pacyu.blog.repositories.*;

import java.util.Map;

import com.pacyu.blog.models.*;
import com.pacyu.blog.helper.SmtpService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;

import java.util.*;


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
            } else if (!Objects.equals(senderEmail, "")) {
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