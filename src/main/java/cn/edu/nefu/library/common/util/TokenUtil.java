/*
 * Copyright © 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.edu.nefu.library.common.util;

import cn.edu.nefu.library.core.mapper.UserMapper;
import cn.edu.nefu.library.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhengyi on 17-7-29.
 */
@Component
public class TokenUtil {

    private static UserMapper userMapper;

    @Autowired
    public void setManagerMapper(UserMapper userMapper) {
        TokenUtil.userMapper = userMapper;
    }

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
}
