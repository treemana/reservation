package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.ErrorMessage;
import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.JsonUtil;
import cn.edu.nefu.library.common.util.TokenUtil;
import cn.edu.nefu.library.common.util.VerifyUtil;
import cn.edu.nefu.library.core.mapper.RedisDao;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.vo.BookCaseVo;
import cn.edu.nefu.library.core.model.vo.UserVo;
import cn.edu.nefu.library.service.BookCaseService;
import cn.edu.nefu.library.service.ReservationService;
import cn.edu.nefu.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author : chenchenT
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
    private final RedisDao redisDao;
    private final UserService userService;


    @Autowired
    public StudentApi(ReservationService reservationService, BookCaseService bookCaseService, RedisDao redisDao, UserService userService) {
        this.reservationService = reservationService;
        this.bookCaseService = bookCaseService;
        this.redisDao = redisDao;
        this.userService = userService;
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public RestData getStartTime(HttpServletRequest request) {

        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            Map<String, Object> data = reservationService.getStartTime();
            return new RestData(data);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/box-order", method = RequestMethod.POST)
    public RestData postBoxOrder(@RequestBody BookCaseVo bookCaseVo, HttpServletRequest request) {
        logger.info("POST postBoxOrder : " + JsonUtil.getJsonString(bookCaseVo));

        try {
            VerifyUtil.verify(request.getHeader("token"));
        } catch (LibException e) {
           return new RestData(1, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }


        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {

            if(bookCaseService.postBoxOrder(bookCaseVo)) {
                return new RestData(true);
            }else {
                return new RestData(1,"排队失败，请重试");
            }
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public RestData getStatus(UserVo userVo, HttpServletRequest request){
        logger.info("GET getStatus : " + JsonUtil.getJsonString(userVo));

        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            String captchaId = (String) request.getSession().getAttribute("vrifyCode");
            if(captchaId.equals(userVo.getVrifyCode())) {
                if(userService.getStatus(userVo) != -1) {
                    return new RestData(userService.getStatus(userVo));
                } else {
                    return  new RestData(1, "用户已分配");
                }
            }else {
                return new RestData(1, "验证码出错！");
            }
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }
}
