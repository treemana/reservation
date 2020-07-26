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
    int getFreeTotal();

    /**
     * 将学生添加到排队队列
     *
     * @param studentId 学号
     * @return 是否添加成功
     */
    boolean pushQueue(String studentId);

    /**
     * 获取一个排队的学生
     *
     * @return 学号
     */
    String popQueue();

    /**
     * 将学生添加到随机队列
     *
     * @param studentId 学号
     * @return 是否添加成功
     */
    boolean pushRandom(String studentId);

    /**
     * 获取一个随机预约的学生
     *
     * @return 学号
     */
    String popRandom();

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
     * @return 去重队列总数
     */
    int getSetSize();

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
     * @param studentId 学号
     * @return 位置
     */
    String getLocation(String studentId);

    /**
     * 指定区域总数 -1
     *
     * @param location 区域
     * @return 操作后的值
     */
    int decrLocation(String location);

    /**
     * 指定区域总数 +1
     *
     * @param location 区域
     * @return 操作后的值
     */
    boolean incrLocation(String location);

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
