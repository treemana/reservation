/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.web.api;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.JsonUtil;
import cn.itgardener.nefu.library.common.util.VerifyUtil;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import cn.itgardener.nefu.library.core.model.vo.UserVo;
import cn.itgardener.nefu.library.service.BookCaseService;
import cn.itgardener.nefu.library.service.ReservationService;
import cn.itgardener.nefu.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

/**
 * @author : chenchenT CMY
 * @date : 2018/11/03
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class StudentApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationService reservationService;
    private final BookCaseService bookCaseService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final RedisDao redisDao;

    @Autowired
    public StudentApi(ReservationService reservationService, BookCaseService bookCaseService, UserService userService, UserMapper userMapper, RedisDao redisDao) {
        this.reservationService = reservationService;
        this.bookCaseService = bookCaseService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.redisDao = redisDao;
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public RestData getStartTime(HttpServletRequest request) {

        Map<String, Object> data = reservationService.getStartTime();
        return new RestData(data);
    }

    @RequestMapping(value = "/box-order", method = RequestMethod.POST)
    public RestData postBoxOrder(@RequestBody BookCaseVo bookCaseVo, HttpServletRequest request) {
        logger.info("POST postBoxOrder : " + JsonUtil.getJsonString(bookCaseVo));

        User user = userMapper.selectByCondition(new User(request.getHeader("token"))).get(0);
        String captchaId = redisDao.getHash("code",user.getStudentId());

        if (captchaId.equals(bookCaseVo.getVrifyCode())) {
            try {
                VerifyUtil.verify(request.getHeader("token"));
            } catch (LibException e) {
                return new RestData(1, e.getMessage());
            } catch (ParseException e) {
                logger.error(e.getLocalizedMessage());
            }

            if (bookCaseService.postBoxOrder(bookCaseVo)) {
                return new RestData(true);
            } else {
                return new RestData(1, "排队失败，请重试");
            }
        } else {
            return new RestData(1, "验证码出错！");
        }
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public RestData getStatus(UserVo userVo, HttpServletRequest request) {
        logger.info("GET getStatus : " + JsonUtil.getJsonString(userVo));

        String captchaId = (String) request.getSession().getAttribute("vrifyCode");
        if (captchaId.equals(userVo.getVrifyCode())) {
            if (userService.getStatus(userVo) != -1) {
                return new RestData(userService.getStatus(userVo));
            } else {
                return new RestData(1, "用户已分配");
            }
        } else {
            return new RestData(1, "验证码出错！");
        }
    }

    @RequestMapping(value = "/area-status/{studentId}", method = RequestMethod.GET)
    public RestData getAreaStatus(@PathVariable String studentId) {
        logger.info("GET getAreaStatus");
        try {
            return new RestData(reservationService.getAreaStatus(studentId));
        } catch (LibException e) {
            e.printStackTrace();
        }
        return new RestData(1, "获取区域预约状态失败");
    }
}
