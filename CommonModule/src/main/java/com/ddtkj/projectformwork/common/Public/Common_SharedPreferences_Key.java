package com.ddtkj.projectformwork.common.Public;
/**
 * SharedPreferences存储的key
 * @ClassName: Common_SharedPreferences_Key
 * @Description: TODO
 * @author: yzc
 * @date: 2016-4-24 上午10:39:31
 */
public class Common_SharedPreferences_Key {
	public static String APP_Global_SharedPreferences="employers_Global_SharedPreferences";//app全局的SharedPreferences表名

	public static String APP_User_SharedPreferences="employers_User_SharedPreferences";//app用户信息的SharedPreferences表名

	public static String APP_SdCard_SharedPreferences="employers_SdCard_SharedPreferences";//SdCard的SharedPreferences表名

	public static String COOKIE_CACHE="COOKIE_CACHE";//存放cookie缓存

	public static String JPUSH_BUNDLE="JPUSH_BUNDLE";//存放Jpush传参

	public static String IS_PUSH_STATE="IS_PUSH_STATUS";//存放接收通知状态

	public static String IS_OPEN_AUTHORIZATION="IS_OPEN_AUTHORIZATION";//标识是否开启授权中心接口

	public static String USER_NAME_CACHE="usernameCache";//存放所有登录成功的用户名

	public static String IS_UPDATE="IS_UPDATE";//存放是否更新的状态

	public static String JD_PAY_TOKEN="jdPayToken";//存放京东token

	public static String FIRSTINSTALL = "FirstInstall";//记录是否第一次启动程序

	public static String WELCOME_PAGE_BEAN = "WelcomePageBean";//启动页bean对象

	public static String USER_REGISTER_CODE_TIME="USER_REGISTER_CODE_TIME";//存放注册页面获取验证码开始倒计时时间

	public static String USER_VERIFICATION_CODE_TIME="USER_VERIFICATION_CODE_TIME";//存放登录页面验证码登录获取短信验证码倒计时时间

	public static String USER_FORGOT_PASSWORD_CODE_TIME="USER_FORGOT_PASSWORD_CODE_TIME";//存放忘记密码页面获取验证码开始倒计时时间
 }
