package com.springboot.accesscontrol.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.accesscontrol.models.dao.AccessHistoryDao;
import com.springboot.accesscontrol.models.entity.AccessHistory;

@Service
public class ServiceAccessHistoryImpl implements IServiceAccessHistory{

	AccessHistoryDao accessHistoryDao;
	
	@Autowired
	public ServiceAccessHistoryImpl(AccessHistoryDao accessHistoryDao) {
		this.accessHistoryDao = accessHistoryDao;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AccessHistory> findAll() {
		return this.accessHistoryDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccessHistory> findAllByIdUser(Long id) {
		return this.accessHistoryDao.findAllByIdUser(id);
	}

	@Override
	@Transactional
	public AccessHistory create(AccessHistory access) {
		return this.accessHistoryDao.save(access);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.accessHistoryDao.deleteById(id);
	}
}
