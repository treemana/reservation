/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper.provider;

import cn.itgardener.nefu.library.common.Page;
import cn.itgardener.nefu.library.common.util.PageUtil;
import cn.itgardener.nefu.library.core.model.BookCase;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import cn.itgardener.nefu.library.core.model.vo.ShipVo;
import org.apache.ibatis.annotations.Param;
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

    public String deleteShipByNumber(BookCase bookCase) {
        return new SQL() {
            {
                UPDATE("bookcase");
                SET("bc_user_id=null, bc_status=0");
                WHERE("bc_number=#{number}");
            }
        }.toString();
    }

    public String deleteAllShip() {
        return new SQL() {
            {
                UPDATE("bookcase");
                SET("bc_user_id=null, bc_status=0");
                WHERE("bc_status=1");
            }
        }.toString();
    }

    public String selectBagNum(@Param("location") int location) {
        return new SQL() {
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
                if (null != bookCaseVo.getStatus() && 2 == bookCaseVo.getStatus()) {
                    if (null != bookCaseVo.getLocation()) {
                        WHERE(" bc_location=" + bookCaseVo.getLocation());
                    }
                    if (null != bookCaseVo.getStatus()) {
                        WHERE(" bc_status=1");
                    }
                    if (null != bookCaseVo.getId()) {
                        WHERE("( bc_number>=" + bookCaseVo.getL() + " and bc_number<= " + bookCaseVo.getR() + " )");
                    }
                    WHERE(" bc_user_id is null");
                    ORDER_BY(" bc_system_id LIMIT " + finalLimit);
                } else if (null != bookCaseVo.getStatus() && 1 == bookCaseVo.getStatus()) {
                    if (null != bookCaseVo.getLocation()) {
                        WHERE(" bc_location=" + bookCaseVo.getLocation());
                    }
                    if (null != bookCaseVo.getStatus()) {
                        WHERE(" bc_status=1");
                    }
                    if (null != bookCaseVo.getId()) {
                        WHERE("( bc_number>=" + bookCaseVo.getL() + " and bc_number<= " + bookCaseVo.getR() + " )");
                    }
                    if (null == bookCaseVo.getStudentId()) {
                        WHERE(" bc_user_id is not null");
                    }
                    if (null != bookCaseVo.getStudentId()) {
                        WHERE(" bc_user_id=" + bookCaseVo.getUserId());
                    }
                    ORDER_BY(" bc_system_id LIMIT " + finalLimit);
                } else {
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
            }
        }.toString();
    }


    public String countByCondition(BookCaseVo bookCaseVo) {

        return new SQL() {
            {
                SELECT("count(bc_system_id) AS totalSize");
                FROM("bookcase");
                if (null != bookCaseVo.getStatus() && 2 == bookCaseVo.getStatus()) {
                    if (null != bookCaseVo.getLocation()) {
                        WHERE(" bc_location=" + bookCaseVo.getLocation());
                    }
                    if (null != bookCaseVo.getStatus()) {
                        WHERE(" bc_status=1");
                    }
                    if (null != bookCaseVo.getId()) {
                        WHERE("( bc_number>=" + bookCaseVo.getL() + " and bc_number<= " + bookCaseVo.getR() + " )");
                    }
                    WHERE(" bc_user_id is null");
                } else if (null != bookCaseVo.getStatus() && 1 == bookCaseVo.getStatus()) {
                    if (null != bookCaseVo.getLocation()) {
                        WHERE(" bc_location=" + bookCaseVo.getLocation());
                    }
                    WHERE(" bc_status=1");
                    if (null != bookCaseVo.getId()) {
                        WHERE("( bc_number>=" + bookCaseVo.getL() + " and bc_number<= " + bookCaseVo.getR() + " )");
                    }
                    if (null == bookCaseVo.getStudentId()) {
                        WHERE(" bc_user_id is not null");
                    }
                    if (null != bookCaseVo.getStudentId()) {
                        WHERE(" bc_user_id=" + bookCaseVo.getUserId());
                    }
                } else {
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
            }
        }.toString();
    }

    public String selectBookCaseNumberByLocation(@Param("l") int l) {
        return new SQL() {
            {
                SELECT("bc_system_id AS systemId,bc_number AS number");
                FROM("bookcase");
                WHERE("bc_location=#{l}");
                WHERE("bc_status=0");
                ORDER_BY("bc_system_id LIMIT 1");
            }
        }.toString();
    }

    public String selectUserIdByStudentId(ShipVo shipVO) {
        return new SQL() {
            {
                SELECT("user_system_id AS systemId, user_username AS studentId, user_password AS studentName, " +
                        "user_type AS type, user_token AS token");
                FROM("user");
                WHERE("user_username=#{studentId}");
            }
        }.toString();
    }

    public String updateSingleShip(ShipVo shipVO) {
        return new SQL() {
            {
                UPDATE("bookcase");
                SET("bc_user_id = #{userId}," +
                        "bc_status = #{status}");
                WHERE("bc_number = #{number}");
            }
        }.toString();
    }


    public String selectByNumber(ShipVo shipVO) {
        return new SQL() {
            {
                SELECT("bc_system_id AS systemId, bc_location AS location," +
                        "bc_number AS number,  bc_user_id AS userId, bc_status AS status");
                FROM("bookcase");
                WHERE("bc_number = #{number}");
            }
        }.toString();
    }

    public String selectBookCaseByUserId(@Param("userId") int userId) {
        return new SQL() {
            {
                SELECT("bc_system_id AS systemId, bc_location AS location," +
                        "bc_number AS number,  bc_user_id AS userId, bc_status AS status");
                FROM("bookcase");
                WHERE("bc_user_id=#{userId}");
            }
        }.toString();
    }
}


