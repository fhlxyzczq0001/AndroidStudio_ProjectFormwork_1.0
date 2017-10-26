package com.ddtkj.projectformwork.common.Public;

/**全局标识
 * @author: Administrator 杨重诚
 * @date: 2016/11/3:9:41
 */

public enum Common_Main_PublicCode {
    Business_TAB_HOME("首页"),
    Business_TAB_HELP("找人帮忙"),
    Business_TAB_MSG("消息"),
    Business_TAB_MYINFO("我的账户");

    // 定义私有变量
    private String nCode;

    // 构造函数，枚举类型只能为私有
    private Common_Main_PublicCode(String _nCode) {
        this.nCode = _nCode;
    }

    @Override
    public String toString() {
        return String.valueOf(this.nCode);
    }
}
