package cn.edu.nefu.library.service;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.model.vo.TimeVO;

import java.util.Map;
/**
 * @author : pc chenchenT
 * @date : 2018/10/30
 * @since : Java 8
 */
public interface ReservationTimeService {
    /**
     * 查询预约时间
     * @return map
     */
    Map<String, String>  getReservationTime() throws LibException;

    /**
     * 修改预约时间
     * @return 是否修改着成功
     * @throws LibException 异常
     */
    boolean putReservationTime(TimeVO timeVO) throws LibException;

    /**
     * 查询开放时间
     * @return
     */
    Map<String, Object> getStartTime();

}
