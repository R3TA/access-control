package com.springboot.accesscontrol;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.accesscontrol.models.dao.UserDao;
import com.springboot.accesscontrol.models.service.IServiceUser;
import com.springboot.accesscontrol.models.service.ServiceUserImpl;

@Configuration
public class MvcConfig {
	/*
	@Bean("MyUserService")
	public IServiceUser registrarMiServicio(UserDao userDao) {
		return new ServiceUserImpl (userDao);
	}
	*/
}
