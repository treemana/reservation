/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.web.api;

import cn.itgardener.nefu.library.common.ErrorMessage;
import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.JsonUtil;
import cn.itgardener.nefu.library.core.mapper.RedisDao;
import cn.itgardener.nefu.library.core.mapper.UserMapper;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.service.UserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author : Jimi
 * @date : 2018/10/27
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class LoginApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final DefaultKaptcha defaultKaptcha;
    private final UserMapper userMapper;
    private final RedisDao redisDao;

    @Autowired
    public LoginApi(UserService userService, DefaultKaptcha defaultKaptcha, UserMapper userMapper, RedisDao redisDao) {
        this.userService = userService;
        this.defaultKaptcha = defaultKaptcha;
        this.userMapper = userMapper;
        this.redisDao = redisDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestData postLogin(@RequestBody User user) {
        logger.info("POST postLogin : " + JsonUtil.getJsonString(user));

        if (null == user.getStudentId() || 1 > user.getStudentId().length() ||
                null == user.getStudentName() || 1 > user.getStudentName().length()) {
            return new RestData(1, ErrorMessage.PARAMATER_ERROR);
        }

        try {
            Map<String, Object> data = userService.postLogin(user);
            return new RestData(data);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public RestData getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("GET getCode");

        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        User user = userMapper.selectByCondition(new User(httpServletRequest.getHeader("token"))).get(0);
        try {
            //生产验证码字符串并保存到redis中
            String createText = defaultKaptcha.createText();
            logger.info(createText);
            redisDao.pushHash("code", user.getStudentId(), createText);

            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (Exception e) {
            try {
                httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e1) {
                logger.warn(e1.getLocalizedMessage());
            }
            return new RestData(1, ErrorMessage.SYSTEM_ERROR);
        }

        //定义response输出类型为image/jpeg类型,使用base64输出流输出图片的byte数组
        byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        String apache = new String(Base64.encodeBase64(captchaChallengeAsJpeg));
        return new RestData(apache);
    }
}
