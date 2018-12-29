/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper;

import cn.itgardener.nefu.library.core.mapper.provider.ConfigProvider;
import cn.itgardener.nefu.library.core.model.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
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
     * 查询开放区域
     *
     * @return 数组<config>
     */
    @SelectProvider(type = ConfigProvider.class, method = "selectOpenArea")
    List<Config> selectOpenArea();

    /**
     * 查询预约时间
     *
     * @return 返回list
     */
    @SelectProvider(type = ConfigProvider.class, method = "selectOpenTime")
    List<Config> selectOpenTime();

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
     * @param location
     * @return Config结果集
     */
    @SelectProvider(type=ConfigProvider.class,method = "selectLocation")
    List<Config> selectLocation(String location);
}
