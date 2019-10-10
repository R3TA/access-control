package com.springboot.accesscontrol.models.service;

import java.util.List;

import com.springboot.accesscontrol.models.entity.AccessHistory;

public interface IServiceAccessHistory {	
	AccessHistory create(AccessHistory access);
	List<AccessHistory> findAll();
	List<AccessHistory> findAllByIdUser(Long id);
	void delete(Long id);
}
