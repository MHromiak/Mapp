package com.cs3300.locationsearch.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cs3300.locationsearch.model.Account;
import com.cs3300.locationsearch.repositories.AccountRepository;

public class AccountService {
	private BCryptPasswordEncoder encoder;
	private AccountRepository accRepo;

	public AccountService() {
		this(new BCryptPasswordEncoder(), new AccountRepository());
	}

	public AccountService(BCryptPasswordEncoder encoder, AccountRepository accRepo) {
		this.encoder = encoder;
		this.accRepo = accRepo;
	}

	/**
	 * Given a user with username, password, email, check if user already exists, if
	 * email already exists, then encrypts the password and then adds the user to
	 * the database.
	 * 
	 * @param account
	 */
	public void addUser(Account account) {
		account.setPassword(encryptPassword(account.getPassword()));
		try {
			if (accRepo.getAccountByEmail(account) == null && accRepo.getAccountByUsername(account) == null) {
				accRepo.addAccount(account);
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
	
	public boolean checkUser(Account account) {
		try {
			Account user = accRepo.getAccountByUsername(account);
			return passwordMatchesHash(user.getPassword(), encryptPassword(account.getPassword()));	
		} catch (Exception e) {
			System.out.println("Failed to check user");
			
		}
		return false;
		
	}
	

	
	

	/**
	 * Encodes password. This uses bcrypt encryption as well as randomized salt.
	 * 
	 * @param password
	 * @return
	 */
	public String encryptPassword(String password) {
		return this.encoder.encode(password);
	}

	/**
	 * Checks if password string matches the hashed password
	 * 
	 * @param pass
	 * @param hash
	 * @return
	 */
	public boolean passwordMatchesHash(String pass, String hash) {
		return encoder.matches(pass, hash);
	}
}