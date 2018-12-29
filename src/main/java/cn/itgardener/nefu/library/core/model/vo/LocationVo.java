package cn.itgardener.nefu.library.core.model.vo;

/**
 * @author CMY
 * @Date 20181227
 * @since java1.8
 */
public class LocationVo {
    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 预约状态 0为可预约 1为不可预约
     */
    private Integer status;

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
