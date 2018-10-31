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
                SELECT("config_key as configKey,config_value as configValue ");
                FROM("config");
                WHERE("config_key like 'area%' and config_value = '1'");
            }
        }.toString();
    }

}
