package cn.edu.nefu.library.common.util;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.mapper.ConfigMapper;
import cn.edu.nefu.library.core.mapper.UserMapper;
import cn.edu.nefu.library.core.model.Config;
import cn.edu.nefu.library.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author : Jimi
 * @date : 2018/11/12
 * @since : Java 8
 */
@Component
public class VerifyUtil {

    private static  UserMapper userMapper;
    private static ConfigMapper configMapper;

    @Autowired
    public VerifyUtil(UserMapper userMapper, ConfigMapper configMapper) {
        VerifyUtil.userMapper = userMapper;
        VerifyUtil.configMapper = configMapper;
    }

    public static boolean verify(String token) throws LibException,Exception{
        List<Config> configList = configMapper.selectOpenAera();
        User user = new User();
        user.setToken(token);
        List<User> users = userMapper.selectByCondition(user);
        String startTime = null;
        String endTime = null;
        int startGrade = 0;
        int endGrade = 0;

        int userGrade = Integer.parseInt(users.get(0).getStudentId().substring(0,4));
        System.out.println(userGrade);

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
        String nowTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Config config : configList) {
            if(config.getConfigKey().equals("startTime")) {
               startTime = config.getConfigValue();
            } else if(config.getConfigKey().equals("endTime")) {
               endTime = config.getConfigValue();
            } else if(config.getConfigKey().equals("startGrade")) {
               startGrade =Integer.parseInt(config.getConfigValue());
            } else if(config.getConfigKey().equals("endGrade")) {
               endGrade =Integer.parseInt(config.getConfigValue());
            }
        }

        if (userGrade < startGrade || userGrade > endGrade) {
            if (startGrade == endGrade) {
                throw new LibException("仅对" + startGrade + "级开放!");
            } else {
                throw new LibException("仅对" + startGrade + "-" + endGrade + "级开放!");
            }
        } else {
            Date nowDate = format.parse(nowTime);
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);

            long now = nowDate.getTime();
            long start = startDate.getTime();
            long end = endDate.getTime();
            if(now < start || now >end) {
                throw new LibException("未到开放时间");
            }
        }

        return true;

    }

}

