/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper;

import cn.itgardener.nefu.library.core.mapper.provider.UserProvider;
import cn.itgardener.nefu.library.core.model.BookCase;
import cn.itgardener.nefu.library.core.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Jimi
 * @date : 2018/10/27
 * @since : Java 8
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据条件筛选
     *
     * @param user
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectByCondition")
    List<User> selectByCondition(User user);

    /**
     * 设置token
     *
     * @param user
     * @return
     */
    @Update("UPDATE user SET user_token=#{token} WHERE user_system_id=#{systemId}")
    int updateTokenBySystemId(User user);

    /**
     * 查询用户类别
     *
     * @return
     */
    @SelectProvider(type = UserProvider.class, method = "selectByType")
    List<User> selectByType();


    /**
     * 删除黑名单t
     *
     * @param user
     * @return
     */
    @UpdateProvider(type = UserProvider.class, method = "deleteBlackListByStudentId")
    int deleteBlackListByStudentId(User user);

    /**
     * 添加黑名单
     *
     * @param user
     * @return
     */
    @Update("UPDATE user SET user_type=2 WHERE user_username=#{studentId}")
    int updateTypeByStudentId(User user);

    /**
     * @param bookCase
     * @return
     */
    @Select("SELECT user_system_id AS systemId, user_username AS studentId, user_password AS studentName, " +
            "user_type AS type, user_token AS token from user where user_system_id=#{userId}")
    User selectByUserId(BookCase bookCase);

    /**
     * 根据studentId查出user
     *
     * @param studentId
     * @return List<User>
     */
    @SelectProvider(type = UserProvider.class, method = "selectByStudentId")
    List<User> selectByStudentId(@Param("studentId") String studentId);

    /**
     * 根据token查找类型
     * @param user token
     * @return user
     */
    @SelectProvider(type = UserProvider.class, method = "selectByToken")
    User selectByToken(User user);
}
