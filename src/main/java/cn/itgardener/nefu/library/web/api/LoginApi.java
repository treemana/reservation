/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.web.api;

import cn.itgardener.nefu.library.common.ErrorMessage;
import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.JsonUtil;
import cn.itgardener.nefu.library.common.util.TokenUtil;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.service.UserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

    @Autowired
    public LoginApi(UserService userService, DefaultKaptcha defaultKaptcha) {
        this.userService = userService;
        this.defaultKaptcha = defaultKaptcha;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestData postLogin(@RequestBody User user) {
        logger.info("POST postLogin : " + JsonUtil.getJsonString(user));
        try {
            Map<String, Object> data = userService.postLogin(user);
            return new RestData(data);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }


    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public RestData getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaChallengeAsJpeg;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        User currentUser = TokenUtil.getUserByToken(httpServletRequest);
        if (null != currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try {
                //生产验证码字符串并保存到session中
                String createText = defaultKaptcha.createText();
                System.out.println(createText);
                httpServletRequest.getSession().setAttribute("vrifyCode", createText);

                //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
                BufferedImage challenge = defaultKaptcha.createImage(createText);
                ImageIO.write(challenge, "jpg", jpegOutputStream);
            } catch (IllegalArgumentException e) {
                httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            }

            //定义response输出类型为image/jpeg类型，使用base64输出流输出图片的byte数组
            captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            return new RestData(encoder.encode(captchaChallengeAsJpeg));
        }
    }


    @RequestMapping(value = "/vrifycode/{vrifyCode}", method = RequestMethod.GET)
    public RestData vrifyCode(@PathVariable(value = "vrifyCode") String vrifyCode, HttpServletRequest httpServletRequest) {


        User currentUser = TokenUtil.getUserByToken(httpServletRequest);
        if (null != currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            String captchaId = (String) httpServletRequest.getSession().getAttribute("vrifyCode");

            if (captchaId.equals(vrifyCode)) {
                return new RestData("请求成功");
            } else {
                return new RestData(1, "验证码错误");
            }
        }

    }
}
