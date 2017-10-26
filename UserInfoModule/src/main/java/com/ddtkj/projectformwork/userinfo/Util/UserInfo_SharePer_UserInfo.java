package com.ddtkj.projectformwork.userinfo.Util;

import com.ddtkj.projectformwork.userinfo.Base.UserInfo_Application_Utils;
import com.utlis.lib.SharePre;

/**
 * Created by ${杨重诚} on 2017/7/12.
 */

public class UserInfo_SharePer_UserInfo {
    //初始化SharedPreferences
    private static SharePre sharePre = null;
    private static UserInfo_SharePer_UserInfo single=null;
    private UserInfo_SharePer_UserInfo(){
        getSharePre();
    }
    //静态工厂方法
    public static UserInfo_SharePer_UserInfo getInstance() {
        if (single == null || sharePre == null) {
            single = new UserInfo_SharePer_UserInfo();
        }
        return single;
    }
    public static SharePre getSharePre() {
        sharePre = UserInfo_Application_Utils.getApplication().getUserSharedPreferences();
        return sharePre;
    }
}
