/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import cn.itgardener.nefu.library.core.model.vo.LocationVo;
import cn.itgardener.nefu.library.core.model.vo.ShipVo;

import java.util.List;
import java.util.Map;

/**
 * @author : chenchenT CMY
 * @date : 2018/10/28
 * @since : Java 8
 */
public interface BookCaseService {

    /**
     * 获取我的书包柜信息
     *
     * @param user 用户学号
     * @return 书包柜位置和编号
     * @throws LibException 是否有柜子
     */
    Map<String, Object> getLocationByUserId(User user) throws LibException;


    /**
     * 根据Id设置预留的书包柜
     *
     * @param bookCaseVo
     * @return 是否预留成功
     */
    RestData setKeepById(BookCaseVo bookCaseVo);

    /**
     * 根据number设置预留的书包柜
     *
     * @param bookCaseVo
     * @return 是否预留成功
     */
    RestData setKeepByNumber(BookCaseVo bookCaseVo);

    /**
     * 修改单个关系
     *
     * @param shipVO 书包柜的id和学号
     * @return 是否修改成功
     * @throws LibException 异常信息
     */
    boolean putShip(ShipVo shipVO) throws LibException;

    /**
     * 清空/删除关系
     *
     * @param data 书包柜编号数组
     * @return 是否清除成功
     */
    RestData deleteShip(List<Integer> data);

    /**
     * 获取书包柜数量
     *
     * @return 书包柜位置和数量的列表
     */
    List<Map<String, Object>> getBagNum(String floor);

    /**
     * 根据条件获取书包柜详情
     *
     * @param bookCaseVo 书包柜实体
     * @return 书包柜详情
     */
    RestData selectDetailByCondition(BookCaseVo bookCaseVo);

    /**
     * 统一处理参数
     *
     * @param bookCaseVo 书包柜实体
     * @return bookCaseVo
     */
    String processParameter(BookCaseVo bookCaseVo);

    /**
     * 预约书包柜
     *
     * @param bookCaseVo
     * @return 是否成功
     */
    boolean postBoxOrder(BookCaseVo bookCaseVo) throws LibException;

    /**
     * redis书包柜队列
     *
     * @param studentId
     */
    void boxQueue(String studentId);

    /**
     * redis 队列出队
     *
     * @return
     */
    String popQueue();

    /**
     * 删除书包柜
     *
     * @param bookCaseVo vo
     * @return restdata
     * @throws LibException 异常
     */

    RestData deleteBookcaseById(BookCaseVo bookCaseVo) throws LibException;

    /**
     * 按照编号删除书包柜
     *
     * @param bookCaseVo list
     * @return restdata
     * @throws LibException 异常
     */
    RestData deleteBookcaseByNumber(BookCaseVo bookCaseVo) throws LibException;

    /**
     * 批量增加柜子
     *
     * @param bookCaseVo
     * @return 结果
     */
    boolean addBookcase(BookCaseVo bookCaseVo);

    /**
     * 增加区域
     *
     * @param locationVo 实例
     * @return Boolean
     */
    boolean addLocation(LocationVo locationVo);

    /**
     * 删除区域
     *
     * @param location location
     * @return restData
     * @throws LibException exp
     */

    RestData deleteLocation(String location) throws LibException;
}
