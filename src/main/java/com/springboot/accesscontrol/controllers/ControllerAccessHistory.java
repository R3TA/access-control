package com.springboot.accesscontrol.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.accesscontrol.models.entity.AccessHistory;
import com.springboot.accesscontrol.models.entity.User;
import com.springboot.accesscontrol.models.service.IServiceAccessHistory;
import com.springboot.accesscontrol.models.service.IServiceUser;

@RestController
@RequestMapping("/api")
public class ControllerAccessHistory {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IServiceUser userService;
	
	@Autowired
	IServiceAccessHistory accessHistory;
	
	@GetMapping("/assistance")
	public ModelAndView usuario(ModelAndView model) {
		List<AccessHistory> accessHistoryList = accessHistory.findAll();
		model.addObject("titulo", "Mantenedor de asistencias");
		model.addObject("assistances", accessHistoryList);
		model.setViewName("assistance");
		return model;
	}
	
	@PostMapping("/access")
	public ResponseEntity<?> findByCardNumber(@Valid @RequestBody AccessHistory access){	
		log.info(access.getUser().getCardNumber());
		User userActual = userService.findByCardNumberIs(access.getUser().getCardNumber());
		AccessHistory accessActual = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			userActual = userService.findByCardNumberIs(access.getUser().getCardNumber());
			
			LocalDateTime datetime = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDate = datetime.format(format);
			datetime = LocalDateTime.parse(formattedDate, format);
			log.info(datetime.toString() + " " + access.getMovimiento());
			accessActual = new AccessHistory(userActual, datetime, access.getMovimiento());
			accessHistory.create(accessActual);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error executing user database searching");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (userActual == null) {
			response.put("mensaje", "The user cardNumber: '" + access.getUser().getCardNumber() +"' was not found!");
		} else {
			response.put("mensaje", "Assistance of user ".concat(userActual.getCardNumber()).concat(" was registrated successfully!"));
			response.put("user", accessActual);
			accessHistory.create(accessActual);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
