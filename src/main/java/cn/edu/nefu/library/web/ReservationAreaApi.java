package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.ErrorMessage;
import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.TokenUtil;

import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.service.ReservationAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author : pc
 * @date : 2018/10/30
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class ReservationAreaApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationAreaService reservationAreaService;

    @Autowired
    public ReservationAreaApi(ReservationAreaService reservationAreaService) {
        this.reservationAreaService = reservationAreaService;
    }

    @RequestMapping(value = "/open-area", method = RequestMethod.GET)
    public RestData getReservationArea(HttpServletRequest request) {
        logger.info("get reservationArea");
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(1, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try{
                List<Integer> reservationArea = reservationAreaService.getReservationArea();
                return new RestData(reservationArea);
            } catch (LibException e){
                logger.info(e.getMessage());
                return new RestData(1,e.getMessage());
            }
        }

   }
}
