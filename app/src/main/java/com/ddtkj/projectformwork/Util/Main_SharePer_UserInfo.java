package com.ddtkj.projectformwork.Util;


import com.ddtkj.projectformwork.Base.My_Application;
import com.utlis.lib.SharePre;

public class Main_SharePer_UserInfo {
    //初始化SharedPreferences
    static SharePre sharePre = My_Application.getInstance().getUserSharedPreferences();

    public static SharePre getSharePre(){
        return sharePre;
    }
}
