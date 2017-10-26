package com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus;


import com.ddtkj.projectformwork.common.Public.Common_Enum_String_Code;

/**
 * 第三方登录方式
 *
 * @author: Administrator 杨重诚
 * @date: 2016/11/30:17:34
 */

public class Common_OtherLoging_Eventbus {
    private Common_Enum_String_Code mEnum_string_code;
    private boolean isReceive;//是否接收成功

    public Common_OtherLoging_Eventbus(Common_Enum_String_Code mEnum_string_code) {
        this.mEnum_string_code = mEnum_string_code;
    }

    public Common_Enum_String_Code getEnum_string_code() {
        return mEnum_string_code;
    }

    public void setEnum_string_code(Common_Enum_String_Code enum_string_code) {
        mEnum_string_code = enum_string_code;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }
}
