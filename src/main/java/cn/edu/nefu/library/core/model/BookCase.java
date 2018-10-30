package cn.edu.nefu.library.core.model;

/**
 * @author : chenchenT
 * @date : 2018/10/28
 * @since : Java 8
 */
public class BookCase {

    /**
     * 柜子ID
     */
    private Integer systemId;

    /**
     * 柜子位置 null 全部 1 二楼北 2 二楼南 3 三楼北 4 三楼南
     */
    private Integer location;

    /**
     * 柜子编号
     */
    private Integer number;

    /**
     * 拥有者ID
     */
    private Integer userId;

    /**
     * 柜子当前状态 0 开放 1 占用或预留
     */
    private Integer status;

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
