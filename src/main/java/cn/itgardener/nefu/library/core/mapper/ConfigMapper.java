/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper;

import cn.itgardener.nefu.library.core.mapper.provider.ConfigProvider;
import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.vo.LocationVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : pc CMY
 * @date : 2018/10/30
 * @since : Java 8
 */
@Mapper
@Repository
public interface ConfigMapper {

    /**
     * 查询预约时间
     *
     * @return 返回list
     */
    @SelectProvider(type = ConfigProvider.class, method = "selectAll")
    List<Config> selectAll();

    /**
     * 修改开始时间
     *
     * @param config 开始和结束时间
     * @return int
     */
    @UpdateProvider(type = ConfigProvider.class, method = "updateOpenTime")
    int updateOpenTime(Config config);

    /**
     * 修改开放区域
     *
     * @param config
     * @return 修改的个数
     */
    @UpdateProvider(type = ConfigProvider.class, method = "selectOpenAreaBySystemId")
    int selectOpenAreaBySystemId(Config config);

    /**
     * 修改开始和结束年级年级
     *
     * @param config
     * @return 结果数量
     */
    @UpdateProvider(type = ConfigProvider.class, method = "updateGrade")
    int updateGrade(Config config);

    /**
     * 查询开放时间
     *
     * @return config
     */
    @SelectProvider(type = ConfigProvider.class, method = "selectStartTime")
    Config selectStartTime();

    /**
     * 查询开放年级
     *
     * @param config
     * @return config 实例
     */
    @SelectProvider(type = ConfigProvider.class, method = "selectOpenGrade")
    Config selectOpenGrade(Config config);

    /**
     * 查询结束时间
     *
     * @return Config
     */
    @SelectProvider(type = ConfigProvider.class, method = "selectEndTime")
    Config selectEndTime();

    /**
     * 查询出location的区域
     *
     * @param location
     * @return Config结果集
     */
    @SelectProvider(type = ConfigProvider.class, method = "selectLocation")
    List<Config> selectLocation(String location);

    /**
     * 从config表查询某楼层所有的区域
     *
     * @param locationVo 实例
     * @return config结果集
     */
    @SelectProvider(type = ConfigProvider.class, method = "selectFloorLocation")
    List<Config> selectFloorLocation(LocationVo locationVo);

    /**
     * 插入新区域
     *
     * @param locationVo 对象
     * @return
     */
    @Insert("INSERT INTO config(config_key,config_value) values (#{location},#{status})")
    int addLocation(LocationVo locationVo);

    /**
     * updateLocation
     *
     * @param locationVo locationVo
     * @return int
     */
    @Update("update config set config_value = #{status} where config_key=#{location} ")
    int updateLocation(LocationVo locationVo);

    /**
     * 删除区域
     *
     * @param location 区域
     * @return int
     */
    @Delete("delete from config where config_key = #{location}")
    int deleteLocation(String location);
}
