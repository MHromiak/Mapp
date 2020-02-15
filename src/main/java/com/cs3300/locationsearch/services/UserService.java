package com.cs3300.locationsearch.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cs3300.locationsearch.model.User;
import com.cs3300.locationsearch.repositories.UserRepository;

public class UserService {
	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;
	
	public UserService() {
		this(new BCryptPasswordEncoder(), new UserRepository());
	}
	
	public UserService(BCryptPasswordEncoder encoder, UserRepository userRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
	}
	
	// TODO:
	// * method to  check if user from input = user from database (during login stage)
	// * method to check if user already exists in database (during sign up stage)
	//		-- this would need to check username and email. only have username check rn
	
	/**
	 * Given a user with username, password, email,
	 * check if user already exists, if email already exists, then
	 * encrypts the password and then adds the user to the database.
	 * @param user
	 */
	public void addUser(User user) {
		user.setPassword(encryptPassword(user.getPassword()));
		try {
			if (userRepo.getUserByEmail(user.getEmail()) == null
					&& userRepo.getUserByUsername(user.getUsername())) {
				userRepo.addUser(user);
			} else {
				// user already exists...
				throw new Exception("User or email already exists.");
			}
		} catch (Exception e) {
			// we can add actual logging later
			System.out.println("Failed to add user");
			e.printStackTrace();
		}
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