package com.springboot.accesscontrol.models.service;

import java.util.List;

import com.springboot.accesscontrol.models.entity.User;

public interface IServiceUser {
	User create(User user);
	List<User> findAll();
	User findById(Long id);
	User findByCardNumberIs(String cardNumber);
	User update(Long id, User user);
	void deleteById(Long id);
}
