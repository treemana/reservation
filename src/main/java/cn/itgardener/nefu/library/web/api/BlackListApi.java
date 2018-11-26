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
 * @date : 2018/10/28
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class BlackListApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public BlackListApi(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestData getBlackList(HttpServletRequest request) {
        if (!VerifyUtil.verifyType(request)) {
            return new RestData(1, "您没有访问权限");
        }
        logger.info("get BlackList");
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
        if (1 == userService.postAddBlackList(user))
            return new RestData(null);
        else
            return new RestData(1, "添加黑名单失败");
    }
}
