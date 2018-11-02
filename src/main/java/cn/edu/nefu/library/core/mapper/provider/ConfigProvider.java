package cn.edu.nefu.library.core.mapper.provider;

import cn.edu.nefu.library.core.model.Config;
import cn.edu.nefu.library.core.model.vo.GradeVO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author : pc CMY
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
public String updateOpenTime( Config config) {
    return new SQL() {
        {
            UPDATE("config");
            SET("config_value=#{configValue}");
            WHERE("config_key=#{configKey}");

        }
    }.toString();
}

    public String updateStartGrade(GradeVO gradeVO){
        return new SQL(){
            {
                UPDATE("config");
                SET("config_value=#{startGrade}");
                WHERE("config_key='startGrade'");
            }
        }.toString();
    }
    public String updateEndGrade(GradeVO gradeVO){
        return new SQL(){
            {
                UPDATE("config");
                SET("config_value=#{endGrade}");
                WHERE("config_key='endGrade'");
            }
        }.toString();
    }

    public String updateOpenAera(Config config){
        return new SQL(){
            {
                UPDATE("config");
                SET("config_value=#{configValue}");
                WHERE("config_system_id = #{systemId}");

            }
        }.toString();
}
}
