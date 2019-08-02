package com.firebasespring.pe.FirebaseSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;

@SpringBootApplication
public class FirebaseSpringApplication {
	static String FB_BASE_URL="";

	public static void main(String[] args) {
		SpringApplication.run(FirebaseSpringApplication.class, args);
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials
							.fromStream(new ClassPathResource("/firebase-authentication.json").getInputStream()))
					.setDatabaseUrl("https://testauthtoken-cb10a.firebaseio.com").build();
			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
