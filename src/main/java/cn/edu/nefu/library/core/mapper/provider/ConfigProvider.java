package cn.edu.nefu.library.core.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author : pc
 * @date : 2018/10/30
 * @since : Java 8
 */
public class ConfigProvider {
    public String selectOpenAera(){
        return new SQL(){
            {
                SELECT("config_system_id as systemId, config_key as configKey,config_value as configValue");
                FROM("config");
            }
        }.toString();
    }

    public String selectOpenTime(){
        return new SQL(){
            {
                SELECT("config_system_id as systemId ,config_key as configKey,config_value as configValue");
                FROM("config");
            }
        }.toString();
    }

}
