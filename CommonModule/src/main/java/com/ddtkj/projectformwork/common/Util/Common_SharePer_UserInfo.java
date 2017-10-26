package com.ddtkj.projectformwork.common.Util;

import android.app.Application;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_CookieMapBean;
import com.ddtkj.projectformwork.common.Public.Common_SharedPreferences_Key;
import com.utlis.lib.SharePre;

import static com.alibaba.fastjson.JSON.parseObject;

public class Common_SharePer_UserInfo extends Application {
    //初始化SharedPreferences
    static SharePre sharePre= Common_Application.getInstance().getUserSharedPreferences();;
    /**
     * 存放cookie缓存
     */
    public static void sharePre_PutCookieCache(Common_CookieMapBean cookieMapBean) {
        String JSONS = JSONObject.toJSONString(cookieMapBean);
        sharePre.put(Common_SharedPreferences_Key.COOKIE_CACHE, JSONS);
        sharePre.commit();
    }

    /**
     * 读取Cookie缓存
     *
     * @return
     */
    public static Common_CookieMapBean sharePre_GetCookieCache() {
        String JSONS = sharePre.getString(Common_SharedPreferences_Key.COOKIE_CACHE,
                "");
        return parseObject(JSONS, Common_CookieMapBean.class);
    }
    /**
     * 存放用户接收通知状态
     * @param isPushStatus
     */
    public static void sharePre_PutPushStatus(boolean isPushStatus){
        sharePre.put(Common_SharedPreferences_Key.IS_PUSH_STATE, isPushStatus);
        sharePre.commit();

    }
    /**
     * 获取用户接收通知状态
     * @return
     */
    public static boolean sharePre_GetPushStatus(){
        return sharePre.getBoolean(Common_SharedPreferences_Key.IS_PUSH_STATE, true);
    }
    /**
     * 获取极光推送的信息
     *
     * @return
     */
    public static Bundle sharePre_GetJpushBundle() {
        String jPusString=sharePre.getString(Common_SharedPreferences_Key.JPUSH_BUNDLE,"");
        Bundle bundle=new Bundle();
        if(!jPusString.isEmpty()){
            String [] jPusStrings=jPusString.split(",");
            //bundle.putString(JPushInterface.EXTRA_EXTRA,jPusStrings[0]);
            if(jPusStrings[1].contains("true")){
                bundle.putBoolean("isLoging",true);
            }else {
                bundle.putBoolean("isLoging",false);
            }
            return bundle;
        }
        return null;
    }
}
