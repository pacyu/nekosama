package com.nekosamahe.blog.controller;

import com.nekosamahe.blog.repositories.*;
import com.nekosamahe.blog.models.*;
import com.nekosamahe.blog.helper.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.*;
import javax.mail.MessagingException;


@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SmtpService smtpService;
    

    @GetMapping(value="{id}")
    public Comment one(@PathVariable String id) {
        return commentRepository.findById(id).get();
    }

    @PostMapping()
    public Map<String, Object> create(
        @RequestHeader Map<String, String> headers,
        @RequestBody Map<String, Object> postData,
        final Locale locale) throws MessagingException {

        String aid = (String)postData.get("aid");
        String name = (String)postData.get("name");
        String email = (String)postData.get("email");
        String content = (String)postData.get("message");

        Date created = new Date();
        User user = userRepository.findByUsername(name);
        if (user == null) {
            user = new User(email, name);
            userRepository.save(user);
        }
        String uid = user.getId();

        Archive archive = archiveRepository.findById(aid).get();

        if (postData.get("toUser") != null && postData.get("floor") != null && postData.get("layer") != null) {
            
            String toUser = (String)postData.get("toUser");
            User replyUser = userRepository.findByUsername(toUser);
            long floor = ((Integer)postData.get("floor")).longValue();
            long layer = ((Integer)postData.get("layer")).longValue();

            Comment specifyComment = commentRepository.findCommentByMuilField(aid, uid, floor, layer);

            if (specifyComment != null)
            {
                Comment comment = new Comment(aid, uid, content, specifyComment.getFloor(), specifyComment.getLayer() + 1, specifyComment.getFloor(), created);
                comment.setRelateId(specifyComment.getId());
                comment.setToUid(replyUser.getId());
                commentRepository.save(comment);
                this.smtpService.sendTextMail(user.getUsername(), email, archive.getTitle(), content, replyUser.getUsername(), replyUser.getEmail(), locale);
            }
        }
        else {
            List<Comment> comments = commentRepository.findCommentByLayerZero(aid);
            commentRepository.save(new Comment(aid, uid, content, comments.size() + 1L, 0L, comments.size() + 1L, created));
            this.smtpService.sendTextMail(user.getUsername(), email, archive.getTitle(), content, "Nekosama", "darkchii@qq.com", locale);
        }
        archive.setReviews(archive.getReviews() + 1L);
        archiveRepository.save(archive);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Reply sucessfully!");
        resp.put("status", 200);
        resp.put("content", content);
        return resp;
    }

    @PutMapping(value="{id}")
    public Map<String, Object> update(@PathVariable String id, @RequestHeader Map<String, String> headers, @RequestBody Map<String, Object> postData) {
        Comment comment = commentRepository.findById(id).get();
        String content = (String)postData.get("content");
        long floor = ((Integer)postData.get("floor")).longValue();
        long layer = ((Integer)postData.get("layer")).longValue();
        long relate = ((Integer)postData.get("relate")).longValue();
        String relateId = (String)postData.get("relateId");

        Date date = new Date();

        comment.setFloor(floor);
        comment.setLayer(layer);
        comment.setRelate(relate);
        comment.setRelateId(relateId);
        comment.setContent(content);
        comment.setUpdateDate(date);
        comment.setIsEdit(true);
        commentRepository.save(comment);
        
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Update sucessfully!");
        resp.put("status", 200);
        resp.put("content", content);
        return resp;
    }

    @DeleteMapping(value="{id}")
    public Map<String, Object> delete(@PathVariable String id, @RequestHeader Map<String, String> headers) {
        Comment comment = commentRepository.findById(id).get();
        // List<Comment> comments = commentRepository.findByRelateId(comment.getId()); // 可选：删除该文章下该评论 ID 相关联的所有评论
        // for (Comment comment2 : comments) {
        //     commentRepository.deleteById(comment2.getId());
        // }
        Archive archive = archiveRepository.findById(comment.getAid()).get();
        archive.setReviews(archive.getReviews() - 1L);
        archiveRepository.save(archive);
        commentRepository.deleteById(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Delete sucessfully!");
        resp.put("status", 200);
        return resp;
    }
}