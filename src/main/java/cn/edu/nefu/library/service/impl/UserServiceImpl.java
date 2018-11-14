/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.edu.nefu.library.service.impl;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.util.TokenUtil;
import cn.edu.nefu.library.core.mapper.ConfigMapper;
import cn.edu.nefu.library.core.mapper.RedisDao;
import cn.edu.nefu.library.core.mapper.UserMapper;
import cn.edu.nefu.library.core.model.Config;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.vo.UserVo;
import cn.edu.nefu.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Jimi,pc CMY
 * @date : 2018/10/27
 * @since : Java 8
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserMapper userMapper;
    private final RedisDao redisDao;
    private final ConfigMapper configMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RedisDao redisDao, ConfigMapper configMapper) {
        this.userMapper = userMapper;
        this.redisDao = redisDao;
        this.configMapper = configMapper;
    }


    @Override
    public Map<String, Object> postLogin(User user) throws LibException {
        Map<String, Object> rtv = null;
        List<User> users = userMapper.selectByCondition(user);
        if (null != users && 1 == users.size()) {
            user = users.get(0);
            int type = user.getType();
            if (2 == type) {
                throw new LibException("当前用户已被禁用!");
            } else {
                if (0 == type) {

                    Config config = new Config();
                    config.setConfigKey("startGrade");
                    int startGrade = Integer.parseInt(configMapper.selectOpenGrade(config).getConfigValue());
                    config.setConfigKey("endGrade");
                    int endGrade = Integer.parseInt(configMapper.selectOpenGrade(config).getConfigValue());
                    int studentGrade = Integer.parseInt(user.getStudentId().substring(0, 4));
                    if (studentGrade < startGrade || studentGrade > endGrade) {
                        if (startGrade == endGrade) {
                            throw new LibException("仅对" + startGrade + "级开放!");
                        } else {
                            throw new LibException("仅对" + startGrade + "-" + endGrade + "级开放!");
                        }
                    }
                }
                user.setToken(TokenUtil.getToken());
                if (0 < userMapper.updateTokenBySystemId(user)) {
                    rtv = new HashMap<>(3);
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

    @Override
    public List<Map<String, Object>> getBlackList() throws LibException {
        List<Map<String, Object>> rtv = new ArrayList<>();
        List<User> users = userMapper.selectByType();
        if (null != users) {
            for (User user : users) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("studentId", user.getStudentId());
                map.put("name", user.getStudentName());
                rtv.add(map);
            }

        } else {
            throw new LibException("黑名单为空");
        }
        return rtv;
    }

    @Override
    public boolean deleteBlackListByStudentId(User user) {


        logger.info("delete BalckList,studentId： " + user.getStudentId());
        return 0 < userMapper.deleteBlackListByStudentId(user);


    }

    @Override
    public int getStatus(UserVo userVo) {
        if (!redisDao.isMember("finish", userVo.getStudentId())) {
            int currentCount = Integer.parseInt(redisDao.get("l_" + userVo.getStudentId()).split(",")[1]);
            int popCount = Integer.parseInt(redisDao.get("popCount"));
            return currentCount - popCount + 1;
        } else {
            return -1;
        }


    }

    @Override
    public Map<String, Object> postAddBlackList(User user) throws LibException {
        Map<String, Object> rtv = null;
        int x = userMapper.updateTypeByStudentId(user);
        if (x == 0) {
            throw new LibException("用户名不存在");
        }
        return rtv;
    }
}
