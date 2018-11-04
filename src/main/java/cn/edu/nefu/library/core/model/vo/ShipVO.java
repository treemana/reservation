package cn.edu.nefu.library.core.model.vo;
/**
 * @author : pc
 * @date : 2018/11/04
 * @since : Java 8
 */
public class ShipVO {
    /**
     * 书包柜的编号
     */
    private String number;
    /**
     * 学生学号，为 null 则释放关系, 为学号则建立关系
     */
    private String studentId;
    /**
     * 学号为空设置为0，不为空设置为1
     */
    private Integer status;
    /**
     * 学号所对应的userId

     */
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
