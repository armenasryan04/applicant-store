package com.samplemission.collectcvsfromgoogledrive.service.googledrive.impl;

import com.samplemission.collectcvsfromgoogledrive.service.googledrive.FileSyncService;
import com.samplemission.collectcvsfromgoogledrive.service.googledrive.GoogleDriveService;
import com.samplemission.collectcvsfromgoogledrive.service.googledrive.MinioService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileSyncServiceImpl implements FileSyncService {

    private final GoogleDriveService googleDriveService;
    private final MinioService minioService;


    public void syncFiles() throws Exception {
        Set<String> googleDriveFiles = googleDriveService.getGoogleDriveFiles();
        Set<String> minioFiles = minioService.getMinioFiles();
        // файлы, которых нет в MinIO
        Set<String> filesToSync = getFilesToSync(googleDriveFiles, minioFiles);
        // Асинхронная загрузка отсутствующих файлов
        extracted(filesToSync);
    }

    private void extracted(Set<String> filesToSync) {
        filesToSync.parallelStream().forEach(fileId -> {
            try (InputStream fileContent = googleDriveService.downloadFile(fileId)) {
                String fileName = googleDriveService.getFileName(fileId);
                minioService.uploadFile(fileName, fileContent);
                System.out.println("Файл " + fileName + " синхронизирован.");
            } catch (Exception e) {
                System.err.println("Ошибка при синхронизации файла " + fileId + ": " + e.getMessage());
            }
        });
    }

    @NotNull
    private Set<String> getFilesToSync(Set<String> googleDriveFiles, Set<String> minioFiles) {
        Set<String> filesToSync = googleDriveFiles.stream()
                .filter(fileId -> {
                    try {
                        String fileName = googleDriveService.getFileName(fileId);
                        return !minioFiles.contains(fileName);
                    } catch (IOException e) {
                        System.err.println("Ошибка получения имени файла: " + e.getMessage());
                        return false;
                    }
                }).collect(Collectors.toSet());
        return filesToSync;
    }


}