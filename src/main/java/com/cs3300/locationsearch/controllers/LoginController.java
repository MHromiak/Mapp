package com.cs3300.locationsearch.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs3300.locationsearch.model.Account;
import com.cs3300.locationsearch.services.AccountService;


@Controller
@RequestMapping("/submitLoginInfo/")
public class LoginController {
	
	@Autowired
	AccountService accService;
	
	/**
	 * Microservice that gives status of user account in database
	 * 
	 * endpoint format: /submitLoginInfo?username={}&password={}
	 * 
	 * @param username
	 * @param pass
	 * @return true if user is in database and can be logged in. False otherwise
	 */
	
	public boolean loginUser(@RequestParam(value="username", required = true) String username, 
			@RequestParam(value="password", required = true) String pass) {
		
		Account account = new Account();
		account.setEmail("Not needed for login");
		account.setPassword(pass);
		account.setUsername(username);
		
		boolean logged = accService.checkUser(account);
		return logged;
		
	}

}