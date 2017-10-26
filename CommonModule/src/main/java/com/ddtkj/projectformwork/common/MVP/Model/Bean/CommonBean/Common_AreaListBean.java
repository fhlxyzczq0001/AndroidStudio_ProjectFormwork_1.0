package com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean;

import java.util.List;

/**区域list
 * @author: Administrator 杨重诚
 * @date: 2016/11/21:11:58
 */

public class Common_AreaListBean {
    private List<Common_AreaBean> province_list;
    private List<Common_AreaBean> city_list;
    private List<Common_AreaBean> area_list;


    public List<Common_AreaBean> getProvince_list() {
        return province_list;
    }

    public void setProvince_list(List<Common_AreaBean> province_list) {
        this.province_list = province_list;
    }

    public List<Common_AreaBean> getCity_list() {
        return city_list;
    }

    public void setCity_list(List<Common_AreaBean> city_list) {
        this.city_list = city_list;
    }

    public List<Common_AreaBean> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<Common_AreaBean> area_list) {
        this.area_list = area_list;
    }
}
