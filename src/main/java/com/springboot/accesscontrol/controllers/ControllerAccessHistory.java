package com.springboot.accesscontrol.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	IServiceUser userService;
	IServiceAccessHistory accessHistory;
	
	@Autowired
	public ControllerAccessHistory(IServiceUser userService, IServiceAccessHistory accessHistory) {
		this.userService = userService;
		this.accessHistory = accessHistory;		
	}
	
	@GetMapping("/assistance")
	public ModelAndView usuario(ModelAndView model) {
		model.addObject("titulo", "Mantenedor de asistencias");
		model.setViewName("assistance");
		return model;
	}
	
	@GetMapping("/assistances")
	public List<AccessHistory> findAll(){
		return this.accessHistory.findAll();
	}
	
	@GetMapping("/assistances/{id}")
	public List<AccessHistory> findAllById(@PathVariable Long id){
		//log.info(accessHistory.findAll().toString());
		return this.accessHistory.findAllByIdUser(id);
	}
	
	@PostMapping("/access")
	public ResponseEntity<?> create(@Valid @RequestBody AccessHistory access, BindingResult result) {	
		log.info(access.getUser().getCardNumber());
		
		User userActual = null;
		AccessHistory accessActual = null;
		Map<String, Object> response = new HashMap<>();
		String message = (access.getUser().getCardNumber() == null) 
				? "The field 'cardNumber' must not be null": (access.getUser().getCardNumber().isEmpty() 
						? "The field 'cardNumber' must not be empty" : access.getUser().getCardNumber().equals("null")
						? "The field 'cardNumber' must not be null": "The field 'cardNumber' is null");
		
		LocalDateTime datetime = LocalDateTime.now().withNano(0);
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//String formattedDateTime = datetime.format(formatter);
		//datetime = LocalDateTime.parse(formattedDateTime, formatter).withNano(0);
		Timestamp timestamp =Timestamp.valueOf(datetime);
		System.out.println("Fecha: " + timestamp + ", Movimiento:" + access.getMovimiento() + ", Card number: "+access.getUser().getCardNumber());
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "The field '"+ err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		//System.out.println("Fecha: '" + datetime.toString() + "', Movimiento: '" + access.getMovimiento() + "', Card number: '"+ userRegistrated.getCardNumber()+"'");
		
		if (access.getUser().getCardNumber() == null) {
			response.put("mensaje", message);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} else {		
			switch(access.getUser().getCardNumber()) {
				case "null": 
					response.put("mensaje", message);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
				case "": 
					response.put("mensaje", message);
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
				default:
				;		
			}
			
			try {
				System.out.println("Fecha: " + datetime.toString() + ", Movimiento:" + access.getMovimiento() + ", Card number: "+access.getUser().getCardNumber());
				userActual = this.userService.findByCardNumberIs(access.getUser().getCardNumber());
				accessActual = new AccessHistory();
				accessActual.setUser(userActual);
				accessActual.setAccessAt(timestamp);
				accessActual.setMovimiento(access.getMovimiento());
			} catch(DataAccessException e) {
					response.put("mensaje", "Error executing user database searching");
					response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			
			if (userActual == null) {
				response.put("mensaje", "The user cardNumber: '" + access.getUser().getCardNumber() +"' was not found!");
			} else {
				response.put("user", accessActual);
				this.accessHistory.create(accessActual);
				response.put("mensaje", "Assistance of user ".concat(userActual.getCardNumber()).concat(" was registrated successfully!"));
			}
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}		
	}
	
}
