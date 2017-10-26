package com.ddtkj.projectformwork.userinfo.MVP.Presenter.Implement.Project;

import android.content.Context;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.projectformwork.userinfo.MVP.Presenter.Interface.Project.UserInfo_ProjectUtil_Interface;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * 应用需求工具类实现
 *
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:10
 */

public class UserInfo_ProjectUtil_Implement implements UserInfo_ProjectUtil_Interface {
    Common_ProjectUtil_Interface mCommon_projectUtil_interface;
    public UserInfo_ProjectUtil_Implement() {
        mCommon_projectUtil_interface=new Common_ProjectUtil_Implement();
    }

    /**
     * 根据URL跳转不同页面
     *
     * @param context
     * @param menuUrl
     * @param title
     */
    @Override
    public void urlIntentJudge(Context context, String menuUrl, String title) {
        mCommon_projectUtil_interface.urlIntentJudge(context,menuUrl,title);
    }
    /**
     * 将cookie同步到WebView
     * @param mContext
     * @param url
     * @param syncCookieListener
     */
    @Override
    public void syncCookie(final Context mContext, final String url, final  Common_ProjectUtil_Implement.SyncCookieListener syncCookieListener) {
        mCommon_projectUtil_interface.syncCookie(mContext, url, syncCookieListener);
    }
    /**
     * 请求应用版本最新版本
     *
     * @param source
     * @param resultCheckAppListener
     */
    @Override
    public void checkAppVersion(Context context,String source, Common_ProjectUtil_Implement.ResultCheckAppListener resultCheckAppListener) {
        mCommon_projectUtil_interface.checkAppVersion(context, source, resultCheckAppListener);
    }
    /**
     * 普通更新
     * @param content
     */
    @Override
    public void showAppUpdateDialog_Common(final Context context,String content,final String url) {
        mCommon_projectUtil_interface.showAppUpdateDialog_Common(context,content,url);
    }
    /**
     * 强制更新
     * @param content
     */
    @Override
    public void showAppUpdateDialog_Forced(final Context context,final String content, final String url) {
        mCommon_projectUtil_interface.showAppUpdateDialog_Forced(context,content,url);
    }

    /**
     * 获取下载的DialogBuilder
     * @return
     */
    @Override
    public NiftyDialogBuilder getDownloadDialogBuilder() {
        return mCommon_projectUtil_interface.getDownloadDialogBuilder();
    }
    /**
     * 设置维护页
     * @param context
     * @param msgs
     */
    @Override
    public void setServerState(final Context context, final String msgs) {
        mCommon_projectUtil_interface.setServerState(context,msgs);
    }
    /**
     * 设置WebView的参数并返回
     *
     * @param webView
     * @return
     */
    @Override
    public WebView setWebViewSetting(WebView webView) {
        return  mCommon_projectUtil_interface.setWebViewSetting(webView);
    }
    /**
     * 请求权限
     * @param context
     * @param requestPermissions 权限组
     * @param permissionNames 权限名称
     * @param permissionsResultListener
     */
    @Override
    public void onePermissions(final Context context, final String[] requestPermissions, final String[] permissionNames,final Common_ProjectUtil_Implement.PermissionsResultListener permissionsResultListener,boolean isNeedAgain){
        mCommon_projectUtil_interface.onePermissions(context,requestPermissions,permissionNames,permissionsResultListener,isNeedAgain);
    }
    /**
     * 是否显示密码
     * @param editText 输入框
     * @param imageView 图形控件
     */
    @Override
    public boolean isShowPassword(boolean isShowPassword, EditText editText, ImageView imageView){
        return mCommon_projectUtil_interface.isShowPassword(isShowPassword,editText,imageView);
    }
}
