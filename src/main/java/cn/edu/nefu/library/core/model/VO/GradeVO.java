package cn.edu.nefu.library.core.model.VO;

/**
 * author:CMY
 * @date 20181031
 * since java 8
 */
public class GradeVO {
    /**
     * 开始年级
     */
    private String startGrade;
    /**
     * 结束年级
     */
    private String endGrade;

    public String getStartGrade() {
        return startGrade;
    }

    public void setStartGrade(String startGrade) {
        this.startGrade = startGrade;
    }

    public String getEndGrade() {
        return endGrade;
    }

    public void setEndGrade(String endGrade) {
        this.endGrade = endGrade;
    }
}
