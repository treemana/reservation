package cn.edu.nefu.library.service.impl;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.mapper.ConfigMapper;
import cn.edu.nefu.library.core.model.Config;
import cn.edu.nefu.library.core.model.VO.GradeVO;
import cn.edu.nefu.library.service.ReservationAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author : pc CMY
 * @date : 2018/10/30
 * @since : Java 8
 */
@Service
public class ReservationAreaServiceImpl implements ReservationAreaService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConfigMapper configMapper;

    @Autowired
    public ReservationAreaServiceImpl(ConfigMapper configMapper) {
        this.configMapper = configMapper;
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
    public void postGrade(GradeVO gradeVO)throws LibException{
        int startGrade = configMapper.updateStartGrade(gradeVO);
        int endGrade = configMapper.updateEndGrade(gradeVO);
        if(startGrade==0||endGrade==0){
            throw new LibException("更新开放年级失败");
        }else{
            logger.info("更新成功");
        }
    }
}
