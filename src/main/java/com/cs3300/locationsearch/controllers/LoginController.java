package com.cs3300.locationsearch.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cs3300.locationsearch.model.Account;
import com.cs3300.locationsearch.services.AccountService;


@Controller
public class LoginController {
	
	
	
	@RequestMapping("/submitLoginInfo/")
	public boolean loginUser(@RequestParam(required = true) String username, 
			@RequestParam(required = true) String pass) {
		
		Account account = new Account();
		account.setEmail("Not needed for login");
		account.setPassword(pass);
		account.setUsername(username);
		
		AccountService serv = new AccountService();
		boolean logged = serv.checkUser(account);
		return logged;
		
	}

}