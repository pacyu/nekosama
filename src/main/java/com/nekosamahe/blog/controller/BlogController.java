package com.nekosamahe.blog.controller;

import com.nekosamahe.blog.models.*;
import com.nekosamahe.blog.repositories.*;
import com.nekosamahe.blog.helper.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.*;


@Controller
public class BlogController {
    @Autowired
    private ArchiveRepository archiveRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;

    @GetMapping()
    public String root(@RequestParam Map<String, Object> requestParams, Model model) {
        int pageCode = requestParams.get("page") == null ? 0 : Integer.parseInt((String)requestParams.get("page"));
        List<Archive> archives = archiveRepository.findAll(PageRequest.of(pageCode, 6)).getContent();
        List<Category> categories = categoryRepository.findAll();
        List<List<Category>> group_categories = new ArrayList<>();

        for (int i = 0; i < categories.size(); i += 3) {
            group_categories.add(categories.subList(i, (i + 3) > categories.size() ? categories.size() : (i + 3)));
        }

        model.addAttribute("archives", archives);
        model.addAttribute("categories", group_categories);
        model.addAttribute("page", pageCode);
        if (archives.size() != 6)
            model.addAttribute("isAll", true);
        return "blog/index";
    }
    
    @GetMapping("/index")
    public String home(@RequestParam Map<String, Object> requestParams, Model model) {
        int pageCode = requestParams.get("page") == null ? 0 : Integer.parseInt((String)requestParams.get("page"));
        List<Archive> archives = archiveRepository.findAll(PageRequest.of(pageCode, 6)).getContent();
        List<Category> categories = categoryRepository.findAll();
        List<List<Category>> group_categories = new ArrayList<>();

        for (int i = 0; i < categories.size(); i += 3) {
            group_categories.add(categories.subList(i, (i + 3) > categories.size() ? categories.size() : (i + 3)));
        }

        model.addAttribute("archives", archives);
        model.addAttribute("categories", group_categories);
        model.addAttribute("page", pageCode);
        if (archives.size() != 6)
            model.addAttribute("isAll", true);
        return "blog/index";
    }

    @GetMapping("/category")
    public String categoryView(@RequestParam Map<String, Object> requestParams, Model model) {
        int pageCode = requestParams.get("page") == null ? 0 : Integer.parseInt((String)requestParams.get("page"));
        List<Category> categories = categoryRepository.findAll();
        List<Archive> archives = null;
        if (requestParams.get("cw") != null) {
            String cw = (String)requestParams.get("cw");
            archives = archiveRepository.findByCategory(cw, PageRequest.of(pageCode, 6)).getContent();
            model.addAttribute("cw", cw);
        } else {
            archives = archiveRepository.findAll(PageRequest.of(pageCode, 6)).getContent();
        }
        model.addAttribute("categories", categories);
        model.addAttribute("archives", archives);
        model.addAttribute("page", pageCode);
        if (archives.size() != 6)
            model.addAttribute("isAll", true);
        return "blog/category";
    }

    @GetMapping("/about")
    public String aboutView(Model model) {
        Archive archive = archiveRepository.findByTitle("About me");
        List<Tag> tags = tagRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("about", archive);
        model.addAttribute("tags", tags);
        model.addAttribute("categories", categories);
        return "blog/about";
    }

    @GetMapping("/archive")
    public String detailsView(@RequestParam Map<String, Object> postData, Model model) {
        System.out.println("============================");
        System.out.println(postData.get("aid"));
        System.out.println("============================");

        String aid = (String)postData.get("aid");
        Integer pageCode = postData.get("page") == null ? 0 : Integer.parseInt((String)postData.get("page"));
        Archive archive = archiveRepository.findById(aid).get();
        List<Archive> archives = archiveRepository.findAll();
        List<Comment> comments = commentRepository.findByAid(aid, PageRequest.of(pageCode, 3)).getContent();
        List<Tag> tags = tagRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        
        if (comments.size() > 0)
        {
            List<Comment> masters = commentRepository.findCommentByLayerZero(aid, PageRequest.of(pageCode, 3)).getContent();
            List<FloorLayer> floors = new ArrayList<FloorLayer>();
            
            for (Comment master : masters) {
                User user = userRepository.findById(master.getUid()).get();
                List<Comment> layers = commentRepository.findCommentByLayerThanZero(aid, master.getFloor());
                List<Replier> replies = new LinkedList<>();
                for (Comment layer : layers) {
                    User fromUser = userRepository.findById(layer.getUid()).get();
                    User toUser = userRepository.findById(layer.getToUid()).get();
                    replies.add(new Replier(toUser.getUsername(), fromUser.getUsername(), layer.getPostDate(), layer.getContent()));
                }
                floors.add(new FloorLayer(new Master(user.getUsername(), master.getPostDate(), master.getContent()), replies));
            }
            model.addAttribute("comments", floors);
            if (masters.size() != 3) {
                model.addAttribute("isAll", true);
            }
        }
        model.addAttribute("page", pageCode);
        model.addAttribute("article", archive);
        model.addAttribute("archives", archives);
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tags);
        return "blog/detail";
    }

    @GetMapping("/lab")
    public String labView(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "blog/lab";
    }

    @GetMapping("/search")
    public String searchView(@RequestParam Map<String, Object> requestRarams, Model model) {
        List<Category> categories = categoryRepository.findAll();

        if (requestRarams.get("q") != null) {
            String q = (String)requestRarams.get("q");
            int pageCode = (requestRarams.get("page") == null) ? 0 : Integer.parseInt((String)requestRarams.get("page"));

            if (q.equals("tag") || q.equals("标签")) { // 列出所有标签
                List<Tag> tags = tagRepository.findAll();
                model.addAttribute("tags", tags);
                model.addAttribute("resultType", "tag");
            } else if (q.equals("date")) { // 文档按日期排序展示
                List<Archive> archives = archiveRepository.findAll(PageRequest.of(pageCode, 10)).getContent();
                if (archives != null) {
                    model.addAttribute("archives", archives);
                    model.addAttribute("resultType", "date");
                    if (archives.size() != 10) {
                        model.addAttribute("isAll", true);
                    }
                }
            } else if (q.equals("image") || q.equals("图片") || q.equals("封面")) { // 列出所有封面图片
                List<Archive> archives = archiveRepository.findAll(PageRequest.of(pageCode, 6)).getContent();
                if (archives != null) {
                    model.addAttribute("archives", archives);
                    model.addAttribute("resultType", "cover");
                    if (archives.size() != 6) {
                        model.addAttribute("isAll", true);
                    }
                }
            } else if (q.contains("img:")) {
                List<Archive> archives = archiveRepository.findArchiveByCoverRegEx(q.split(":")[1], PageRequest.of(pageCode, 6)).getContent();
                if (archives != null) {
                    model.addAttribute("archives", archives);
                    model.addAttribute("resultType", "cover");
                    if (archives.size() != 6) {
                        model.addAttribute("isAll", true);
                    }
                }
            } else { // 其它
                List<Archive> archives = archiveRepository.findArchivesByRegEx(".*" + q + ".*", PageRequest.of(pageCode, 10)).getContent();
                if (archives != null) {
                    model.addAttribute("archives", archives);
                    model.addAttribute("resultType", "archive");
                    if (archives.size() != 10) {
                        model.addAttribute("isAll", true);
                    }
                } else {
                    model.addAttribute("resultType", "notfound");
                }
            }

            model.addAttribute("searchWord", q);
            model.addAttribute("page", pageCode);
        } else {
            model.addAttribute("resultType", "search");
        }
        
        model.addAttribute("categories", categories);
        
        return "blog/search";
    }
}