package com.cs3300.locationsearch.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
	private BCryptPasswordEncoder encoder;
	
	public UserService () {
		this.encoder = new BCryptPasswordEncoder();
	}
	
	public UserService (BCryptPasswordEncoder encoder) {
		this.encoder = encoder;
	}
	
	/**
	 * Encodes password. This uses bcrypt encryption as well as randomized salt.
	 * @param password
	 * @return
	 */
	public String encryptPassword(String password) {
		return this.encoder.encode(password);
	}
	
	/**
	 * Checks if password string matches the hashed password
	 * @param pass
	 * @param hash
	 * @return
	 */
	public boolean passwordMatchesHash(String pass, String hash) {
		return encoder.matches(pass, hash);
	}
}