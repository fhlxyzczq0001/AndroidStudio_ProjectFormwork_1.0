package com.ddtkj.projectformwork.Base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.chenenyu.router.Router;
import com.ddtkj.projectformwork.BuildConfig;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpPath;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_UserInfoBean;
import com.ddtkj.projectformwork.common.Public.Common_PublicMsg;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.utlis.lib.ChannelUtil;
import com.utlis.lib.L;
import com.utlis.lib.SharePre;
import com.utlis.lib.ToolsUtils;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

import java.util.ArrayList;
import java.util.Arrays;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * @ClassName: My_Application
 * @author: Administrator杨重诚
 * @date: 2016/6/6 15:43
 */
public class My_Application extends Application {
    public static My_Application mApp;
    Common_Application mCommonApplication;
    public static My_Application getInstance() {
        return mApp;
    }
    //阿里HttpDns
    public static HttpDnsService httpdns;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initModule();//初始化Module模块
        CustomActivityOnCrash.install(this,-1);//初始化系统异常显示的activity(第二个参数是异常图片，-1是默认图片，否则传R.mipmap.icon)
        //TODO 打包时需把app项目下的common_build.gradle中的LOG_DEBUG定义为false(非调试模式)
        // 调试时，将第三个参数改为true
        Bugly.init(this, "cb1002aef1", BuildConfig.LOG_DEBUG);
        //bugly声明为开发设备，用于开发者调试使用
        Bugly.setIsDevelopmentDevice(getApplicationContext(), BuildConfig.LOG_DEBUG);
        //初始化友盟分享
        UMShareAPI.get(this);
        // 初始化httpdns
        httpdns = HttpDns.getService(getApplicationContext(), "183461");
        // 预解析热点域名
        httpdns.setPreResolveHosts(new ArrayList<>(Arrays.asList(Common_HttpPath.HOSTS)));
        mCommonApplication.setHttpDnsService(httpdns);
        //爱呗初始化
        //CrashHandler.getInstance().init(this);
        initTalkingData();//初始化Talkingdata
        //JPushInterface.init(this);            // 初始化 JPush
        RxRetrofitApp.init(this);//RXJAVA+OKHTTP+RETROFIT+...的初始化
        // 初始化路由框架
        Router.initialize(this);
        //动态判断是debug还是release
        if(BuildConfig.LOG_DEBUG){
            L.isDebug = true;//设置Log打印是否显示
            Config.DEBUG = true;//打印友盟分享Log调试打印
            //JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
            // Talkingdata
            TCAgent.LOG_ON = true;
            // 开启路由框架log
            Router.setDebuggable(true);
        }else{
            L.isDebug = false;//设置Log打印是否显示
            Config.DEBUG = false;//打印友盟分享Log调试打印
            //JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
            // Talkingdata
            TCAgent.LOG_ON = false;
        }
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信
        PlatformConfig.setWeixin(Common_PublicMsg.WX_APP_ID, Common_PublicMsg.WX_SECRET);
        //新浪微博
        PlatformConfig.setSinaWeibo(Common_PublicMsg.WB_APPID, Common_PublicMsg.WB_APPKEY,"http://sns.whalecloud.com/sina2/callback");//分享回调地址
        //qq
        PlatformConfig.setQQZone(Common_PublicMsg.QQ_APPID, Common_PublicMsg.QQ_APPKEY);

    }
    /**
     * 初始化Module模块
     */
    private void initModule(){
        //初始化公共Module
        mCommonApplication=Common_Application.initCommonApplication(mApp);
        if(!com.ddtkj.projectformwork.common.BuildConfig.IsBuildMudle){
            //初始化业务模块
            ToolsUtils.initReflectionModule("com.ddtkj.projectformwork.business.Base.Application.release.Business_Application","initBusiness_Application",
                    new Class[]{Application.class,Common_Application.class},new Object[]{mApp, mCommonApplication});
            //初始化用户中心模块
            ToolsUtils.initReflectionModule("com.ddtkj.projectformwork.userinfo.Base.Application.release.UserInfo_Application","initUserInfo_Application",
                    new Class[]{Application.class,Common_Application.class},new Object[]{mApp, mCommonApplication});
        }
    }
    /**
     * 获取全局SharePre
     *
     * @return
     */
    public SharePre getGlobalSharedPreferences() {
        return mCommonApplication.getGlobalSharedPreferences();
    }

    /**
     * 获取用户SharePre
     *
     * @return
     */
    public SharePre getUserSharedPreferences() {
        return mCommonApplication.getUserSharedPreferences();
    }

    /**
     * 获取sdcard的SharePre
     *
     * @return
     */
    public SharePre getSdCardSharedPreferences() {
        return mCommonApplication.getSdCardSharedPreferences();
    }

    /**
     * 初始化Talkingdata
     */
    private void initTalkingData() {
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        // TCAgent.init(this, "36584E61F35FBB6FB89ECCD0FF79C8CD",
        TCAgent.init(this, Common_PublicMsg.TCAgent_APPKEY, ChannelUtil.getChannel(this));
        TCAgent.setReportUncaughtExceptions(true);
        // talkingdata广告统计
        TalkingDataAppCpa
                .init(this, Common_PublicMsg.TalkingDataAppCpa_APPKEY, TCAgent.getPartnerId(this));
    }


    // 取得用户信息
    public Common_UserInfoBean getUseInfoVo() {
        if (mCommonApplication.getUseInfoVo() != null) {
            return mCommonApplication.getUseInfoVo();
        }
        return null;
    }

    // 设置用户信息
    public void setUseInfoVo(Common_UserInfoBean useInfoVo) {
        mCommonApplication.setUserInfoBean(useInfoVo);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        // 安装tinker
        Beta.installTinker();
    }
}
