package com.ddtkj.projectformwork.userinfo.Util;

import com.ddtkj.projectformwork.common.Public.Common_SharedPreferences_Key;
import com.ddtkj.projectformwork.userinfo.Base.UserInfo_Application_Utils;
import com.utlis.lib.SharePre;

public class UserInfo_SharePer_GlobalInfo {
    //初始化SharedPreferences
    private static SharePre sharePre = null;
    private static UserInfo_SharePer_GlobalInfo single = null;

    private UserInfo_SharePer_GlobalInfo() {
        getSharePre();
    }

    //静态工厂方法
    public static UserInfo_SharePer_GlobalInfo getInstance() {
        if (single == null || sharePre == null) {
            single = new UserInfo_SharePer_GlobalInfo();
        }
        return single;
    }

    public static SharePre getSharePre() {
        sharePre = UserInfo_Application_Utils.getApplication().getUserSharedPreferences();
        return sharePre;
    }
    /**
     * 存放登录页面获取短信验证码倒计时时间
     * @param CodeTime
     */
    public static void sharePre_PutLogingVerificationCodeTime(long CodeTime) {
        sharePre.put(Common_SharedPreferences_Key.USER_VERIFICATION_CODE_TIME, CodeTime);
        sharePre.commit();
    }

    /**
     * 获取登录页面获取短信验证码倒计时时间
     *
     * @return
     */
    public static Long sharePre_GetLogingVerificationCodeTime() {
        return sharePre.getLong(Common_SharedPreferences_Key.USER_VERIFICATION_CODE_TIME, 0);
    }
    /**
     * 存放注册页面按下倒计时的系统时间
     *
     * @param CodeTime
     */
    public static void sharePre_PutRegisterCodeTime(long CodeTime) {
        sharePre.put(Common_SharedPreferences_Key.USER_REGISTER_CODE_TIME, CodeTime);
        sharePre.commit();
    }

    /**
     * 获取用户注册页按下倒计时的系统时间
     *
     * @return
     */
    public static Long sharePre_GetRegisterCodeTime() {
        return sharePre.getLong(Common_SharedPreferences_Key.USER_REGISTER_CODE_TIME, 0);
    }
    /**
     * 存放忘记密码页面按下倒计时的系统时间
     *
     * @param CodeTime
     */
    public static void sharePre_PutForgotPasswordCodeTime(long CodeTime) {
        sharePre.put(Common_SharedPreferences_Key.USER_FORGOT_PASSWORD_CODE_TIME, CodeTime);
        sharePre.commit();
    }

    /**
     * 获取忘记密码页按下倒计时的系统时间
     *
     * @return
     */
    public static Long sharePre_GetForgotPasswordTime() {
        return sharePre.getLong(Common_SharedPreferences_Key.USER_FORGOT_PASSWORD_CODE_TIME, 0);
    }
}
