package cn.edu.nefu.library.service;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.model.VO.GradeVO;

import java.util.List;
import java.util.Map;


/**
 * @author : pc CMY
 * @date : 2018/10/30
 * @since : Java 8
 */
public interface ReservationAreaService {
    /**
     * 查询预约的区域
     * @return
     */
    List<Map<String,String>> getReservationArea() throws LibException;

    /**
     * 修改开始结束年级
     * @param gradeVO
     * @throws LibException
     */
    void postGrade(GradeVO gradeVO)throws LibException;

}
