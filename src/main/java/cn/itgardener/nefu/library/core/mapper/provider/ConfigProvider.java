/*
 * Copyright (c) 2014-2019 www.itgardener.cn. All rights reserved.
 */

package cn.itgardener.nefu.library.core.mapper.provider;

import cn.itgardener.nefu.library.core.model.Config;
import cn.itgardener.nefu.library.core.model.vo.LocationVo;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author : pc CMY chenchenT
 * @date : 2018/10/30
 * @since : Java 8
 */
public class ConfigProvider {

    public String selectAll() {
        return new SQL() {
            {
                SELECT("config_system_id as systemId ,config_key as configKey,config_value as configValue");
                FROM("config");
            }
        }.toString();
    }

    public String updateOpenTime(Config config) {
        return new SQL() {
            {
                UPDATE("config");
                SET("config_value=#{configValue}");
                WHERE("config_key=#{configKey}");
            }
        }.toString();
    }

    public String updateGrade(Config config) {
        return new SQL() {
            {
                UPDATE("config");
                SET("config_value=#{configValue}");
                WHERE("config_key=#{configKey}");
            }
        }.toString();
    }

    public String selectOpenAreaBySystemId(Config config) {
        return new SQL() {
            {
                UPDATE("config");
                SET("config_value=#{configValue}");
                WHERE("config_system_id = #{systemId}");

            }
        }.toString();
    }

    public String selectStartTime() {
        return new SQL() {
            {
                SELECT("config_key as configKey,config_value as configValue ");
                FROM("config");
                WHERE("config_key='startTime'");
            }
        }.toString();
    }

    public String selectEndTime() {
        return new SQL() {
            {
                SELECT("config_key as configKey,config_value as configValue ");
                FROM("config");
                WHERE("config_key='endTime'");
            }
        }.toString();
    }

    public String selectOpenGrade(Config config) {
        return new SQL() {
            {
                SELECT("config_key as configKey,config_value as configValue");
                FROM("config");
                WHERE("config_key=#{configKey}");
            }
        }.toString();
    }

    public String selectFloorLocation(LocationVo locationVo) {
        return new SQL() {
            {
                SELECT("config_key as configKey,config_value as configValue");
                FROM("config");
                WHERE("config_key like '" + locationVo.getFloor() + "%'");
            }
        }.toString();
    }

    public String selectLocation(String location) {
        return new SQL() {
            {
                SELECT("config_key as configKey,config_value as configValue");
                FROM("config");
                WHERE("config_key=#{location}");
            }
        }.toString();
    }
}
