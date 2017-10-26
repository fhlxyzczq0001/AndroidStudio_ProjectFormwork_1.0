package com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus;

/**登录成功通知
 * @author: Administrator 杨重诚
 * @date: 2016/9/2:14:02
 */
public class Common_LoginSuccess_EventBus {
    private boolean LoginSuccess;
    private boolean isReceive=false;
    public Common_LoginSuccess_EventBus(boolean LoginSuccess){
        this.LoginSuccess=LoginSuccess;
    }

    public boolean isLoginSuccess() {
        return LoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        LoginSuccess = loginSuccess;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }
}
