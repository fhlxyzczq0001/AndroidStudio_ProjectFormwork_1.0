package com.ddtkj.projectformwork.Util;


import com.ddtkj.projectformwork.Base.My_Application;
import com.ddtkj.projectformwork.common.Public.Common_SharedPreferences_Key;
import com.utlis.lib.SharePre;

public class Main_SharePer_GlobalInfo {
    //初始化SharedPreferences
    static SharePre sharePre = My_Application.getInstance().getGlobalSharedPreferences();

    public static SharePre getSharePre() {
        return sharePre;
    }

    /**
     * 存放是否第一次启动应用
     *
     * @param isFirst
     */
    public static void sharePre_PutFirstInstall(boolean isFirst) {
        sharePre.put(Common_SharedPreferences_Key.FIRSTINSTALL, isFirst);
        sharePre.commit();
    }

    /**
     * 获取用户是否第一次启动应用
     *
     * @return
     */
    public static boolean sharePre_GetFirstInstall() {
        return sharePre.getBoolean(Common_SharedPreferences_Key.FIRSTINSTALL, true);
    }
}
