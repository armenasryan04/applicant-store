package com.samplemission.collectcvsfromgoogledrive.scheduler;

import com.samplemission.collectcvsfromgoogledrive.service.googledrive.FileSyncService;
import com.samplemission.collectcvsfromgoogledrive.service.googledrive.impl.FileSyncServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileSyncScheduler {

    private final FileSyncService fileSyncService;

    @Scheduled(cron = "0 0 0 * * ?") // Запуск раз в  день
    public void syncFiles() {
        try {
            fileSyncService.syncFiles();
            System.out.println("Файлы синхронизированы.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
