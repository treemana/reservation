/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.core.model.vo.TimeVo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author : pc CMY chenchenT
 * @date : 2018/10/30
 * @since : Java 8
 */
public interface ReservationService {
    /**
     * 查询预约的区域
     *
     * @return 区域状态
     * @throws LibException 异常信息
     */
    List<Map<String, String>> getReservationArea(int floor) throws LibException;

    /**
     * 修改预约区域
     *
     * @param list 区域列表
     * @return 是否成功
     * @throws LibException 异常信息
     */
    boolean putReservationArea(List<Integer> list) throws LibException;

    /**
     * 查询预约时间
     *
     * @return map
     * @throws LibException 异常信息
     */
    Map<String, String> getReservationTime() throws LibException;

    /**
     * 修改预约时间
     *
     * @param timeVO 时间实体
     * @return 是否修改着成功
     */
    RestData putReservationTime(TimeVo timeVO);

    /**
     * 查询开放时间
     *
     * @return 开放时间
     */
    Map<String, Object> getStartTime();

    /**
     * 获取各个区域的预约状态
     *
     * @param studentId 学号
     * @param floor     楼层
     * @return [0, 1, 1, 0] 0表示可以预约,1表示不可预约
     * @throws ParseException 异常信息
     */
    List<HashMap<String, Object>> getAreaStatus(String studentId, String floor) throws ParseException;

    /**
     * 验证码校验
     *
     * @param verifyCode
     * @param studentId
     */
    void verifyCode(String verifyCode, String studentId) throws LibException;
}
