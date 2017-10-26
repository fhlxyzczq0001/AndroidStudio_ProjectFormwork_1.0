package com.ddtkj.projectformwork.userinfo.Base.Application.release;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_UserInfoBean;
import com.ddtkj.projectformwork.userinfo.Base.Application.UserInfo_Application_Interface;
import com.utlis.lib.SharePre;

/**
 * @ClassName: Directly_Application
 * @author: Administrator杨重诚
 * @date: 2016/6/6 15:43
 */
//public class Directly_Application extends LitePalApplication {
public class UserInfo_Application implements UserInfo_Application_Interface {
    public static Application mApplication;//上下文
    static Common_Application mCommonApplication;//公共lib的Application
    static UserInfo_Application employersUserApplication;//自己的Application

    /**
     * 联调打包要在主项目app初始化这个方法
     * @param application
     * @param mCommonApplication
     * @return
     */
    public void initUserInfo_Application(Application application, Common_Application mCommonApplication){
        mApplication=application;
        UserInfo_Application.mCommonApplication=mCommonApplication;
        employersUserApplication=getInstance();
    }

    /**
     * 初始化配置文件
     * @param mContext
     */
    @Override
    public void requestProfile(Context mContext){
    }

    /**
     * 获取单例对象
     * @return
     */
    public static UserInfo_Application getInstance(){
        if(employersUserApplication==null){
            //第一次check，避免不必要的同步
            synchronized (UserInfo_Application.class){//同步
                if(employersUserApplication==null){
                    //第二次check，保证线程安全
                    employersUserApplication=new UserInfo_Application();
                }
            }
        }
        return employersUserApplication;
    }

    /**
     * 获取上下文
     * @return
     */
    public Application getApplication(){
        return mApplication;
    }

    /**
     * 获取用户SharePre
     *
     * @return
     */
    @Override
    public SharePre getUserSharedPreferences() {
        return mCommonApplication.getUserSharedPreferences();
    }

    // 取得用户信息
    @Override
    public Common_UserInfoBean getUseInfoVo() {
        if (mCommonApplication.getUseInfoVo() != null) {
            return mCommonApplication.getUseInfoVo();
        }
        return null;
    }
    // 设置用户信息
    @Override
    public void setUseInfoVo(Common_UserInfoBean useInfoVo) {
        mCommonApplication.setUserInfoBean(useInfoVo);
    }

    @Override
    public HttpDnsService getHttpDnsService() {
        return Common_Application.httpdns;
    }
}
