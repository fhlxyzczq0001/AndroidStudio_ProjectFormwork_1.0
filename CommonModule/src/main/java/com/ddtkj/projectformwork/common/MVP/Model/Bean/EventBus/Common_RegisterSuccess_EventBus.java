package com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus;

/**注册成功通知
 * @author: Administrator 杨重诚
 * @date: 2016/9/2:14:02
 */
public class Common_RegisterSuccess_EventBus {
    private boolean  RegisterSuccess;
    private String userName;
    private String passWord;
    private boolean isReceive=false;
    public Common_RegisterSuccess_EventBus(boolean RegisterSuccess, String userName, String passWord){
        this.RegisterSuccess=RegisterSuccess;
        this.userName=userName;
        this.passWord=passWord;
    }

    public boolean isRegisterSuccess() {
        return RegisterSuccess;
    }

    public void setRegisterSuccess(boolean registerSuccess) {
        RegisterSuccess = registerSuccess;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }
}
