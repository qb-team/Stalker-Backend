package it.qbteam.config;

import java.io.IOException;
import java.io.InputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class FirebaseAdminConfig {

    @Value("${GOOGLE_APPLICATION_CREDENTIALS}")
    private String serviceAccountKeyFile;

    @Value("${FIREBASE_DATABASE_URL}")
    private String databaseUrl;

    @Bean
    FirebaseApp createFirebaseApp() throws IOException {
        Resource firebaseConfigFile = new ClassPathResource(serviceAccountKeyFile);
        InputStream serviceAccount = firebaseConfigFile.getInputStream();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl).build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    FirebaseAuth createFirebaseAuth(final FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
