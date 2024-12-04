package com.samplemission.collectcvsfromgoogledrive.service.googledrive.impl;

import com.samplemission.collectcvsfromgoogledrive.service.googledrive.FileSyncService;
import com.samplemission.collectcvsfromgoogledrive.service.googledrive.GoogleDriveService;
import com.samplemission.collectcvsfromgoogledrive.service.googledrive.MinioService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(FileSyncServiceImpl.class);

    public void syncFiles() throws Exception {
        logger.info("синхронизация началaсь!");
        Set<String> googleDriveFiles = googleDriveService.getGoogleDriveFiles();
        Set<String> minioFiles = minioService.getMinioFiles();
        Set<String> filesToDelete = getFilesToDelete(minioFiles);
        Set<String> filesToSync = getFilesToSync(googleDriveFiles, minioFiles);
        extracted(filesToSync);
        deleteExtra(filesToDelete);
        logger.info("синхронизация завершена!");
    }


    private void extracted(Set<String> filesToSync) {
        filesToSync.parallelStream().forEach(fileId -> {
            try (InputStream fileContent = googleDriveService.downloadFile(fileId)) {
                String fileName = googleDriveService.getFileName(fileId);
                minioService.uploadFile(fileName, fileContent);
                logger.info("Файл " + fileName + " синхронизирован.");
            } catch (Exception e) {
                logger.trace("Ошибка при синхронизации файла " + fileId + ": " + e.getMessage());
            }
        });

    }


    private void deleteExtra(Set<String> filesToDelete) {
        filesToDelete.parallelStream().forEach(fileToDelete -> {
            try {
                String fileName = fileToDelete;
                minioService.deleteFile(fileName);
                logger.info("Файл " + fileName + " удален!.");
            } catch (Exception e) {
                logger.trace("Ошибка при удаления файла " + fileToDelete + ": " + e.getMessage());
            }
        });
        logger.info("удаление лишних файлов завершен!");
    }

    @NotNull
    private Set<String> getFilesToSync(Set<String> googleDriveFiles, Set<String> minioFiles) {
        Set<String> filesToSync = googleDriveFiles.stream()
                .filter(fileId -> {
                    try {
                        String fileName = googleDriveService.getFileName(fileId);
                        return !minioFiles.contains(fileName);
                    } catch (IOException e) {
                        logger.trace(e.getMessage());
                        return false;
                    }
                }).collect(Collectors.toSet());
        return filesToSync;
    }

    @NotNull
    private Set<String> getFilesToDelete(Set<String> minioFiles) {
        Set<String> filesToDelete = minioFiles.stream()
                .filter(fileName -> {
                    try {
                        String fileId = googleDriveService.getFileIdByName(fileName);
                        return fileId == null;
                    } catch (IOException e) {
                        logger.trace(e.getMessage());
                        return false;
                    }
                }).collect(Collectors.toSet());
        return filesToDelete;
    }


}