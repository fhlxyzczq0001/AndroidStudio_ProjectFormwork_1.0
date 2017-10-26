package com.ddtkj.projectformwork.common.Util;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_UserLogingPhoneCacheBean;
import com.ddtkj.projectformwork.common.Public.Common_SharedPreferences_Key;
import com.utlis.lib.SharePre;

public class Common_SharePer_GlobalInfo {
    //初始化SharedPreferences
    static SharePre sharePre = Common_Application.getInstance().getGlobalSharedPreferences();
    /**
     * 存放京东token
     */
    public static void sharePre_PutJD_Token(String jdPayToken) {
        sharePre.put(Common_SharedPreferences_Key.JD_PAY_TOKEN, jdPayToken);
        sharePre.commit();
    }

    /**
     * 获取京东token
     */
    public static String sharePre_GetJD_Token() {
        return sharePre.getString(Common_SharedPreferences_Key.JD_PAY_TOKEN, "");
    }
    /**
     * 存放是否开启授权标识
     *
     */
    public static void sharePre_PutIsOpenAuthorization(boolean isOpenAuthorization) {
        sharePre.put(Common_SharedPreferences_Key.IS_OPEN_AUTHORIZATION, isOpenAuthorization);
        sharePre.commit();
    }

    /**
     * 获取是否开启授权标识
     *
     * @return
     */
    public static boolean sharePre_GetIsOpenAuthorization() {
        return sharePre.getBoolean(Common_SharedPreferences_Key.IS_OPEN_AUTHORIZATION, false);
    }
    /**
     * -存放登录成功的用户名
     *
     * @param userName
     */
    public static void sharePre_PutUserNameCache(String userName) {
        //判断是否已有登录账号
        Common_UserLogingPhoneCacheBean logingPhoneCacheBean = sharePre_GetUserNameCache();
        if (logingPhoneCacheBean == null) {
            logingPhoneCacheBean = new Common_UserLogingPhoneCacheBean();
        }
        logingPhoneCacheBean.getPhoneCache().add(userName);//添加登录名
        String json = JSONObject.toJSONString(logingPhoneCacheBean);//转化成json存放
        sharePre.put(Common_SharedPreferences_Key.USER_NAME_CACHE, json);//存放缓存对象
        sharePre.commit();
    }

    /**
     * 获取存放登录成功的用户名
     *
     * @return
     */
    public static Common_UserLogingPhoneCacheBean sharePre_GetUserNameCache() {
        String userName = sharePre.getString(Common_SharedPreferences_Key.USER_NAME_CACHE,
                "");
        if (userName != null && !userName.equals("")) {
            return JSONObject.parseObject(userName, Common_UserLogingPhoneCacheBean.class);
        }
        return null;
    }
    /**
     * 存放是否更新应用
     *
     * @param isUpdate
     */
    public static void sharePre_PutIsUpdate(boolean isUpdate) {
        sharePre.put(Common_SharedPreferences_Key.IS_UPDATE, isUpdate);
        sharePre.commit();
    }

    /**
     * 获取是否更新应用
     *
     * @return
     */
    public static boolean sharePre_GetIsUpdate() {
        return sharePre.getBoolean(Common_SharedPreferences_Key.IS_UPDATE, false);
    }
}
