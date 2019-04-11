/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.web.api;

import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.JsonUtil;
import cn.itgardener.nefu.library.common.util.VerifyUtil;
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

    @Autowired
    public StudentApi(ReservationService reservationService, BookCaseService bookCaseService, UserService userService) {
        this.reservationService = reservationService;
        this.bookCaseService = bookCaseService;
        this.userService = userService;
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public RestData getStartTime() {
        logger.info("GET getStartTime");

        Map<String, Object> data = reservationService.getAllTime();
        return new RestData(data);
    }

    @RequestMapping(value = "/box-order", method = RequestMethod.POST)
    public RestData postBoxOrder(@RequestBody BookCaseVo bookCaseVo, HttpServletRequest request) {
        logger.info("POST postBoxOrder : " + JsonUtil.getJsonString(bookCaseVo));

        try {
            VerifyUtil.verify(request.getHeader("token"));
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage());
        }

        try {
            return new RestData(bookCaseService.postBoxOrder(bookCaseVo));
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public RestData getStatus(UserVo userVo) {
        logger.info("GET getStatus : " + JsonUtil.getJsonString(userVo));

        try {
            return new RestData(userService.getStatus(userVo));
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/area-status", method = RequestMethod.GET)
    public RestData getAreaStatus(BookCaseVo bookCaseVo) {
        logger.info("GET getAreaStatus:" + bookCaseVo.getStudentId());
        String studentId = bookCaseVo.getStudentId();
        String floor = String.valueOf(bookCaseVo.getFloor());
        if (null == studentId || null == floor) {
            return new RestData("请输入学号和楼层");
        }
        try {
            return new RestData(reservationService.getAreaStatus(studentId, floor));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new RestData(1, "获取区域预约状态失败");
    }

    @RequestMapping(value = "/open-area", method = RequestMethod.GET)
    public RestData getReservationArea(@RequestParam int floor) {
        logger.info("GET getReservationArea");
        try {
            List<Map<String, String>> reservationArea = reservationService.getReservationArea(floor);
            return new RestData(reservationArea);
        } catch (LibException e) {
            logger.info(e.getMessage());
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/info/{studentId}", method = RequestMethod.GET)
    public RestData getLocation(@PathVariable(value = "studentId") String studentId) {
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

    @RequestMapping(value = "/num/{floor}", method = RequestMethod.GET)
    public RestData getNum(@PathVariable(value = "floor") String floor) {
        logger.info("GET getNum");
        List<Map<String, Object>> data = bookCaseService.getBagNum(floor);
        return new RestData(data);
    }

}
