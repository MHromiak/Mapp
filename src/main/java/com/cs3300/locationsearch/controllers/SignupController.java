package com.cs3300.locationsearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs3300.locationsearch.model.Account;
import com.cs3300.locationsearch.model.ApiError;
import com.cs3300.locationsearch.services.AccountService;
import com.google.gson.Gson;

@Controller
public class SignupController {

	@Autowired
	AccountService accService;
	
	@RequestMapping("/signup")
	public String signup() {
		return "signup";
	}
	
	@PostMapping(value="/signup")
	@ResponseBody
	public String addAccount(@RequestParam("email") String email,
			@RequestParam("username") String username, @RequestParam("password") String password) {
		Account acc = new Account();
		acc.setEmail(email);
		acc.setUsername(username);
		acc.setPassword(password);
		try {
			accService.addUser(acc);
			return "success";
		} catch (Exception e) {
			ApiError apierror = new ApiError(HttpStatus.BAD_REQUEST, "Failed to add user: already exists!");
			Gson gson = new Gson();
			String error = gson.toJson(apierror);
			return error;
		}
	}
}
