package cn.edu.nefu.library.service;

import cn.edu.nefu.library.common.LibException;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
import org.apache.ibatis.annotations.UpdateProvider;
import cn.edu.nefu.library.core.mapper.provider.BookCaseProvider;

import java.util.Map;

/**
 * @author : chenchenT
 * @date : 2018/10/28
 * @since : Java 8
 */
public interface BookCaseService {

    /**
     * 获取我的书包柜信息
     * @param user 用户学号
     * @return 书包柜位置和编号
     * @throws LibException 是否有柜子
     */
    Map<String, Object> getLocationByUserId(User user) throws LibException;

    /**
     * 设置预留的书包柜
     * @param bookCase 书包柜编号
     * @return 是否预留成功
     * @throws LibException 是否预留成功
     */
    int setKeepByNumber(BookCase bookCase);

    /**
     * 清空/删除关系
     * @param bookCase 书包柜编号
     * @return 是否清除成功
     */
    int updateShipByNumber(BookCase bookCase);

}
