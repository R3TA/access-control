package com.springboot.accesscontrol.models.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.accesscontrol.models.entity.User;

public interface UserDao extends JpaRepository<User, Long>{
	Optional<User> findByCardNumberIs(String cardNumber);
}
