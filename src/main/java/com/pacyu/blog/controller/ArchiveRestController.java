package com.pacyu.blog.controller;

import com.pacyu.blog.repositories.*;
import com.pacyu.blog.models.*;
import com.pacyu.blog.bean.*;
import com.pacyu.blog.exception.NotFoundException;
import com.pacyu.blog.assembler.ModelAssembler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.*;
import java.util.stream.*;


@RestController
@RequestMapping("/api/archive")
public class ArchiveRestController {

	private final ModelAssembler assembler;
	@Autowired
	private ArchiveRepository archiveRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;

	ArchiveRestController(ModelAssembler assembler) {
		this.assembler = assembler;
	}

	@GetMapping()
	public CollectionModel<EntityModel<Archive>> all() {
		List<EntityModel<Archive>> archives = archiveRepository
			.findAll()
			.stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());
		return new CollectionModel<>(archives,
			linkTo(methodOn(ArchiveRestController.class).all()).withSelfRel());
	}

	@GetMapping(value="{id}")
	public EntityModel<Archive> one(@PathVariable String id) {
		Archive archive = archiveRepository
			.findById(id)
			.orElseThrow(() -> new NotFoundException(id));
		return assembler.toModel(archive);
	}

	@PostMapping()
	@ResponseBody
	public Map<String, Object> create(@RequestBody RequestBean postData) throws Exception {
		String _type = postData.getType();
		String title = postData.getTitle();
		String content = postData.getContent();
		String introduction = postData.getIntroduction();
		String cover = postData.getCover();
		ArrayList<String> categories = postData.getCategories();
		ArrayList<String> tags = postData.getTags();
		Boolean isRelease = postData.getRelease();

		for (String string : categories) {
			if (categoryRepository.findByKind(string) == null) {
				Category category = new Category(string);
				categoryRepository.save(category);
			}
		}

		for (String string : tags) {
			if (tagRepository.findByTag(string) == null) {
				Tag tag = new Tag(string);
				tagRepository.save(tag);
			}
		}

		Date created = new Date();
		Archive archive = new Archive(title, introduction, content, cover, categories,
			created, null, null, "pacyu", 0L, 0L, 0L, 0L, false, false, tags);
		if (isRelease) {
			archive.setReleaseDate(created);
		} else {
			archive.setIsDraft(true);
		}
		archive.setHashString(archive.genMd5());
		archiveRepository.save(archive);
		Map<String, Object> resp = new HashMap<>();
		resp.put("message", _type + " sucessfully!");
		resp.put("archive", archive.getId());
		resp.put("status", 200);
		return resp;
	}

	@PutMapping(value="{id}")
	public Map<String, Object> update(@RequestBody RequestBean postData, @RequestHeader Map<String, String> headers, @PathVariable String id) {
		Archive archive = archiveRepository.findById(id).get();
		String _type = postData.getType();
		String title = postData.getTitle();
		String content = postData.getContent();
		String introduction = postData.getIntroduction();
		String cover = postData.getCover();
		ArrayList<String> categories = postData.getCategories();
		ArrayList<String> tags = postData.getTags();
		Boolean isRelease = postData.getRelease();
		switch (_type)
		{
		case "draft":
			archive.setIsDraft(!archive.getIsDraft());
			break;
		case "update":
			archive.setTitle(title);
			archive.setIntroduction(introduction);
			archive.setContent(content);
			archive.setCover(cover);
			archive.setUpdateDate(new Date());
			archive.setCategory(categories);
			archive.setTag(tags);
			archive.setEditCount(archive.getEditCount() + 1L);
			archive.setIsDraft(!isRelease);
			for (String string : categories) {
				if (categoryRepository.findByKind(string) == null) {
					Category category = new Category(string);
					categoryRepository.save(category);
				}
			}
			for (String string : tags) {
				if (tagRepository.findByTag(string) == null) {
					Tag tag = new Tag(string);
					tagRepository.save(tag);
				}
			}
			break;
		case "remove":
			archive.setIsRemove(!archive.getIsRemove());
			break;
		}
		archiveRepository.save(archive);
		Map<String, Object> resp = new HashMap<>();
		resp.put("message", _type + " sucessfully!");
		resp.put("archive", id);
		resp.put("status", 200);
		return resp;
	}

	@DeleteMapping(value="{id}")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody Map<String, String> postData, @PathVariable String id) {
		String _type = postData.get("type");
		switch (_type)
		{
			case "delete":
			{
				List<Comment> comments = commentRepository.findByAid(id);
				for (Comment comment : comments) {
					commentRepository.deleteById(comment.getId());
				}
				archiveRepository.deleteById(id);
			}
			break;
		}
		Map<String, Object> resp = new HashMap<>();
		resp.put("message", _type + " sucessfully!");
		resp.put("archive", id);
		resp.put("status", 200);
		return resp;
	}

}