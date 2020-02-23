package com.cs3300.locationsearch;

import java.io.IOException;

import org.junit.*;

import com.cs3300.locationsearch.services.AccountService;

public class AccountServiceTest {

	AccountService accountService;

	@Before
	public void setUp() {
		this.accountService = new AccountService();
	}

	@After
	public void tearDown() {
		accountService = null;
	}

	/**
	 * Check to ensure that the hash password is not the same as the string.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPassNotSameAsHash() throws IOException {
		String pass = "test12";
		String hashed = accountService.encryptPassword(pass);
		Assert.assertNotEquals(pass, hashed);
	}

	/**
	 * Checks to see if password matches the hash.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPassMatchesHash() throws IOException {
		String pass = "test12";
		String hashed = accountService.encryptPassword(pass);
		Assert.assertTrue(accountService.passwordMatchesHash(pass, hashed));
	}
}
