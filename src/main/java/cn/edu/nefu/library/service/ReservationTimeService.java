package cn.edu.nefu.library.service;

import cn.edu.nefu.library.common.LibException;

import java.util.Map;
/**
 * @author : pc
 * @date : 2018/10/30
 * @since : Java 8
 */
public interface ReservationTimeService {
    /**
     * 查询预约时间
     * @return map
     */
    Map<String, String>  getReservationTime() throws LibException;
}
