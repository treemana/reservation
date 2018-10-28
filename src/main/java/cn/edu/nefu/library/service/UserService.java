package cn.edu.nefu.library.service;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author : Jimi,PC
 * @date : 2018/10/27,10/28
 * @since : Java 8
 */
public interface UserService {

    /**
     * 用户登陆
     * @param user
     * @return
     * @throws LibException
     */
    Map<String, Object> postLogin(User user) throws LibException;

    /**
     * 查询黑名单
     * @return 黑名单
     * @throws LibException
     */
    List<Map<String, Object>> getBlackList() throws LibException;
}
