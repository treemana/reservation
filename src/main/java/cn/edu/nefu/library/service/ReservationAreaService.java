package cn.edu.nefu.library.service;

import cn.edu.nefu.library.common.LibException;

import java.util.List;
import java.util.Map;


/**
 * @author : pc
 * @date : 2018/10/30
 * @since : Java 8
 */
public interface ReservationAreaService {
    /**
     * 查询预约的区域
     * @return
     */
    List<Map<String,String>> getReservationArea() throws LibException;
}
