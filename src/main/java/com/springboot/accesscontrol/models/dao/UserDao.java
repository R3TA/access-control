package com.springboot.accesscontrol.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.accesscontrol.models.entity.User;

public interface UserDao extends JpaRepository<User, Long>{
	User findByCardNumberIs(String cardNumber);
}
