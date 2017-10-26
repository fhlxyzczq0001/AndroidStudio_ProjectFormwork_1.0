package com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Project;

import android.content.Context;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**项目相关的工具类接口
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:09
 */

public interface Common_ProjectUtil_Interface {
    /**
     * 根据URL跳转不同页面
     * @param context
     * @param menuUrl
     * @param title
     */
    public void urlIntentJudge(Context context, String menuUrl, String title);
    /**
     * 将cookie同步到WebView
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH
     */
    public void syncCookie(Context mContext, String url, Common_ProjectUtil_Implement.SyncCookieListener syncCookieListener);
    /**
     * 普通更新
     * @param content
     */
    public void showAppUpdateDialog_Common(Context context, String content, String url);
    /**
     * 强制更新
     * @param content
     */
    public void showAppUpdateDialog_Forced(Context context, String content, String url);
    /**
     * 应用检测更新
     */
    public void checkAppVersion(Context context, String source, Common_ProjectUtil_Implement.ResultCheckAppListener resultCheckAppListener);

    /**
     * 获取下载的DialogBuilder
     * @return
     */
    public NiftyDialogBuilder getDownloadDialogBuilder();

    /**
     * 设置维护页
     * @param context
     * @param msgs
     */
    public void setServerState(final Context context, final String msgs);
    /**
     * 设置WebView的参数并返回
     * @param webView
     * @return
     */
    public WebView setWebViewSetting(WebView webView);

    /**
     * 请求权限
     * @param context
     * @param requestPermissions 权限组
     * @param permissionNames 权限名称
     * @param permissionsResultListener
     */
    public void onePermissions(final Context context, final String[] requestPermissions, final String[] permissionNames, final Common_ProjectUtil_Implement.PermissionsResultListener permissionsResultListener, boolean isNeedAgain);
    /**
     * 是否显示密码
     * @param editText 输入框
     * @param imageView 图形控件
     */
    public boolean isShowPassword(boolean isShowPassword, EditText editText, ImageView imageView);
}
