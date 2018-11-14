/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.edu.nefu.library.core.mapper;

import cn.edu.nefu.library.common.Page;
import cn.edu.nefu.library.core.mapper.provider.BookCaseProvider;
import cn.edu.nefu.library.core.model.BookCase;
import cn.edu.nefu.library.core.model.User;
import cn.edu.nefu.library.core.model.vo.BookCaseVo;
import cn.edu.nefu.library.core.model.vo.ShipVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : chenchenT
 * @date : 2018/10/28
 * @since : Java 8
 */
@Mapper
@Repository
public interface BookCaseMapper {

    /**
     * 通过用户ID获取书包柜信息
     *
     * @param user 用户ID
     * @return bookCase 书包柜实体
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectByUserId")
    BookCase selectByUserId(User user);

    /**
     * 设置预留的书包柜
     *
     * @param bookCase 书包柜编号
     * @return 操作是否成功 1 成功 0 失败
     */
    @UpdateProvider(type = BookCaseProvider.class, method = "setByNumber")
    int setByNumber(BookCase bookCase);

    /**
     * 通过书包柜编号释放关系
     *
     * @param bookcase 书包柜编号
     * @return 操作是否成功 1 成功 0 失败
     */
    @UpdateProvider(type = BookCaseProvider.class, method = "deleteShipByNumber")
    int deleteShipByNumber(BookCase bookcase);

    /**
     * 清空所有书包柜关系
     *
     * @return 操作是否成功 1 成功 0 失败
     */
    @UpdateProvider(type = BookCaseProvider.class, method = "deleteAllShip")
    int deleteAllShip();

    /**
     * 通过location筛选书包柜数量
     *
     * @param location
     * @return
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectBagNum")
    int selectBagNum(@Param("location") int location);

    /**
     * 查询出对应位置编号最小的一个书包柜
     *
     * @param l 地区
     * @return
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectBookCaseNumberByLocation")
    BookCase selectBookCaseNumberByLocation(@Param("l") int l);

    /**
     * 根据书包柜编号更新使用者ID
     *
     * @param bcNumber
     * @param studentId
     * @return
     */
    @Update("UPDATE bookcase SET bc_user_id=#{studentId},bc_status=1 WHERE bc_number=#{bcNumber}")
    int updateOwnerbyBcNumber(@Param("bcNumber") int bcNumber, @Param("studentId") int studentId);


    /**
     * 根据条件查询书包柜详情
     *
     * @param bookCaseVo 书包柜编号
     * @param page
     * @return 操作是否成功 1 成功 0 失败
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectDetailByCondition")
    List<BookCase> selectDetailByCondition(BookCaseVo bookCaseVo, Page page);

    /**
     * 条件查询计数
     *
     * @param bookCaseVo 参数集
     * @return 符合条件的记录
     */
    @SelectProvider(type = BookCaseProvider.class, method = "countByCondition")
    Page countByCondition(BookCaseVo bookCaseVo);

    /**
     * 根据学号查询学生的systemid
     *
     * @param shipVO 学号
     * @return user
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectUserIdByStudentId")
    User selectUserIdByStudentId(ShipVo shipVO);

    /**
     * 修改关系，书包柜与单个学生的关系
     *
     * @param shipVO 参数
     * @return int
     */
    @UpdateProvider(type = BookCaseProvider.class, method = "updateSingleShip")
    int updateSingleShip(ShipVo shipVO);

    /**
     * 查询是否存在这个number的书包柜
     *
     * @param shipVO number
     * @return bookcase
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectByNumber")
    BookCase selectByNumber(ShipVo shipVO);

}