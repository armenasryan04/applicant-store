package com.samplemission.collectcvsfromgoogledrive.scheduler;

import com.samplemission.collectcvsfromgoogledrive.service.googledrive.FileSyncService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileSyncScheduler {

    private final FileSyncService fileSyncService;
    Logger logger = LoggerFactory.getLogger(FileSyncScheduler.class);

    @Scheduled(cron = "0 * * * * ?") // Запуск раз в  день
    public void syncFiles() {
        try {

            fileSyncService.syncFiles();

        } catch (Exception e) {
            logger.trace(e.getStackTrace().toString());
        }
    }
}
