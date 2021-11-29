package fpt.toeic.config;


import fpt.toeic.service.LogService;

import java.util.ArrayList;

public class LogConfig extends ThreadConfig {
    @Override
    public void doProcess(ArrayList items) {
        LogService logService = new LogService();
        logService.setItems(items);
        executorService.submit(logService);
    }
}
