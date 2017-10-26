package com.ddtkj.projectformwork.common.Public;

/**路由机制URL
 * Created by Administrator on 2017/6/3.
 */

public class Common_RouterUrl {
    //前缀
    public final static String RouterHTTP="crowdsourcingRoute://";
    //主app项目Host
    final static String mainAppHost=RouterHTTP+"crowdsourcing/";
    //主页面
    public final static String mainMainActivityRouterUrl =mainAppHost+"mainActivity";
    //webView页面
    public final static String mainWebViewRouterUrl =mainAppHost+"interactionWebView";
    //启动页面
    public final static String mainWelcomePageRouterUrl =mainAppHost+"welcomePage";
    //引导页面
    public final static String mainGuideRouterUrl =mainAppHost+"guide";
    //支付PayLibrary_Activity页面
    public final static String mainPayLibraryRouterUrl =mainAppHost+"payLibrary";
    //网络异常页面
    public final static String mainNetworkErrorRouterUrl =mainAppHost+"networkError";

//======================================================================================================
    //用户包名
    final static String userInfoAppHost=RouterHTTP+"userInfo/";
    //登陆界面
    public final static String userinfo_UserLogingRouterUrl =userInfoAppHost+"log";
    //忘记密码界面
    public final static String userinfo_ForgetPasswordRouterUrl =userInfoAppHost+"forgetPassword";
    //注册界面
    public final static String userinfo_UserRegisterRouterUrl =userInfoAppHost+"userRegister";
    //三方登陆界面
    public final static String userinfo_ThiryUserLogingRouterUrl =userInfoAppHost+"hirytLog";
    //设置登录密码界面
    public final static String userinfo_SetLogingPasswordRouterUrl =userInfoAppHost+"setLogingPassword";
//======================================================================================================
    //业务包名
    final static String businessAppHost=RouterHTTP+"business/";
    //业务主界面
    public final static String business_MainActivityRouterUrl =businessAppHost+"mainActivity";
    //==============================拦截器====================================
    //用户信息界面拦截器
    public final static String interceptor_UserInfo_RouterUrl =RouterHTTP+"userInfo";

}
