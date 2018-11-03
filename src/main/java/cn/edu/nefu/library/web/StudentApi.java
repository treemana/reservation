package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.ErrorMessage;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.TokenUtil;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.service.ReservationAreaService;
import cn.edu.nefu.library.service.ReservationTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    private final ReservationTimeService reservationTimeService;

    @Autowired
    public StudentApi(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public RestData getStartTime(HttpServletRequest request) {
        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            Map<String, Object> data = reservationTimeService.getStartTime();
            return new RestData(data);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }
}
