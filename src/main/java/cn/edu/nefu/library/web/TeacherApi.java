package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.ErrorMessage;
import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.TokenUtil;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.vo.BookCaseVo;
import cn.edu.nefu.library.core.model.vo.ShipVO;
import cn.edu.nefu.library.core.model.vo.TimeVO;
import cn.edu.nefu.library.service.BookCaseService;
import cn.edu.nefu.library.service.ReservationService;
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
    public RestData putReservationTime(@RequestBody TimeVO timeVO, HttpServletRequest request) {
        logger.info("put reservationTime" + timeVO.getEndTime() + timeVO.getStartTime());
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            boolean bool = reservationService.putReservationTime(timeVO);
            if(bool) {
                return new RestData(true);
            }else {
                return new RestData(1,"修改失败");
            }


        }
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public RestData getDetail(BookCaseVo bookCaseVo, HttpServletRequest request) {

        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            return bookCaseService.selectDetailByCondition(bookCaseVo);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/ship", method = RequestMethod.PUT)
    public RestData putShip(@RequestBody ShipVO shipVO,HttpServletRequest request){
        logger.info("put Ship");
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            logger.info(ErrorMessage.PLEASE_RELOGIN);
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {
            try{
                return new RestData(bookCaseService.putShip(shipVO));
            } catch ( LibException e){
                return new RestData(1, e.getMessage());
            }
        }
    }

    @RequestMapping(value = "/ship", method = RequestMethod.DELETE)
    public RestData deleteShip(@RequestBody List<Integer> data, HttpServletRequest request) {

        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            return bookCaseService.deleteShip(data);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/preorder", method = RequestMethod.POST)
    public RestData setKeep(@RequestBody List<Integer> data, HttpServletRequest request) {

        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            return bookCaseService.setKeepByNumber(data);
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

}
