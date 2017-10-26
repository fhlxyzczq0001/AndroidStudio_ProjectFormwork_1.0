package com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean;
/**
 * 网络请求返回对象
 */
public class Common_RequestBean {

    private String res_code;
    private String res_msg;
    private Object data;

    private int type = 0;//仅用于订单号查询充值结果返回

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getRes_msg() {
        return res_msg;
    }

    public void setRes_msg(String res_msg) {
        this.res_msg = res_msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {

        return type;
    }
}
