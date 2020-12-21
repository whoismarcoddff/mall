package com.example.backend.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrderCancelTask {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderCancelTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime () {
//        LOGGER.info("The time is now {}", dateFormat.format(new Date()));
//    }
}
