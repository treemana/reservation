/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.core.model.vo.GradeVo;
import cn.itgardener.nefu.library.core.model.vo.TimeVo;

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
     * @return
     */
    List<Map<String, String>> getReservationArea() throws LibException;

    /**
     * 修改开始结束年级
     *
     * @param gradeVO
     * @throws LibException
     */
    boolean postGrade(GradeVo gradeVO) throws LibException;

    /**
     * 修改
     * @param list dasd
     * @return dasd
     * @throws LibException sdas
     */
    /**
     * 修改预约区域
     *
     * @param list
     * @return
     * @throws LibException
     */
    Boolean putReservationArea(List<Integer> list) throws LibException;

    /**
     * 查询预约时间
     *
     * @return map
     */
    Map<String, String> getReservationTime() throws LibException;

    /**
     * 修改预约时间
     *
     * @return 是否修改着成功
     * @throws LibException 异常
     */
    RestData putReservationTime(TimeVo timeVO);

    /**
     * 查询开放时间
     *
     * @return
     */
    Map<String, Object> getStartTime();

    /**
     * 获取开放年级
     *
     * @return {"startGrade":2016,"endGrade":2018}
     */
    Map<String, Object> getOpenGrade();

    /**
     * 获取各个区域的预约状态
     *
     * @return [0, 1, 1, 0] 0表示可以预约，1表示不可预约
     */
    List<Integer> getAreaStatus(String studentId) throws LibException;
}
