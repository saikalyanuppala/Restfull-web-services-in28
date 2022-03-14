package com.adp.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.rest.bean.User;
import com.adp.rest.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User save(User user) {
		return repository.save(user);
	}

	public Optional<User> findOne(int id) {
		return repository.findById(id);
	}

	public boolean deleteById(Integer id) {
		repository.deleteById(id);
		return repository.findById(id).isEmpty();
	}
}
