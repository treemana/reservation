package cn.edu.nefu.library.core.mapper;

import cn.edu.nefu.library.core.mapper.provider.UserProvider;
import cn.edu.nefu.library.core.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Jimi
 * @date : 2018/10/27
 * @since : Java 8
 */
@Mapper
@Repository
public interface UserMapper {

    @SelectProvider(type = UserProvider.class, method = "selectByCondition")
    List<User> selectByCondition(User user);

    @Update("UPDATE user SET user_token=#{token} WHERE user_system_id=#{systemId}")
    int updateTokenBySystemId(User user);

    @SelectProvider(type = UserProvider.class, method = "selectByType")
    List<User> selectByType();

    @Select("SELECT user_system_id AS systemId, user_username AS studentId, user_password AS studentName, " +
            "user_type AS type, user_token AS token from user where user_username=#{studentId}")
    User selectByStudenId(User user);

}