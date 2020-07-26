/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service.schedule;

import cn.itgardener.nefu.library.service.BookCaseService;
import cn.itgardener.nefu.library.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author : Jimi
 * @date : 2018/11/1
 * @since : Java 8
 */
@Component
public class ScheduledTask {

    private Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    private final RedisService redisService;
    private final BookCaseService bookCaseService;

    @Autowired
    public ScheduledTask(RedisService redisService, BookCaseService bookCaseService) {
        this.redisService = redisService;
        this.bookCaseService = bookCaseService;
    }

    /**
     * fixedDelay = 1000表示当前方法开始执行1000ms后,Spring scheduling会再次调用该方法
     */
    @Scheduled(fixedDelay = 1000)
    public void doPopList() {
        String studentId;
        while (true) {
            logger.debug("ScheduledTask : 查询排队队列");
            studentId = redisService.popQueue();

            if (null != studentId) {
                bookCaseService.boxQueue(studentId);
                logger.debug("ScheduledTask : {}已经分配完毕", studentId);
                continue;
            }

            if (redisService.getFreeTotal() > redisService.getSetSize()) {
                break;
            }

            // todo 判断 set 数量,新添加一个查询 size 的方法

        }
    }
}
