package com.springboot.accesscontrol;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.accesscontrol.controllers.ControllerUser;
import com.springboot.accesscontrol.models.entity.User;
import com.springboot.accesscontrol.models.service.IServiceUser;

@RunWith(SpringRunner.class)
@WebMvcTest(ControllerUser.class)
//@SpringBootTest
public class ControllerUserTest {
    Logger log = LoggerFactory.getLogger(getClass());
	@Spy
	@InjectMocks
	private ControllerUser controllerUser;
	
	@MockBean
	private IServiceUser serviceUser;
	
	@MockBean
	BindingResult bindingResult;
	
	@Autowired
	MockMvc mockMvc;
	
	@Before
    public void setUp() {
		controllerUser = new ControllerUser(serviceUser);
    }

	@Test
	public void testCreateSuccessfully() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setCardNumber("12121212");
	    
		Mockito.when(serviceUser.create(Mockito.any(User.class))).thenReturn(user);
		ResponseEntity<?> responseEntity = controllerUser.create(user, bindingResult);
        ObjectMapper Obj = new ObjectMapper();
		String json = Obj.writeValueAsString(user);

		mockMvc.perform(post("/api")
        		.content(json)
        		.contentType(MediaType.APPLICATION_JSON)
	    		.accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.message").value("The user was created successfully!"))
        .andExpect(jsonPath("$.user.id").value(user.getId()))
		.andExpect(jsonPath("$.user.cardNumber").value(user.getCardNumber()));

		 Mockito.verify(serviceUser, Mockito.times(1)).create(user);
		 //Mockito.verifyNoMoreInteractions(serviceUser);

	}
	
	//Domain Layer
	@Test
	public void testFindAllSuccessfully() throws Exception {
		User u1 = new User();
		u1.setId(1L);
		u1.setCardNumber("82719181");

		User u2 = new User();
		u2.setId(2L);
		u1.setCardNumber("72719187");
		
		List<User> users = Arrays.asList(u1, u2);
		
		Mockito.when(serviceUser.findAll()).thenReturn(users);
		
		List<User> result = controllerUser.findAll();
		
		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0).getId()).isEqualTo(u1.getId());
		assertThat(result.get(0).getCardNumber()).isEqualTo(u1.getCardNumber());
		assertThat(result.get(1).getId()).isEqualTo(u2.getId());
		assertThat(result.get(1).getCardNumber()).isEqualTo(u2.getCardNumber());
	}
	
	//Web Layer
	@Test
	public void testFindAll() throws Exception {
		mockMvc.perform(get("/api/users"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
		
		Mockito.verify(serviceUser, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(serviceUser);
	}
	
	@Test
	public void testFindById() throws Exception {
	}
	
	@Test
	public void testUpdate() throws Exception {
	}
	
	@Test
	public void testDelete() throws Exception {
	
	}
	
}
