package com.springboot.accesscontrol.models.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.accesscontrol.models.dao.UserDao;
import com.springboot.accesscontrol.models.entity.User;

@Service
public class ServiceUserImpl implements IServiceUser{

	@Autowired
	UserDao userDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	@Transactional
	public User create(User user) {
		return userDao.save(user);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		userDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public User update(Long id, User userUpdated) {
		User userActual = userDao.findById(id).orElseThrow(null);
		userActual.setCardNumber(userUpdated.getCardNumber());
		return userDao.save(userActual);		
	}

	@Override
	@Transactional(readOnly = true)
	public User findByCardNumberIs(String cardNumber) {
		return userDao.findByCardNumberIs(cardNumber);
	}
}
