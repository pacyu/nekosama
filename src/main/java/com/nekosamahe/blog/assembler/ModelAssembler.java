package com.nekosamahe.blog.assembler;

import com.nekosamahe.blog.controller.ArchiveRestController;
import com.nekosamahe.blog.models.Archive;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ModelAssembler implements RepresentationModelAssembler<Archive, EntityModel<Archive>> {

  @Override
  public EntityModel<Archive> toModel(Archive archive) {

    return new EntityModel<>(archive,
      linkTo(methodOn(ArchiveRestController.class).one(archive.getId())).withSelfRel(),
      linkTo(methodOn(ArchiveRestController.class).all()).withRel("archives"));
  }
}