package com.samplemission.collectcvsfromgoogledrive.service.googledrive.impl;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.File;
import com.samplemission.collectcvsfromgoogledrive.service.googledrive.GoogleDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GoogleDriveServiceImpl implements GoogleDriveService {
    @Value("${google.drive.folder.id}")
    private String folderId;
    private final Drive driveService;


    public Set<String> getGoogleDriveFiles() throws IOException {
        Set<String> googleDriveFiles = new HashSet<>();

        FileList result = driveService.files().list()
                .setQ(String.format("'%s' in parents and trashed = false", folderId))
                .setPageSize(1000) // Ограничение на количество файлов
                .setFields("files(id, name)") // Указываем, что хотим получать id и имя
                .execute();

        for (File file : result.getFiles()) {
            googleDriveFiles.add(file.getName());
        }
        return googleDriveFiles;
    }

    public InputStream downloadFile(String fileId) throws IOException {
        return driveService.files().get(fileId).executeMediaAsInputStream();
    }

    public String getFileName(String fileId) throws IOException {
        return driveService.files().get(fileId).setFields("name").execute().getName();
    }

    public String getFileIdByName(String fileName) throws IOException {
        FileList result = driveService.files().list()
                .setQ(String.format("name='%s' and trashed=false", fileName)) // Ищем файл с заданным именем, не в корзине
                .setFields("files(id, name)") // Указываем, что нам нужно ID и имя
                .execute();

        for (File file : result.getFiles()) {
            if (fileName.equals(file.getName())) {
                return file.getId(); // Возвращаем ID первого найденного файла
            }
        }
        return null; // Если файл не найден, возвращаем null
    }
}