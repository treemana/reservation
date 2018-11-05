package cn.edu.nefu.library.service.impl;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.mapper.ConfigMapper;
import cn.edu.nefu.library.core.model.Config;
import cn.edu.nefu.library.core.model.vo.GradeVO;
import cn.edu.nefu.library.core.model.vo.TimeVO;
import cn.edu.nefu.library.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : pc CMY
 * @date : 2018/10/30
 * @since : Java 8
 */
@Service
public class ReservationServiceImpl implements ReservationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConfigMapper configMapper;

    @Autowired
    public ReservationServiceImpl(ConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    public Boolean putReservationArea(List<Integer> list) throws LibException {
        int count = 0;
        for(int i=1;i<=4;i++ ){
            Config config = new Config();
            config.setSystemId(i);
            if(list.contains(i)){
                config.setConfigValue("1");
                count++;
            }else{
                config.setConfigValue("0");
            }
            configMapper.updateOpenArea(config);
        }
        if(count == list.size()){
            return true;
        }else{
            throw new LibException("修改失败");
        }

    }

    @Override
    public List<Map<String,String>> getReservationArea() throws LibException {

        List<Config> configs = configMapper.selectOpenAera();
        if (null == configs) {
            throw new LibException("预约区域为空");
        } else {

            List<Map<String,String>> rtv = new ArrayList<>(4);
            for (Config config : configs ) {
                Map<String ,String> map = new HashMap<>(3);
                if(config.getConfigKey().contains("area_")){
                    map.put("systemId", config.getSystemId().toString());
                    map.put("configKey", config.getConfigKey());
                    map.put("configValue", config.getConfigValue());
                    rtv.add(map);
                }

            }
            return rtv;


        }
    }
    @Override
    public boolean postGrade(GradeVO gradeVO) throws LibException {
        boolean rtv = false;
        Config config = new Config();
        config.setConfigKey("startGrade");
        config.setConfigValue(gradeVO.getStartGrade());
        int startGrade = configMapper.updateGrade(config);
        config.setConfigKey("endGrade");
        config.setConfigValue(gradeVO.getEndGrade());
        int endGrade = configMapper.updateGrade(config);
        if (0 == startGrade || 0 == endGrade) {
            throw new LibException("更新开放年级失败");
        }else{
            logger.info("更新成功");
            rtv = true;
        }
        return rtv;
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

    @Override
    public Map<String, Object> getOpenGrade() {
        Map<String, Object> rtv = new HashMap<>(2);
        Config config = new Config();
        config.setConfigKey("startGrade");
        Config c = configMapper.selectOpenGrade(config);
        rtv.put("startGrade", c.getConfigValue());
        config.setConfigKey("endGrade");
        c = configMapper.selectOpenGrade(config);
        rtv.put("endGrade", c.getConfigValue());
        return rtv;
    }


}
