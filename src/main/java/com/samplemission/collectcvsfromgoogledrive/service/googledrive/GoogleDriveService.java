package com.samplemission.collectcvsfromgoogledrive.service.googledrive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface GoogleDriveService {
    // Получаем список файлов из GoogleDrive
    Set<String> getGoogleDriveFiles() throws IOException;
    // Загрузка файла по id
    InputStream downloadFile(String fileId) throws IOException;
    // Получаем имя файла
    String getFileName(String fileId) throws IOException;

}
