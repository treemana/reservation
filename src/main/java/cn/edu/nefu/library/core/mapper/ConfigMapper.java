package cn.edu.nefu.library.core.mapper;

import cn.edu.nefu.library.core.mapper.provider.ConfigProvider;
import cn.edu.nefu.library.core.model.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : pc
 * @date : 2018/10/30
 * @since : Java 8
 */
@Mapper
@Repository
public interface ConfigMapper {
    /**
     * 查询开放区域
     * @return 数组<config>
     */
    @SelectProvider(type= ConfigProvider.class,method = "selectOpenAera")
    List<Config> selectOpenAera();
}
