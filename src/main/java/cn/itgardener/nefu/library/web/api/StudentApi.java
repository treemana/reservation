/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.web.api;

import cn.itgardener.nefu.library.common.ErrorMessage;
import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.JsonUtil;
import cn.itgardener.nefu.library.common.util.TokenUtil;
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
import java.util.List;
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
    public RestData getStartTime() {
        logger.info("GET getStartTime");

        Map<String, Object> data = reservationService.getStartTime();
        return new RestData(data);
    }

    @RequestMapping(value = "/box-order", method = RequestMethod.POST)
    public RestData postBoxOrder(@RequestBody BookCaseVo bookCaseVo, HttpServletRequest request) {
        logger.info("POST postBoxOrder : " + JsonUtil.getJsonString(bookCaseVo));

        User user = userMapper.selectByCondition(new User(request.getHeader("token"))).get(0);
        String captchaId = redisDao.getHash("code",user.getStudentId());

        if (captchaId.equals(bookCaseVo.getVerifyCode())) {
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
                return new RestData(1, "排队失败,请重试");
            }
        } else {
            return new RestData(1, "验证码出错！");
        }
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public RestData getStatus(UserVo userVo, HttpServletRequest request) {
        logger.info("GET getStatus : " + JsonUtil.getJsonString(userVo));

        String captchaId = (String) request.getSession().getAttribute("verifyCode");
        if (captchaId.equals(userVo.getVerifyCode())) {
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

    @RequestMapping(value = "/open-area", method = RequestMethod.GET)
    public RestData getReservationArea() {
        logger.info("GET getReservationArea");
        try {
            List<Map<String, String>> reservationArea = reservationService.getReservationArea();
            return new RestData(reservationArea);
        } catch (LibException e) {
            logger.info(e.getMessage());
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "open-grades", method = RequestMethod.GET)
    public RestData getGrade(HttpServletRequest request) {
        logger.info("GET getGrade");
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, ErrorMessage.OPERATIOND_ENIED);
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try {
                Map rtv = reservationService.getOpenGrade();
                logger.info("getGrade  is  successful");
                return new RestData(rtv);
            } catch (Exception e) {
                logger.info(e.getMessage());
                return new RestData(1, e.getMessage());
            }
        }
    }

    @RequestMapping(value = "/info/{studentId}", method = RequestMethod.GET)
    public RestData getLocation(@PathVariable(value = "studentId") String studentId, HttpServletRequest request) {
        logger.info("GET getLocation : studentId=" + studentId);

        User user = new User();
        user.setStudentId(studentId);

        try {
            Map<String, Object> data = bookCaseService.getLocationByUserId(user);
            return new RestData(data);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/num", method = RequestMethod.GET)
    public RestData getNum() {
        logger.info("GET getNum");

        List<Map<String, Object>> data = bookCaseService.getBagNum();
        return new RestData(data);
    }

}
