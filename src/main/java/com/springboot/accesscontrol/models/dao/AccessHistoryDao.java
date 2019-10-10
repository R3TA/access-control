package com.springboot.accesscontrol.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.accesscontrol.models.entity.AccessHistory;

public interface AccessHistoryDao extends JpaRepository<AccessHistory, Long>{

	@Query("SELECT a FROM AccessHistory a WHERE user_id = ?1")
	List<AccessHistory> findAllByIdUser(Long id);
}
