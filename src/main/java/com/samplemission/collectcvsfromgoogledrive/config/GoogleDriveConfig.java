package com.samplemission.collectcvsfromgoogledrive.config;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Configuration
public class GoogleDriveConfig {
    @Value("${google.cloud.credentials.path}")
    private String filePath;

    @Bean
    public Drive googleDrive() throws IOException, GeneralSecurityException {
        // Устанавливаем HttpTransport и JsonFactory
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();


        File credentialsFile = new File(filePath);
        if (!credentialsFile.exists()) {
            throw new FileNotFoundException("credentials.json file not found at: " + credentialsFile.getAbsolutePath());
        }

        // Используем GoogleCredential для совместимости с Google API Client
        InputStream credentialsStream = new FileInputStream(credentialsFile);
        // Используем GoogleCredential для совместимости с Google API Client
        GoogleCredential credential = GoogleCredential.fromStream(credentialsStream, transport, jsonFactory)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        // Создаем экземпляр Google Drive
        return new Drive.Builder(transport, jsonFactory, credential)
                .setApplicationName("GoogleDriveMinioSync")
                .build();
    }
}
