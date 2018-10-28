package cn.edu.nefu.library.core.mapper.provider;

import cn.edu.nefu.library.core.model.User;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author : chenchenT
 * @date : 2018/10/28
 * @since : Java 8
 */
public class BookCaseProvider {

    public String selectByUserId(User user) {
        return new SQL() {
            {
                SELECT("bc_system_id AS systemId,bc_location AS location, bc_number AS number, " +
                        "bc_user_id AS userId, bc_status AS status");
                FROM("bookcase");
                WHERE("bc_user_id=#{systemId}");
            }
        }.toString();
    }
}
