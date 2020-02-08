package com.cs3300.locationsearch;

import java.io.IOException;

import org.junit.*;

import com.cs3300.locationsearch.services.PasswordService;

public class PasswordServiceTest {
	
  PasswordService passService;
	
  @Before
  public void setUp() {
	  this.passService = new PasswordService();
  }
  
  @After
  public void tearDown() {
	  passService = null;
  }
  
  /**
   * Check to ensure that the hash password is not the same as the string.
   * @throws IOException
   */
  @Test
  public void testPassNotSameAsHash() throws IOException {
	String pass = "test12";
	String hashed = passService.getHash(pass);
	Assert.assertNotEquals(pass, hashed);
  }
  
  /**
   * Checks to see if password matches the hash.
   * @throws IOException
   */
  @Test
  public void testPassMatchesHash() throws IOException {
	  String pass = "test12";
	  String hashed = passService.getHash(pass);
	  Assert.assertTrue(passService.isMatching(pass, hashed));
  }
}
