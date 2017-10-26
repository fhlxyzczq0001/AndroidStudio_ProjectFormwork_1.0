package com.ddtkj.projectformwork.common.MVP.View.Interface.Activity;

import android.webkit.WebView;
/**交互webview的接口
 * @ClassName: com.ygworld.MVP.View.Interface
 * @author: Administrator 杨重诚
 * @date: 2016/11/3:9:26
 */

public interface Common_Act_WebView_View_Interface {
    /**
     * 初始化WebView
     * @param mWebView
     */
    public void initWebView(WebView mWebView);
    /**
     * 设置WebView
     *
     * @param mWebView
     * @param Url
     */
    public void setWebView(WebView mWebView, String Url);
    /**
     * 返回按钮的操作
     */
    public void finishAct();

    /**
     * 判断是否是全屏
     *
     * @return
     */
    public boolean inCustomView();
    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView();

    /**
     * webview重新加载
     */
    public void reload();

    /**
     * webview加载
     * @return
     */
    public void webViewLoadUrl(String url);

}
