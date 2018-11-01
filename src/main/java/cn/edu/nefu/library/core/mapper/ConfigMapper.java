package cn.edu.nefu.library.core.mapper;

import cn.edu.nefu.library.core.mapper.provider.ConfigProvider;
import cn.edu.nefu.library.core.model.Config;
import cn.edu.nefu.library.core.model.VO.GradeVO;
import org.apache.ibatis.annotations.Mapper;
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
     * @return 数组<config>
     */
    @SelectProvider(type= ConfigProvider.class,method = "selectOpenAera")
    List<Config> selectOpenAera();

    /**
     * 修改开放区域
     * @return 修改的个数
     */
    @UpdateProvider(type= ConfigProvider.class,method = "updateOpenAera")
    int updateOpenAera(List list);

    /**
     * 查询预约时间
     * @return 返回list
     */
    @SelectProvider(type= ConfigProvider.class,method = "selectOpenTime")
    List<Config> selectOpenTime();

    /**
     * 修改开始时间
     * @param  config 开始和结束时间
     * @return int
     */
    @UpdateProvider(type= ConfigProvider.class,method = "updateOpenTime")
    int updateOpenTime(Config config);

    /**
     * 修改开放区域
     * @return 修改的个数
     */
    @UpdateProvider(type= ConfigProvider.class,method = "updateOpenAera")
    int updateOpenArea(Config config);

    /**
     * 修改开始年级
     * @param gradeVO
     * @return 结果数量
     */
    @UpdateProvider(type = ConfigProvider.class,method="updateStartGrade")
    int updateStartGrade(GradeVO gradeVO);

    /**
     * 修改结束年级
     * @param gradeVO
     * @return 结果数量
     */
    @UpdateProvider(type = ConfigProvider.class,method="updateEndGrade")
    int updateEndGrade(GradeVO gradeVO);

}
