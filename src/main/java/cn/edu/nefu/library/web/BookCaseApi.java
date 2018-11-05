package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.ErrorMessage;
import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.JsonUtil;
import cn.edu.nefu.library.common.util.TokenUtil;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.vo.BookCaseVo;
import cn.edu.nefu.library.service.BookCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author : chenchenT CMY
 * @date : 2018/10/28
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class BookCaseApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BookCaseService bookCaseService;

    @Autowired
    public BookCaseApi(BookCaseService bookCaseService) {
        this.bookCaseService = bookCaseService;
    }

    @RequestMapping(value = "/info/{studentId}", method = RequestMethod.GET)
    public RestData getLocation(@PathVariable(value = "studentId") String studentId, HttpServletRequest request) {
        User user = new User();
        user.setStudentId(studentId);
        logger.info("getLocation : " + JsonUtil.getJsonString(user));
        User currentUser = TokenUtil.getUserByToken(request);
        if (null != currentUser) {
            try {
                Map<String, Object> data = bookCaseService.getLocationByUserId(user);
                return new RestData(data);
            } catch (LibException e) {
                return new RestData(1, e.getMessage());
            }
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/preorder", method = RequestMethod.POST)
    public RestData setKeep(@RequestBody List<BookCase> bookCases, HttpServletRequest request) {

        User currentUser = TokenUtil.getUserByToken(request);
        int success = 0;
        if (null != currentUser) {
            for (BookCase bookCase : bookCases) {
                if (0 >= bookCaseService.setKeepByNumber(bookCase)) {
                    success = 1;
                    break;
                }
            }
            if (success == 0) {
                return new RestData(0, "操作成功!");
            } else {
                return new RestData(1, "操作失败,请重试!");
            }
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }

    @RequestMapping(value = "/ship", method = RequestMethod.DELETE)
    public RestData setShip(@RequestBody List<BookCase> bookCases, HttpServletRequest request) {

        User currentUser = TokenUtil.getUserByToken(request);
        int success = 0;
        if (null != currentUser) {
            for (BookCase bookCase : bookCases) {
                if (0 >= bookCaseService.updateShipByNumber(bookCase)) {
                    success = 1;
                    break;
                }
            }
            if (0 == success) {
                return new RestData(0, "操作成功!");
            } else {
                return new RestData(1, "操作失败,请重试!");
            }
        } else {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        }
    }


    @RequestMapping(value = "/num", method = RequestMethod.GET)
    public RestData getNum(HttpServletRequest request) {
        logger.info("getNum is running");
        User currentUser = TokenUtil.getUserByToken(request);
        if (null == currentUser) {
            return new RestData(2, ErrorMessage.PLEASE_RELOGIN);
        } else {

            try {
                List<Map<String, Object>> data = bookCaseService.getBagNum();
                return new RestData(data);
            } catch (LibException e) {
                return new RestData(1, e.getMessage());
            }
        }
    }

}
