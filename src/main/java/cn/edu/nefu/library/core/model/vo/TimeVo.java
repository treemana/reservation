/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.edu.nefu.library.core.model.vo;

/**
 * @author : pc
 * @date : 2018/10/30
 * @since : Java 8
 */
public class TimeVo {
    /**
     * 预约开始时间
     */
    private String startTime;
    /**
     * 预约结束时间
     */
    private String endTime;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
