/*
 * Copyright (c) 2014-2018 www.itgardener.cn. All rights reserved.
 */

package cn.edu.nefu.library.core.mapper.provider;

import cn.edu.nefu.library.core.model.Config;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author : pc CMY chenchenT
 * @date : 2018/10/30
 * @since : Java 8
 */
public class ConfigProvider {
    public String selectOpenAera() {
        return new SQL() {
            {
                SELECT("config_system_id as systemId, config_key as configKey,config_value as configValue");
                FROM("config");
            }
        }.toString();
    }

    public String selectOpenTime() {
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

    public String updateOpenAera(Config config) {
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

    public String selectOpenGrade(Config config) {
        return new SQL() {
            {
                SELECT("config_key as configKey,config_value as configValue");
                FROM("config");
                WHERE("config_key=#{configKey}");
            }
        }.toString();
    }
}
