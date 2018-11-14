/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper.provider;

import cn.itgardener.nefu.library.core.model.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author : Jimi,PC
 * @date : 2018/10/27,10/28
 * @since : Java 8
 */
public class UserProvider {

    public String selectByCondition(User user) {
        return new SQL() {
            {
                SELECT("user_system_id AS systemId, user_username AS studentId, user_password AS studentName, " +
                        "user_type AS type, user_token AS token");
                FROM("user");
                if (null != user.getSystemId()) {
                    WHERE("user_system_id=#{systemId}");
                }
                if (null != user.getStudentId()) {
                    WHERE("user_username=#{studentId}");
                }
                if (null != user.getStudentName()) {
                    WHERE("user_password=#{studentName}");
                }
                if (null != user.getType()) {
                    WHERE("user_type=#{type}");
                }
                if (null != user.getToken()) {
                    WHERE("user_token=#{token}");
                }

            }
        }.toString();
    }

    public String selectByType() {
        return new SQL() {
            {
                SELECT("user_username AS studentId, user_password AS studentName");
                FROM("user");
                WHERE("user_type=2");
            }

        }.toString();

    }

    public String deleteBlackListByStudentId(User user) {
        return new SQL() {
            {
                UPDATE("user");
                SET("user_type=0");
                WHERE("user_username=#{studentId}");

            }
        }.toString();
    }
}
