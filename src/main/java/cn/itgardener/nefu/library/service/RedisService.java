/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service;

import java.util.List;

/**
 * @author : Hunter
 * @date : 19-4-15 下午3:40
 * @since : Java 8
 */
public interface RedisService {

    /**
     * 同步MySQL和Redis
     */
    boolean updateRedis();

    /**
     * @return 此次可预约柜子总数
     */
    int getTotal();

    /**
     * 将学生添加到排队队列
     *
     * @param studentId 学号
     * @return 是否添加成功
     */
    boolean pushQueue(String studentId);

    /**
     * 将学号添加到 set 并计算大小
     *
     * @param studentId 学号
     * @return 结果集
     */
    List<Integer> addAndSize(String studentId);

    /**
     * 将学生从去重 set 中移除
     *
     * @param studentId 学号
     */
    void totalSetRemove(String studentId);

    /**
     * 添加目标位置记录
     *
     * @param studentId 学号
     * @param location  位置
     */
    void addLocation(String studentId, String location);

    /**
     * 根据学号获取位置
     *
     * @param stucentId 学号
     * @return 位置
     */
    String getLocation(String stucentId);

    /**
     * 存储验证码
     *
     * @param studentId 学号
     * @param captcha   验证码
     */
    void putCaptcha(String studentId, String captcha);

    /**
     * 获取验证码
     *
     * @param studentId 学号
     * @return 验证码
     */
    String getCaptcha(String studentId);
}
