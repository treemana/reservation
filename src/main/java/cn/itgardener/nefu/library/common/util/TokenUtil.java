/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.common.util;

import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * @author ZhengYi
 * @date 17-7-29
 */
@Component
public class TokenUtil {

    private static UserMapper userMapper;

    public static User getUserByToken(HttpServletRequest request) {
        User user = null;
        String token = request.getHeader("token");
        if (null != token) {
            User userCondition = new User();
            userCondition.setToken(token);
            List<User> users = userMapper.selectByCondition(userCondition);
            if (1 == users.size()) {
                user = users.get(0);
            }
        }
        return user;
    }

    public static String getPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public static String getToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Autowired
    public static void setUserMapper(UserMapper userMapper) {
        TokenUtil.userMapper = userMapper;
    }
}
