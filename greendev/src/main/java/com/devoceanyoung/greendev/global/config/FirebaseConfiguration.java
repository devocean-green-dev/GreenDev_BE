package com.devoceanyoung.greendev.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfiguration {
    @PostConstruct
    public void init() {
        String path = "src/main/resources/greendev-admin-sdk.json";
        try (InputStream serviceAccount = new FileInputStream(path)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
