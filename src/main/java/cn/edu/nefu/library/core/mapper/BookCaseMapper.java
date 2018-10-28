package cn.edu.nefu.library.core.mapper;

import cn.edu.nefu.library.core.mapper.provider.BookCaseProvider;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

/**
 * @author : chenchenT
 * @date : 2018/10/28
 * @since : Java 8
 */
@Mapper
@Repository
public interface BookCaseMapper {

    @SelectProvider(type = BookCaseProvider.class, method = "selectByUserId")
    BookCase selectByUserId(User user);

}