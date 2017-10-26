package com.ddtkj.projectformwork.common.Public;

/**
 * String类型的枚举
 *
 * @author: Administrator 杨重诚
 * @date: 2016/11/9:14:12
 */

public enum Common_Enum_String_Code {
    /**
     * 第三方登录方式微信
     */
    LOGING_WAY_WX("wx"),
    /**
     * 第三方登录方式QQ
     */
    LOGING_WAY_QQ("qq"),
    /**
     * 第三方登录方式微博
     */
    LOGING_WAY_WB("wb");

    // 定义私有变量
    private String nCode;

    // 构造函数，枚举类型只能为私有
    private Common_Enum_String_Code(String _nCode) {
        this.nCode = _nCode;
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}
