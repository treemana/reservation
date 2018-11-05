package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.ErrorMessage;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.JsonUtil;
import cn.edu.nefu.library.common.util.TokenUtil;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.vo.BookCaseVo;
import cn.edu.nefu.library.service.BookCaseService;
import cn.edu.nefu.library.service.ReservationService;
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


    @Autowired
    public StudentApi(ReservationService reservationService, BookCaseService bookCaseService) {
        this.reservationService = reservationService;
        this.bookCaseService = bookCaseService;
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
}
