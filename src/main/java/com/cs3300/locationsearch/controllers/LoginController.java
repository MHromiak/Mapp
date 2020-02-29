package com.cs3300.locationsearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cs3300.locationsearch.model.Account;
import com.cs3300.locationsearch.services.AccountService;


@Controller
public class LoginController {
    
    @Autowired
    AccountService accService;
    
    /**
     * Microservice that gives status of user account in database
     * 
     * endpoint format: /submitLoginInfo?username={}&password={}
     * 
     * @param username
     * @param password
     * @return true if user is in database and can be logged in. False otherwise
     */
    @PostMapping(value="/submitLoginInfo")
    @ResponseBody
    public boolean loginUser(@RequestParam(value="username", required = true) String username, 
            @RequestParam(value="password", required = true) String password) {
        
        Account account = new Account();
        account.setPassword(password);
        account.setUsername(username);
        
        boolean logged = accService.checkUser(account);
        return logged;
        
    }

}
