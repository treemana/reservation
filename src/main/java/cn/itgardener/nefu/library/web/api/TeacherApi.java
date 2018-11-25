/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.web.api;

import cn.itgardener.nefu.library.common.ErrorMessage;
import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.TokenUtil;
import cn.itgardener.nefu.library.common.util.VerifyUtil;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import cn.itgardener.nefu.library.core.model.vo.ShipVo;
import cn.itgardener.nefu.library.core.model.vo.TimeVo;
import cn.itgardener.nefu.library.service.BookCaseService;
import cn.itgardener.nefu.library.service.ReservationService;
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

    @Autowired
    public TeacherApi(ReservationService reservationService, BookCaseService bookCaseService) {
        this.reservationService = reservationService;
        this.bookCaseService = bookCaseService;
    }

    @RequestMapping(value = "/open-time", method = RequestMethod.GET)
    public RestData getReservationTime(HttpServletRequest request) {
        logger.info("get reservationTime");
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1,"您没有访问权限");
        }
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
        logger.info("put reservationTime" + timeVO.getEndTime() + timeVO.getStartTime());
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try{
                VerifyUtil.verifyTime();

            } catch (Exception e){
                return new RestData(1,e.getMessage());
            }
            return  reservationService.putReservationTime(timeVO);
        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public RestData getDetail(BookCaseVo bookCaseVo, HttpServletRequest request) {
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
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
        logger.info("put Ship");
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
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
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            return bookCaseService.deleteShip(data);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/preorder", method = RequestMethod.POST)
    public RestData setKeep(@RequestBody List<Integer> data, HttpServletRequest request) {
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
        }
        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            return bookCaseService.setKeepByNumber(data);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

}
