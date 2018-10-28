package cn.edu.nefu.library.service.impl;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.util.TokenUtil;
import cn.edu.nefu.library.core.mapper.UserMapper;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Jimi
 * @date : 2018/10/27
 * @since : Java 8
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public Map<String, Object> postLogin(User user) throws LibException {
        Map<String, Object> rtv = null;
        List<User> users = userMapper.selectByCondition(user);
        if (null != users && 1 == users.size()) {
            user = users.get(0);
            if (user.getType() == 2) {
                throw new LibException("当前用户已被禁用!");
            } else {
                user.setToken(TokenUtil.getToken());
                if (0 < userMapper.updateTokenBySystemId(user)) {
                    rtv = new HashMap<>();
                    user = users.get(0);
                    rtv.put("systemId", user.getSystemId());
                    rtv.put("token", user.getToken());
                    rtv.put("type", user.getType());
                }
            }
        } else {
            throw new LibException("用户名或密码不正确!");
        }
        return rtv;
    }
}
