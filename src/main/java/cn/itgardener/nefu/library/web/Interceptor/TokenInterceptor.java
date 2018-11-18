package cn.itgardener.nefu.library.web.Interceptor;

import cn.itgardener.nefu.library.common.ErrorMessage;
import cn.itgardener.nefu.library.common.LibException;
import cn.itgardener.nefu.library.common.RestData;
import cn.itgardener.nefu.library.common.util.JsonUtil;
import cn.itgardener.nefu.library.common.util.TokenUtil;
import cn.itgardener.nefu.library.core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author CMY
 * @date 2018/11/17
 * @since : Java 8
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws LibException {
        String path = request.getServletPath();
        logger.info("get TokenInterceptor");
        User user = TokenUtil.getUserByToken(request);
        boolean result = path.contains("login");
        if (result) {
            return true;
        } else {
            if (null == user) {
                logger.info("token验证失败，请重新登陆");
                try {
                    responseJson(response, new RestData(2, ErrorMessage.PLEASE_RELOGIN));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            } else {
                logger.info("token验证成功");
                return true;
            }
        }
    }

    private void responseJson(HttpServletResponse response, RestData restData) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JsonUtil.getJsonString(restData));
        response.flushBuffer();
        writer.close();

    }
}
