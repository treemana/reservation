/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.common.util;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.core.mapper.BookCaseMapper;
import cn.itgardener.nefu.library.core.mapper.ConfigMapper;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static cn.itgardener.nefu.library.common.GlobalConst.USER_ADMIN;
import static cn.itgardener.nefu.library.common.GlobalConst.USER_OTHER_ADMIN;

/**
 * @author : Jimi
 * @date : 2018/11/12
 * @since : Java 8
 */
@Component
public class VerifyUtil {

    private static UserMapper userMapper;
    private static ConfigMapper configMapper;
    private static BookCaseMapper bookCaseMapper;

    @Autowired
    public VerifyUtil(UserMapper userMapper, ConfigMapper configMapper, BookCaseMapper bookCaseMapper) {
        VerifyUtil.userMapper = userMapper;
        VerifyUtil.configMapper = configMapper;
        VerifyUtil.bookCaseMapper = bookCaseMapper;
    }

    public static boolean verify(String token) throws LibException, ParseException {
        List<Config> configList = configMapper.selectOpenArea();
        User user = new User();
        user.setToken(token);
        List<User> users = userMapper.selectByCondition(user);
        String startTime = null;
        String endTime = null;
        int startGrade = 0;
        int endGrade = 0;

        int userGrade = Integer.parseInt(users.get(0).getStudentId().substring(0, 4));

        String nowTime = TimeUtil.getCurrentTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Config config : configList) {
            if ("startTime".equals(config.getConfigKey())) {
                startTime = config.getConfigValue();
            } else if ("endTime".equals(config.getConfigKey())) {
                endTime = config.getConfigValue();
            }
        }

        Date nowDate = format.parse(nowTime);
        Date startDate = format.parse(startTime);
        Date endDate = format.parse(endTime);

        long now = nowDate.getTime();
        long start = startDate.getTime();
        long end = endDate.getTime();
        if (now < start || now > end) {
            throw new LibException("未到开放时间");
        }

        return true;
    }

    public static boolean verifyTime() throws LibException {
        String nowTime = TimeUtil.getCurrentTime();

        List<Config> configs = configMapper.selectOpenTime();
        long startTime = 0L;
        long endTime = 0L;
        long currentTime;
        for (Config config2 : configs) {
            if ("startTime".equals(config2.getConfigKey())) {
                try {
                    startTime = dateToStamp(config2.getConfigValue());
                } catch (ParseException e) {
                    throw new LibException("时间转化出现异常");
                }
            }
            if ("endTime".equals(config2.getConfigKey())) {
                try {
                    endTime = dateToStamp(config2.getConfigValue());
                } catch (ParseException e) {
                    throw new LibException("时间转化出现异常");
                }
            }
        }
        try {
            currentTime = dateToStamp(nowTime);
        } catch (ParseException e) {
            throw new LibException("时间转化出现异常");
        }

        if (startTime < currentTime && endTime > currentTime) {
            throw new LibException("当前时间段无法修改");
        }
        return true;
    }

    public static boolean verifyType(HttpServletRequest request) {
        String token = request.getHeader("token");
        User user = new User();
        user.setToken(token);
        User user1 = userMapper.selectByToken(user);
        if (USER_ADMIN == user1.getType() || USER_OTHER_ADMIN == user1.getType()) {
            return true;
        }
        return false;
    }

    private static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }

    public static boolean verifyArea(BookCaseVo bookCaseVo) {
        String location = bookCaseVo.getFloor() + "_" + bookCaseVo.getArea();
        List<Config> configs = bookCaseMapper.selectConfigByLocation(location);
        return 0 != configs.size();

    }

}

