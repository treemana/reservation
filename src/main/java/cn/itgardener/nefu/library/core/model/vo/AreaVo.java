package cn.itgardener.nefu.library.core.model.vo;

import java.util.ArrayList;

/**
 * @author : pc
 * @date : 2019/01/14
 * @since : Java 8
 */
public class AreaVo {
    private ArrayList<String> locationList;
    private Integer status;

    public ArrayList<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<String> locationList) {
        this.locationList = locationList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
