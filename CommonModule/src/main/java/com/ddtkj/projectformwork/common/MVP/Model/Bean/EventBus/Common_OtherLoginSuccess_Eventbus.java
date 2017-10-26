package com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus;

/**其它登录方式登录成功
 * @author: Administrator 杨重诚
 * @date: 2016/11/30:13:02
 */

public class Common_OtherLoginSuccess_Eventbus {
    private boolean isLoginSuccess=false;
    boolean isReceive=false;
    public  Common_OtherLoginSuccess_Eventbus(boolean isLoginSuccess){
        this.isLoginSuccess=isLoginSuccess;
    }

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }
}
