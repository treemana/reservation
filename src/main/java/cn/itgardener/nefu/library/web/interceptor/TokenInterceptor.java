package cn.itgardener.nefu.library.web.interceptor;

import cn.itgardener.nefu.library.common.ErrorMessage;
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
 * @author CMY, Hunter
 * @date 2018/11/17
 * @since : Java 8
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String path = request.getRequestURI();
        logger.info("get TokenInterceptor");

        boolean result = path.contains("login") || "OPTIONS".equals(request.getMethod());

        if (result) {
            return true;
        } else {
            User user = TokenUtil.getUserByToken(request);
            if (null == user) {
                logger.info("token验证失败，请重新登陆");
                try {
                    responseJson(response, new RestData(2, ErrorMessage.PLEASE_RELOGIN));
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage());
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
