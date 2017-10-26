package com.ddtkj.projectformwork.common.HttpRequest;

/**
 * 网络请求路径
 * 
 * @ClassName: Common_HttpPath
 * @Description: TODO
 * @author: yzc
 * @date: 2016-4-24 上午10:38:33
 */
public class Common_HttpPath {
//========================众包主项目===============================
//连接前缀
private static String HTTP = "http://";
	//域名
    public static String HOSTS = "apk.ygqq.com";
	//测试域名
    //public static String HOSTS = "ygqq-apk.dadetongkeji.net.cn";
	//后台项目名
	public static String PROJECT = "/ygqq/";
	//整体的域名地址
	public static String HTTP_HOST = HTTP + HOSTS;
	//整体后台路径
	public static String HTTP_HOST_PROJECT = HTTP_HOST + PROJECT;

//==============================支付相关====================================
	/**
	 * 请求用户信息
	 */
	public static final String URL_API_USER_INFO = "user_info.do";
	/**
	 * 购买会员等级卡
	 */
	public static final String URL_MEMBER_LEVEL_PAY = "memberLevelPay.do";
	/**
	 * 查询用户是否充值成功
	 */
	public static final String CHECK_RECHARGE_TYPE = "check_recharge_type.do";
//===============================项目全局相关===================================
	/**
	 * 维护界面
	 */
	public static final String SERVER_STATUS = "http://update.ygqq.com/ServerStatus.json";
	/**
	 * 检测更新
	 */
	public static final String CHECK_UPDATE = "checkUpdate.do";
	/**
	 * 请求获取地区(省市区)
	 */
	public static final String URL_API_AREA_INFO = "area_info.do";
	/**
	 * 请求启动页更新
	 */
	public static final String URL_API_STARTPAGEUPDATE = "start_page.do";
}
