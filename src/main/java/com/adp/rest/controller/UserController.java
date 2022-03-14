package com.adp.rest.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.adp.rest.bean.Post;
import com.adp.rest.bean.User;
import com.adp.rest.exception.UserNotFoundException;
import com.adp.rest.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> findAllUsers() {
		return userService.findAll();
	}

	@GetMapping("/users/{id}")
	public EntityModel<User> findUserById(@PathVariable("id") Integer id) {
		Optional<User> user = userService.findOne(id);
		if (user.isEmpty())
			throw new UserNotFoundException("user not found with id " + id);

		EntityModel<User> model = EntityModel.of(user.get());

		WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).findAllUsers());
		WebMvcLinkBuilder linkToDelete = linkTo(methodOn(this.getClass(), id).deleteUserById(id));

		model.add(linkToUsers.withRel("all-users"));
		model.add(linkToDelete.withRel("delete-user"));

		return model;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer id) {
		boolean isDeleted = userService.deleteById(id);
		if (!isDeleted)
			throw new UserNotFoundException("User not found with id " + id);
		return ResponseEntity.ok("User deleted");
	}

	@GetMapping("/users/{id}/posts")
	public List<Post> findAllPostsForUser(@PathVariable Integer id) {
		Optional<User> user = userService.findOne(id);
		if (user.isEmpty())
			throw new UserNotFoundException("User not found " + id);
		return user.get().getPosts();
	}
}
