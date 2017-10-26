package com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus;

/**
 * 重置密码成功通知
 * @author: Administrator 杨重诚
 * @date: 2016/9/2:14:02
 */
public class Common_ResetPasswordsSuccess_EventBus {
    private boolean  ResetPasswordsSuccess;
    private String userName;
    private String passWord;
    private boolean isReceive=false;
    public Common_ResetPasswordsSuccess_EventBus(boolean ResetPasswordsSuccess, String userName, String passWord){
        this.ResetPasswordsSuccess=ResetPasswordsSuccess;
        this.userName=userName;
        this.passWord=passWord;
    }

    public boolean isResetPasswordsSuccess() {
        return ResetPasswordsSuccess;
    }

    public void setResetPasswordsSuccess(boolean resetPasswordsSuccess) {
        ResetPasswordsSuccess = resetPasswordsSuccess;
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
