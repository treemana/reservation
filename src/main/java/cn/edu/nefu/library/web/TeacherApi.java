package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.ErrorMessage;
import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.TokenUtil;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.VO.TimeVO;
import cn.edu.nefu.library.service.ReservationTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    private final ReservationTimeService reservationTimeService;

    @Autowired
    public TeacherApi(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @RequestMapping(value = "/open-time", method = RequestMethod.GET)
    public RestData getReservationTime(HttpServletRequest request){
        logger.info("get reservationTime");
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try{
                final Map<String, String> reservationTime = reservationTimeService.getReservationTime();
                logger.info("get get reservationTime success");
                return new RestData(reservationTime);

            } catch ( LibException e) {
                return new RestData(e.getMessage());
            }
        }
    }
    @RequestMapping(value = "/open-time", method = RequestMethod.PUT)
    public RestData putReservationTime(@RequestBody TimeVO timeVO, HttpServletRequest request){
        logger.info("put reservationTime"+timeVO.getEndTime()+timeVO.getStartTime());
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try{
              return new RestData(reservationTimeService.putReservationTime(timeVO));
            } catch (LibException e){
                return new RestData(1,e.getMessage());
            }

        }

    }
}
