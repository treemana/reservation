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
     * 查询预约时间
     * @return 返回list
     */
    @SelectProvider(type= ConfigProvider.class,method = "selectOpenTime")
    List<Config> selectOpenTime();

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
