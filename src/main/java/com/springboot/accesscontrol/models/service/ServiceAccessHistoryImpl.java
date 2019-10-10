package com.springboot.accesscontrol.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.accesscontrol.models.dao.AccessHistoryDao;
import com.springboot.accesscontrol.models.entity.AccessHistory;

@Service
public class ServiceAccessHistoryImpl implements IServiceAccessHistory{

	@Autowired
	AccessHistoryDao accessHistoryDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<AccessHistory> findAll() {
		return accessHistoryDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccessHistory> findAllByIdUser(Long id) {
		return accessHistoryDao.findAllByIdUser(id);
	}

	@Override
	@Transactional
	public AccessHistory create(AccessHistory access) {
		return accessHistoryDao.save(access);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		accessHistoryDao.deleteById(id);
	}
}
