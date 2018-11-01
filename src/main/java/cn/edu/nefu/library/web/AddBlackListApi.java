package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.JsonUtil;
import cn.edu.nefu.library.core.mapper.UserMapper;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : CMY
 * @date : 2018/10/28
 * @since : Java 8
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("api")
@RestController
public class AddBlackListApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    @Autowired
    public AddBlackListApi(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestData postLogin(@RequestBody User user) {
        logger.info("POST postAddBlackApi : " + JsonUtil.getJsonString(user));
        try {
            Map<String, Object> data = userService.postAddBlackList(user);
            return new RestData(null);
        } catch (LibException e) {
            return new RestData(1, e.getMessage());
        }
    }
}
