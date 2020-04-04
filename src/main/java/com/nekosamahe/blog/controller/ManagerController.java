package com.nekosamahe.blog.controller;

// import java.util.concurrent.atomic.AtomicLong;
import com.nekosamahe.blog.repositories.*;
import com.nekosamahe.blog.models.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import java.util.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ArchiveRepository archiveRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;

	@GetMapping()
	public String managerView(@RequestParam(value = "filter", defaultValue = "") String filter, Model model) {
		model.addAttribute("filter", filter);
		return "manager/main";
	}
	
	@GetMapping("/edit")
	public String editView() {
		return "manager/edit";
	}

	@PostMapping("/edit")
	public String editView(@RequestParam Map<String, String> postData, Model model) {
		String id = postData.get("id");
		String _token = postData.get("_csrf");
		Archive archive = archiveRepository.findById(id).get();
		model.addAttribute("archive", archive);
		model.addAttribute("tagNumber", archive.getTag().size());
		model.addAttribute("categoryNumber", archive.getCategory().size());
		return "manager/edit";
	}

	@GetMapping("/archives")
	public String archiveView(Model model) {
		List<Archive> archives = archiveRepository.findAll();
		model.addAttribute("archives", archives);
		return "manager/archive";
	}

	@GetMapping("/categories")
	public String categoryView(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		return "manager/category";
	}

	@GetMapping("/tags")
	public String tagView(Model model) {
		List<Tag> tags = tagRepository.findAll();
		model.addAttribute("tags", tags);
		return "manager/tag";
	}

	@GetMapping("/users")
	public String userView(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "manager/user";
	}

	@GetMapping("/comments")
	public String commentView(Model model) {
		List<Comment> comments = commentRepository.findAll();
		model.addAttribute("comments", comments);
		return "manager/comment";
	}
}