package it.qbteam.config;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseAdminConfig {

    @Value("${GOOGLE_APPLICATION_CREDENTIALS}")
    private String serviceAccountKeyFile;

    @Value("${FIREBASE_DATABASE_URL}")
    private String databaseUrl;

    @Bean
    FirebaseApp createFirebaseApp() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(serviceAccountKeyFile);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl).build();

        return FirebaseApp.initializeApp(options);
    }
}