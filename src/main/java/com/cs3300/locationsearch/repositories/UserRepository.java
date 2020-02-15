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
import com.cs3300.locationsearch.model.User;

public class UserRepository {
	private FirebaseOptions options;
	private Firestore db;
	
	// TODO: add method to get users by email
	// BEFORE RUNNING, ENSURE YOU HAVE LOMBOK INSTALLED AND SET UP YOUR ENVIRONMENT VARIABELS
	// ENVIRONMENT VARIABLES NEED PROJECT_ID AND SERVICE ACCOUNT KEY
	
	/**
	 * Initialize the database.
	 */
	public UserRepository() {		
		try {
			this.options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(new FileInputStream(System.getenv("SERVICE_ACCOUNT"))))
					.setProjectId(System.getenv("PROJECT_ID"))
					.build();
			System.out.println(System.getenv("PROJECT_ID") + " ;; " + System.getenv("SERVICE_ACCOUNT"));
		} catch (Exception e) {
			System.out.println("Failed to initialize firestore options. Invalid credentials may have been provided");
			e.printStackTrace();
		}
		FirebaseApp.initializeApp(options);
		this.db = FirestoreClient.getFirestore();
	}
	
	/**
	 * Add user to database
	 * @param user
	 * @throws Exception
	 */
	public void addUser(User user) throws Exception {
		DocumentReference docRef = db.collection("users").document(user.getUsername());
		Map<String, Object> data = new HashMap<>();
		data.put("username", user.getUsername());
		data.put("password", user.getPassword());
		data.put("email", user.getEmail());
		ApiFuture<WriteResult> result = docRef.set(data);
		System.out.println("Update time : " + result.get().getUpdateTime());
	}
	
	/**
	 * Get a specific user from the database (by Username)
	 * @param user
	 * @return a User with details if found or null if no user is found
	 * @throws Exception
	 */
	public User getUserByUsername(User user) throws Exception {
		DocumentReference docRef = db.collection("users").document(user.getUsername());
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			User userFound = document.toObject(User.class);
			return userFound;
		} else {
			return null;
		}
	}
	
	/**
	 * Gets all users by an email - it returns on the first user found with
	 * a specific email. Otherwise, it will return null if no user is found.
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User getUserByEmail(User user) throws Exception {
		CollectionReference users = db.collection("users");
		Query query = users.whereEqualTo("email", user.getEmail());
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		for (DocumentSnapshot document: querySnapshot.get().getDocuments()) {
			return document.toObject(User.class);
		}
		return null;
	}
	
	/**
	 * Get all users in the database.
	 * @return List<User>
	 * @throws Exception
	 */
	public List<User> getAllUsers() throws Exception {
		ApiFuture<QuerySnapshot> query = db.collection("users").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		List<User> users = new ArrayList<>();
		for (QueryDocumentSnapshot document : documents) {
			User user = document.toObject(User.class);
			users.add(user);
		}
		return users;
	}
}
