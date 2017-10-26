package com.ddtkj.projectformwork.business.Util;

import com.ddtkj.projectformwork.business.Base.Business_Application_Utils;
import com.utlis.lib.SharePre;

/**
 * Created by ${杨重诚} on 2017/7/12.
 */

public class Business_SharePer_UserInfo {
    //初始化SharedPreferences
    private static SharePre sharePre = null;
    private static Business_SharePer_UserInfo single=null;
    private Business_SharePer_UserInfo(){
        getSharePre();
    }
    //静态工厂方法
    public static Business_SharePer_UserInfo getInstance() {
        if (single == null || sharePre == null) {
            single = new Business_SharePer_UserInfo();
        }
        return single;
    }
    public static SharePre getSharePre() {
        sharePre = Business_Application_Utils.getApplication().getUserSharedPreferences();
        return sharePre;
    }
}
