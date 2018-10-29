package cn.edu.nefu.library.core.mapper;

import cn.edu.nefu.library.core.mapper.provider.BookCaseProvider;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

/**
 * @author : chenchenT
 * @date : 2018/10/28
 * @since : Java 8
 */
@Mapper
@Repository
public interface BookCaseMapper {

    /**
     *
     * @param user 用户ID
     * @return bookCase 书包柜实体
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectByUserId")
    BookCase selectByUserId(User user);

    /**
     *
     * @param bookCase 书包柜编号
     * @return 操作是否成功 1 成功 0 失败
     */
    @UpdateProvider(type = BookCaseProvider.class, method = "setByNumber")
    int setByNumber(BookCase bookCase);

    /**
     *
     * @param bookcase 书包柜编号
     * @return 操作是否成功 1 成功 0 失败
     */
    @UpdateProvider(type = BookCaseProvider.class, method = "updateShipByNumber")
    int updateShipByNumber(BookCase bookcase);

}