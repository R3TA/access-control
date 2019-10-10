package com.springboot.accesscontrol.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.accesscontrol.models.entity.User;
import com.springboot.accesscontrol.models.service.IServiceUser;

@RestController
@RequestMapping("/api")
public class ControllerUser {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IServiceUser userService;
	
	@GetMapping("/usuario")
	public ModelAndView usuario(ModelAndView model) {
		List<User> userList = userService.findAll();
		model.addObject("titulo", "Mantenedor de usuarios");
		model.addObject("users", userList);
		model.setViewName("usuario");
		return model;
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
		User userNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '"+ err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			userNew = userService.create(user);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error executing database insertion");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
		
		response.put("mensaje", "The user was created successfully!");
		response.put("user", userNew);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping({"/users"})
	public List<User> findAll(){
		List<User> userList = userService.findAll(); 
		//ModelAndView mav = new ModelAndView();
		//mav.addObject("titulo", "Mantenedor de usuarios");
		//mav.addObject("users", userList);
		//mav.setViewName("users");

		return userList;
	}
	
	@GetMapping({"/users/{id}"})
	public ResponseEntity<?> findById(@PathVariable Long id){
		User userActual = userService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		try {
			userActual = userService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error executing user database searching");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(userActual == null) {
			response.put("mensaje", "The user ID: '" + id +"' was not found!");
		}else {
			//response.put("mensaje", "The user was founded successfully!");
			response.put("user", userActual);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id){
		
		User userActual = userService.findById(id);
		User userUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '"+ err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(userActual == null) {
			response.put("mensaje", "Error: it's not possible edit, the user ID: ".concat(id.toString().concat(" was not founded in database!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			userActual.setCardNumber(user.getCardNumber());
			userUpdated = userService.create(userActual);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error executing database user updating");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		}
		
		response.put("mensaje", "The user was updated successfully!");
		response.put("user", userUpdated);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		User userActual = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			userActual = userService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error deleting user database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(userActual == null) {
			response.put("mensaje", "The user ID: '" + id +"' was not found!");
		}else {
			response.put("mensaje", "The user was deleted successfully!");
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
