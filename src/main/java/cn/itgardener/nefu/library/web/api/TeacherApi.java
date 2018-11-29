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
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import cn.itgardener.nefu.library.core.model.vo.GradeVo;
import cn.itgardener.nefu.library.core.model.vo.ShipVo;
import cn.itgardener.nefu.library.core.model.vo.TimeVo;
import cn.itgardener.nefu.library.service.BookCaseService;
import cn.itgardener.nefu.library.service.ReservationService;
import cn.itgardener.nefu.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : pc
 * @date : 2018/10/31
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class TeacherApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationService reservationService;
    private final BookCaseService bookCaseService;
    private final UserService userService;

    @Autowired
    public TeacherApi(ReservationService reservationService, BookCaseService bookCaseService, UserService userService) {
        this.reservationService = reservationService;
        this.bookCaseService = bookCaseService;
        this.userService = userService;
    }

    @RequestMapping(value = "/open-time", method = RequestMethod.GET)
    public RestData getReservationTime(HttpServletRequest request) {
        logger.info("GET getReservationTime");

        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try {
                final Map<String, String> reservationTime = reservationService.getReservationTime();
                logger.info("get get reservationTime success");
                return new RestData(reservationTime);

            } catch (LibException e) {
                return new RestData(e.getMessage());
            }
        }
    }

    @RequestMapping(value = "/open-time", method = RequestMethod.PUT)
    public RestData putReservationTime(@RequestBody TimeVo timeVO, HttpServletRequest request) {
        logger.info("PUT putReservationTime" + JsonUtil.getJsonString(timeVO));

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, ErrorMessage.OPERATIOND_ENIED);
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            return reservationService.putReservationTime(timeVO);
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public RestData getDetail(BookCaseVo bookCaseVo, HttpServletRequest request) {
        logger.info("GET getDetail");

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, ErrorMessage.OPERATIOND_ENIED);
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            return bookCaseService.selectDetailByCondition(bookCaseVo);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/ship", method = RequestMethod.PUT)
    public RestData putShip(@RequestBody ShipVo shipVO, HttpServletRequest request) {
        logger.info("PUT putShip: " + JsonUtil.getJsonString(shipVO));

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, ErrorMessage.OPERATIOND_ENIED);
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try {
                return new RestData(bookCaseService.putShip(shipVO));
            } catch (LibException e) {
                return new RestData(1, e.getMessage());
            }
        }
    }

    @RequestMapping(value = "/ship", method = RequestMethod.DELETE)
    public RestData deleteShip(@RequestBody List<Integer> data, HttpServletRequest request) {
        logger.info("DELETE deleteShip: " + JsonUtil.getJsonString(data));

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, ErrorMessage.OPERATIOND_ENIED);
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            return bookCaseService.deleteShip(data);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/preorder", method = RequestMethod.POST)
    public RestData postKeep(@RequestBody List<Integer> data, HttpServletRequest request) {
        logger.info("POST postKeep: " + JsonUtil.getJsonString(data));

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, ErrorMessage.OPERATIOND_ENIED);
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            return bookCaseService.setKeepByNumber(data);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestData getBlackList(HttpServletRequest request) {
        logger.info("GET getBlackList");

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
        }
        User token = TokenUtil.getUserByToken(request);
        if (null == token) {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
        try {
            List<Map<String, Object>> blackList = userService.getBlackList();
            return new RestData(blackList);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/list/{studentId}", method = RequestMethod.DELETE)
    public RestData deleteBlackList(@PathVariable String studentId, HttpServletRequest request) {
        logger.info("DELETE deleteBlackList : studentId = " + studentId);

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, ErrorMessage.OPERATIOND_ENIED);
        }

        User user = new User();
        user.setStudentId(studentId);

        try {
            return new RestData(userService.deleteBlackListByStudentId(user));
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestData postLogin(@RequestBody User user, HttpServletRequest request) {
        logger.info("POST postAddBlackApi : " + JsonUtil.getJsonString(user));

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
        }
        if (1 == userService.postAddBlackList(user)) {
            return new RestData(null);
        } else {
            return new RestData(1, "添加黑名单失败");
        }
    }

    @RequestMapping(value = "/open-area", method = RequestMethod.PUT)
    public RestData putReservationArea(@RequestBody List<Integer> list) {
        logger.info("PUT putReservationArea: " + list.toString());

        try {
            VerifyUtil.verifyTime();
            return new RestData(reservationService.putReservationArea(list));
        } catch (Exception e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "open-grades", method = RequestMethod.PUT)
    public RestData postGrade(@RequestBody GradeVo gradeVO, HttpServletRequest request) {
        logger.info("PUT postGrade : " + JsonUtil.getJsonString(gradeVO));

        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, ErrorMessage.OPERATIOND_ENIED);
        }

        try {
            VerifyUtil.verifyTime();
            boolean result = reservationService.postGrade(gradeVO);
            if (result) {
                return new RestData(true);
            } else {
                return new RestData(1, "postGrade is failure");
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new RestData(1, e.getMessage());
        }
    }

}
