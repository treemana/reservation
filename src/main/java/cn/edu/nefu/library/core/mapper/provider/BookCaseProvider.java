package cn.edu.nefu.library.core.mapper.provider;

import cn.edu.nefu.library.common.Page;
import cn.edu.nefu.library.common.util.PageUtil;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.vo.ShipVO;
import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.ibatis.annotations.Param;
import cn.edu.nefu.library.core.model.vo.BookCaseVo;
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

    public String setByNumber(BookCase bookCase) {
        return new SQL() {
            {
                UPDATE("bookcase");
                SET("bc_status=1");
                WHERE("bc_number=#{number}");
            }
        }.toString();
    }

    public String updateShipByNumber(BookCase bookCase) {
        return new SQL() {
            {
                UPDATE("bookcase");
                SET("bc_user_id=null, bc_status=0");
                WHERE("bc_number=#{number}");
            }
        }.toString();
    }


    public String selectBagNum(BookCase bookCase){
        return new SQL(){
            {
                SELECT("count(*)");
                FROM("bookcase");
                WHERE("bc_status=0");
                WHERE("bc_location=#{location}");
            }
        }.toString();
    }

    public String selectDetailByCondition(BookCaseVo bookCaseVo, Page page) {

        String limit = "9";
        if (null != page) {
            limit = PageUtil.getLimit(page.getNowPage(), page.getPageSize());
        }
        String finalLimit = limit;

        return new SQL() {
            {
                SELECT("bc_system_id AS systemId, bc_location AS location, bc_number AS number, " +
                        "bc_user_id AS userId, bc_status AS status");
                FROM("bookcase");
                if (null != bookCaseVo.getLocation()) {
                    WHERE(" bc_location=" + bookCaseVo.getLocation());
                }
                if (null != bookCaseVo.getStatus()) {
                    WHERE(" bc_status=" + bookCaseVo.getStatus());
                }

                if (null != bookCaseVo.getId()) {
                    WHERE("( bc_number>=" + bookCaseVo.getL() + " and bc_number<= " + bookCaseVo.getR() + " )");
                }
                if (null != bookCaseVo.getUserId()) {
                    WHERE(" bc_user_id=" + bookCaseVo.getUserId());
                }
                ORDER_BY(" bc_system_id LIMIT " + finalLimit);
            }
        }.toString();
    }


    public String countByCondition(BookCaseVo bookCaseVo) {

        return new SQL() {
            {
                SELECT("count(bc_system_id) AS totalSize");
                FROM("bookcase");
                if (null != bookCaseVo.getLocation()) {
                    WHERE(" bc_location=" + bookCaseVo.getLocation());
                }
                if (null != bookCaseVo.getStatus()) {
                    WHERE(" bc_status=" + bookCaseVo.getStatus());
                }
                if (null != bookCaseVo.getId()) {
                    WHERE("( bc_number>=" + bookCaseVo.getL() + " and bc_number<= " + bookCaseVo.getR() + " )");
                }
                if (null != bookCaseVo.getUserId()) {
                    WHERE(" bc_user_id=" + bookCaseVo.getUserId());
                }
            }
        }.toString();
    }

    public String selectOneBookCaseNumber(@Param("l")int l) {
        return new SQL() {
            {
                SELECT("bc_system_id AS systemId,bc_number AS number");
                FROM( "bookcase");
                WHERE("bc_location=#{l}");
                WHERE("bc_status=0");
                ORDER_BY("bc_system_id LIMIT 1");
            }
        }.toString();
    }

    public String selectUserIdByStudentId(ShipVO shipVO) {
        return new SQL(){
            {
                SELECT("user_system_id AS systemId, user_username AS studentId, user_password AS studentName, " +
                        "user_type AS type, user_token AS token");
                FROM("user");
                WHERE("user_username=#{studentId}");
            }
        }.toString();
    }
    public String updateSingleShip(ShipVO shipVO){
        return new SQL(){
            {
                UPDATE("bookcase");
                SET("bc_user_id = #{userId}," +
                        "bc_status = #{status}");
                WHERE("bc_number = #{number}");
            }
        }.toString();
    }


    public String selectByNumber(ShipVO shipVO) {
        return new SQL(){
            {
                SELECT("bc_system_id AS systemId, bc_location AS location," +
                        "bc_number AS number,  bc_user_id AS userId, bc_status AS status");
                FROM("bookcase");
                WHERE("bc_number = #{number}");
            }
        } .toString();
    }
}


