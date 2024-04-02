package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: sky-take-out-backend
 * @description: 定时任务类
 * @author: MichaelLong
 * @create: 2024-04-02 22:56
 **/
@Component
@Slf4j
public class MyTask {

    /**
     * @Description: 每五秒执行一次
     * @Param: []
     * @return: void
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    public void executeTask() {
        log.info("定时任务执行{}", new Date());
    }

}
