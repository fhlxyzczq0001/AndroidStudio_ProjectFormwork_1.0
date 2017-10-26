package com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean;

/**省、市bean
 * @author: Administrator 杨重诚
 * @date: 2016/11/21:11:36
 */

public class Common_AreaBean {
    private int id;
    private String name;
    private int status = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
