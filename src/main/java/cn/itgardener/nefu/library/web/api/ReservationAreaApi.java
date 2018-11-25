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
import cn.itgardener.nefu.library.core.model.vo.GradeVo;
import cn.itgardener.nefu.library.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : pc CMY
 * @date : 2018/10/30
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class ReservationAreaApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationService reservationService;

    @Autowired
    public ReservationAreaApi(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RequestMapping(value = "/open-area", method = RequestMethod.GET)
    public RestData getReservationArea(HttpServletRequest request) {
        logger.info("get reservationArea");
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try {
                List<Map<String, String>> reservationArea = reservationService.getReservationArea();
                logger.info("get reservationArea successful");
                return new RestData(reservationArea);
            } catch (LibException e) {
                logger.info(e.getMessage());
                return new RestData(1, e.getMessage());
            }
        }

    }

    @RequestMapping(value = "/open-area", method = RequestMethod.PUT)
    public RestData putReservationArea(@RequestBody List<Integer> list, HttpServletRequest request) {
        logger.info("put reservationArea" + list.toString());
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try {
                VerifyUtil.verifyTime();
                return new RestData(reservationService.putReservationArea(list));
            } catch (Exception e) {
                return new RestData(1, e.getMessage());
            }
        }
    }
    @RequestMapping(value = "open-grades", method = RequestMethod.PUT)
    public RestData postGrade(@RequestBody GradeVo gradeVO, HttpServletRequest request) {
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
        }
        logger.info("get postGrade" + JsonUtil.getJsonString(gradeVO));
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
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

    @RequestMapping(value = "open-grades", method = RequestMethod.GET)
    public RestData getGrade(HttpServletRequest request) {
        logger.info("getGrade is running");
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
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
}


