package com.cs3300.locationsearch.repositories;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.cs3300.locationsearch.model.Account;

public class AccountRepository {
	private FirebaseOptions options;
	private Firestore db;

	// BEFORE RUNNING, ENSURE YOU HAVE LOMBOK INSTALLED AND SET UP YOUR ENVIRONMENT
	// VARIABELS
	// ENVIRONMENT VARIABLES NEED PROJECT_ID AND SERVICE ACCOUNT KEY

	/**
	 * Initialize the database.
	 */
	public AccountRepository() {
		try {
			this.options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(new FileInputStream(System.getenv("SERVICE_ACCOUNT"))))
					.setProjectId(System.getenv("PROJECT_ID")).build();
		} catch (Exception e) {
			System.out.println("Failed to initialize firestore options. Invalid credentials may have been provided");
			e.printStackTrace();
		}
		FirebaseApp.initializeApp(options);
		this.db = FirestoreClient.getFirestore();
	}

	/**
	 * Add user to database
	 * 
	 * @param account
	 * @throws Exception
	 */
	public void addAccount(Account account) throws Exception {
		DocumentReference docRef = db.collection("accounts").document(account.getUsername());
		Map<String, Object> data = new HashMap<>();
		data.put("username", account.getUsername());
		data.put("password", account.getPassword());
		data.put("email", account.getEmail());
		ApiFuture<WriteResult> result = docRef.set(data);
		System.out.println("Update time : " + result.get().getUpdateTime());
	}

	/**
	 * Get a specific user from the database (by Username)
	 * 
	 * @param account
	 * @return an Account with details if found or null if no user is found
	 * @throws Exception
	 */
	public Account getAccountByUsername(Account account) throws Exception {
		DocumentReference docRef = db.collection("accounts").document(account.getUsername());
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			Account userFound = document.toObject(Account.class);
			return userFound;
		} else {
			return null;
		}
	}

	/**
	 * Gets all account by an email - it returns on the first user found with a
	 * specific email. Otherwise, it will return null if no user is found.
	 * 
	 * @param account
	 * @return Account
	 * @throws Exception
	 */
	public Account getAccountByEmail(Account account) throws Exception {
		CollectionReference accounts = db.collection("accounts");
		Query query = accounts.whereEqualTo("email", account.getEmail());
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			return document.toObject(Account.class);
		}
		return null;
	}

	/**
	 * Get all users in the database.
	 * 
	 * @return List<Account>
	 * @throws Exception
	 */
	public List<Account> getAllAccounts() throws Exception {
		ApiFuture<QuerySnapshot> query = db.collection("accounts").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		List<Account> accounts = new ArrayList<>();
		for (QueryDocumentSnapshot document : documents) {
			Account account = document.toObject(Account.class);
			accounts.add(account);
		}
		return accounts;
	}
}
