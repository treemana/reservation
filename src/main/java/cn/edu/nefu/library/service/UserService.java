package cn.edu.nefu.library.service;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.model.User;

import java.util.Map;

/**
 * @author : Jimi
 * @date : 2018/10/27
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
}
