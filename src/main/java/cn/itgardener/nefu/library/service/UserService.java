/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.service;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @author : Jimi,PC
 * @date : 2018/10/27
 * @since : Java 8
 */
public interface UserService {

    /**
     * 用户登陆
     *
     * @param user 用户实体
     * @return 结果集
     * @throws LibException 异常信息
     */
    Map<String, Object> postLogin(User user) throws LibException;

    /**
     * 查询黑名单
     *
     * @return 黑名单
     * @throws LibException 异常信息
     */
    List<Map<String, Object>> getBlackList() throws LibException;

    /**
     * 添加黑名单
     *
     * @param user 用户实体
     * @return 修改数据库的记录数
     */
    int postAddBlackList(User user);

    /**
     * 删除黑名单
     *
     * @param user 用户的id信息
     * @return 返回是否删除成功
     * @throws LibException 异常信息
     */
    boolean deleteBlackListByStudentId(User user) throws LibException;

    /**
     * 当前用户查询排队状态
     *
     * @param userVo 用户实体
     * @return 前面还有多少人
     */
    int getStatus(UserVo userVo) throws LibException;

    /**
     * 上传 xlsx 导入学生
     *
     * @param fileName 上传后的文件名
     * @return RestData
     */
    RestData postStudent(String fileName);

    /**
     * 删除现有的学生账户
     */
    void deleteStudent();
}
