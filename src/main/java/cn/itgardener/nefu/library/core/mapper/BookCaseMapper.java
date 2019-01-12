/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper;

import cn.itgardener.nefu.library.common.Page;
import cn.itgardener.nefu.library.core.mapper.provider.BookCaseProvider;
import cn.itgardener.nefu.library.core.model.BookCase;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.User;
import cn.itgardener.nefu.library.core.model.vo.BookCaseVo;
import cn.itgardener.nefu.library.core.model.vo.ShipVo;
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
     * @param bookCaseVo 书包柜编号
     * @return 操作是否成功 1 成功 0 失败
     */
    @UpdateProvider(type = BookCaseProvider.class, method = "setBookCaseByCondition")
    int setBookCaseByCondition(BookCaseVo bookCaseVo);

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
     * 通过location筛选可预约的书包柜数量
     *
     * @param location
     * @return
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectBagNum")
    int selectBagNum(@Param("location") String location);

    /**
     * 查询出对应位置编号最小的一个书包柜
     *
     * @param l 地区
     * @return
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectBookCaseNumberByLocation")
    BookCase selectBookCaseNumberByLocation(@Param("l") String l);

    /**
     * 根据书包柜编号更新使用者ID
     *
     * @param bcSystemId
     * @param studentId
     * @return
     */
    @Update("UPDATE bookcase SET bc_user_id=#{studentId},bc_status=1 WHERE bc_system_id=#{bcSystemId}")
    int updateOwnerByBcId(@Param("bcSystemId") int bcSystemId, @Param("studentId") int studentId);


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
     * 根据学号查询学生的systemId
     *
     * @param shipVO 学号
     * @return user
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectUserIdByStudentId")
    User selectUserIdByStudentId(ShipVo shipVO);

    /**
     * 修改关系,书包柜与单个学生的关系
     *
     * @param shipVO 参数
     * @return int
     */
    @UpdateProvider(type = BookCaseProvider.class, method = "updateSingleShip")
    int updateSingleShip(ShipVo shipVO);

    /**
     * 根据systemId查询书包柜
     *
     * @param shipVO systemId
     * @return bookcase
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectBySystemId")
    BookCase selectBySystemId(ShipVo shipVO);

    /**
     * 根据number查询书包柜
     *
     * @param shipVO number
     * @return bookcase
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectByNumber")
    BookCase selectByNumber(ShipVo shipVO);

    /**
     * 根据userId查找bookcase
     *
     * @param userId
     * @return BookCase 实例
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectBookCaseByUserId")
    List<BookCase> selectBookCaseByUserId(@Param("userId") int userId);

    /**
     * 根据location查询
     *
     * @param location 位置
     * @return list
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectConfigByLocation")
    List<Config> selectConfigByLocation(String location);

    /**
     * 根据开始结束的num删除柜子
     *
     * @param bookCaseVo vo
     * @return 删除的行数
     */
    @DeleteProvider(type = BookCaseProvider.class, method = "deleteBookcaseByRange")
    int deleteBookcaseByRange(BookCaseVo bookCaseVo);

    /**
     * 根据id删除柜子
     *
     * @param bookCaseVo vo
     * @return int
     */
    @DeleteProvider(type = BookCaseProvider.class, method = "deleteBookcaseById")
    int deleteBookcaseById(BookCaseVo bookCaseVo);

    /**
     * 获取location区域的最大柜子编号
     *
     * @return list
     */
    @Select("SELECT MAX(bc_number) FROM bookcase WHERE bc_location=#{location}")
    List<Integer> getMaxNumber(String location);

    /**
     * 增加柜子
     *
     * @param location 区域
     * @param number   柜子编号
     * @return 插入个数
     */
    @Insert("INSERT INTO bookcase(bc_location,bc_number,bc_status) VALUES (#{location},#{number},0)")
    int addBookcase(@Param("location") String location, @Param("number") int number);

    /**
     * 查询符合条件的书包柜
     *
     * @param bookCaseVo
     * @return List<BookCase>
     */
    @SelectProvider(type = BookCaseProvider.class, method = "selectBookCaseByCondition")
    List<BookCase> selectBookCaseByCondition(BookCaseVo bookCaseVo);

}