package cn.edu.nefu.library.service.impl;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.mapper.ConfigMapper;
import cn.edu.nefu.library.core.model.Config;
import cn.edu.nefu.library.core.model.vo.TimeVO;
import cn.edu.nefu.library.service.ReservationTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : pc chenchenT
 * @date : 2018/10/30
 * @since : Java 8
 */
@Service
public class ReservationTimeServiceImpl implements ReservationTimeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConfigMapper configMapper;

    @Autowired
    public ReservationTimeServiceImpl(ConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    public Map<String, String> getReservationTime() throws LibException {
        Map<String, String> rtv = new HashMap<>(2);
        List<Config> configs = configMapper.selectOpenTime();
        if (null == configs) {
            throw new LibException("查询失败");
        } else {
            for (Config config : configs) {
                if (config.getConfigKey().equals("startTime")) {
                    rtv.put("startTime", config.getConfigValue());
                }
                if (config.getConfigKey().equals("endTime")) {
                    rtv.put("endTime", config.getConfigValue());
                }

            }
        }

        return rtv;
    }

    @Override
    public boolean putReservationTime(TimeVO timeVO) throws LibException {

        Config config = new Config();
        config.setConfigKey("startTime");
        config.setConfigValue(timeVO.getStartTime());
        Config config1 = new Config();
        config1.setConfigKey("endTime");
        config1.setConfigValue(timeVO.getEndTime());
        if (0 == configMapper.updateOpenTime(config) || 0 == configMapper.updateOpenTime(config1)) {
            throw new LibException("修改失败");
        }
        return 0 < configMapper.updateOpenTime(config) * configMapper.updateOpenTime(config1);

    }

    @Override
    public Map<String, Object> getStartTime() {

        Config config = configMapper.selectStartTime();
        String StartTime = config.getConfigValue();
        Date time = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        Map<String, Object> rtv = new HashMap<>(2);
        rtv.put("startTime", config.getConfigValue());
        rtv.put("nowTime", nowTime);
        return rtv;

    }

}
