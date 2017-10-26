package com.ddtkj.projectformwork.common.MVP.View.Implement.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.Base.Common_BaseActivity;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_CookieMapBean;
import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Activity.Common_Act_WebView_Presenter_Javascript_Implement;
import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Activity.Common_Act_WebView_Presenter_Javascript_Interface;
import com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.projectformwork.common.MVP.View.Interface.Activity.Common_Act_WebView_View_Interface;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.Public.Common_SharedPreferences_Key;
import com.ddtkj.projectformwork.common.R;
import com.ddtkj.projectformwork.common.Util.Common_SharePer_UserInfo;
import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.utlis.lib.L;
import com.utlis.lib.SharePre;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

/**
 * 交互webview的实现类
 *
 * @ClassName: com.ygworld.MVP.View.Implement.Activity.WebView
 * @author: Administrator 杨重诚
 * @date: 2016/11/3:9:26
 */
@Route(Common_RouterUrl.mainWebViewRouterUrl)
public class Common_Act_WebView_View_Implement extends Common_BaseActivity implements Common_Act_WebView_View_Interface {
    //进度条
    ProgressBarDeterminate progressDeterminate;
    //WebView
    WebView webView;
    // 全屏时视频加载view
    FrameLayout videoview;

    private View xCustomView;
    private WebChromeClient.CustomViewCallback xCustomViewCallback;
    private SampleWebChromeClient xwebchromeclient;
    ValueCallback mUploadMessage;

    Message mMessage;
    Handler webViewHandle;

    private String url;//页面链接
    private String bar_name;//标题
    private String shareLink;// 分享链接
    private String shareImage;//分享图片
    private String shareTitle;// 分享标题
    private String shareContent;// 分享内容
    private String marker = "";// 用于标记一些特殊的类别，比如“赚“活动，需要做特殊处理
    Bitmap uploadBitmap = null;//上传图片的bitmap

    Common_ProjectUtil_Interface mProjectUtil_presenter_implement;
    Common_Act_WebView_Presenter_Javascript_Interface mMainWebView_presenter_javascript_interface;

    /**
     * 初始化Handler
     */
    private void initHandler() {
        webViewHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                this.obtainMessage();
            }
        };
    }

    @Override
    protected void initMyView() {
        //进度条
         progressDeterminate=(ProgressBarDeterminate)findViewById(R.id.progressDeterminate);
        //WebView
         webView=(WebView)findViewById(R.id.webView);
        // 全屏时视频加载view
         videoview=(FrameLayout)findViewById(R.id.video_view);
    }

    @Override
    protected void initApplication() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.common_act_webview_interaction_layout);
        isShowSystemBarTintManager = false;//不显示沉浸式状态栏
        //设置软键盘弹起方式
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setBackgroundDrawableResource(R.color.black);//设置窗体背景色
    }

    @Override
    protected void init() {
        mProjectUtil_presenter_implement = new Common_ProjectUtil_Implement();
        mMainWebView_presenter_javascript_interface = new Common_Act_WebView_Presenter_Javascript_Implement();
        setWebView(webView, url);//设置webview
        initHandler();//初始化Handler
    }

    @Override
    protected void setListeners() {
        //返回按钮的监听
        tvLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAct();
            }
        });
    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("", R.color.white, R.color.account_text_gray, true, false);
        settvTitleStr(tvLeftTitle, "返回", R.color.account_text_gray);
    }

    @Override
    protected void getData() {

    }

    /**
     * 获取页面传值
     */
    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        bar_name = mBundle.getString("bar_name", "");
        url = mBundle.getString("url", "");
        shareLink = mBundle.getString("shareLink", "");
        shareTitle = mBundle.getString("shareTitle", "");
        shareContent = mBundle.getString("shareContent", "");
        marker = mBundle.getString("marker", "");
    }

    /**
     * 设置WebView
     *
     * @param mWebView
     * @param Url
     */
    @Override
    public void setWebView(final WebView mWebView, final String Url) {
        initWebView(mWebView);
        mProjectUtil_presenter_implement.syncCookie(context, url, new Common_ProjectUtil_Implement.SyncCookieListener() {
            @Override
            public void onResult(boolean isSucc) {
                if (isSucc) {
                    //将cookie同步到WebView
                    mWebView.loadUrl(Url);
                }
            }
        });
    }


    /**
     * 初始化WebView
     *
     * @param mWebView
     */
    @SuppressLint("JavascriptInterface")
    @Override
    public void initWebView(WebView mWebView) {
        mWebView = mProjectUtil_presenter_implement.setWebViewSetting(mWebView);
        //设置js调用本地方法
        mWebView.addJavascriptInterface(mMainWebView_presenter_javascript_interface, "android");

        //设置监听
        mWebView.setWebViewClient(new SampleWebViewClient());
        xwebchromeclient = new SampleWebChromeClient();
        mWebView.setWebChromeClient(xwebchromeclient);

        //设置监听下载功能
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // 监听下载功能，当用户点击下载链接的时候，直接调用系统的浏览器来下载
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    /**
     * 自定义WebViewClient
     *
     * @author Administrator
     */
    private class SampleWebViewClient extends WebViewClient {
        // 是否在本WebView中跳转还是通过系统浏览器跳转，true为webview内跳转
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            if (url != null && !url.equals("")) {
                //判断是不是需要重定向到登陆页面
                if (url.trim().equals("ddt://ation/login")) {
                    upLoginOnAndroid();
                } else {
                    //视图内加载url
                    progressDeterminate.setVisibility(View.VISIBLE);
                    //302到授权中心
                    if (url.startsWith("http://passport.ddtkj.net") || url.startsWith("http://account.dadetongkeji.net.cn")) {
                        try {
                            URL parsedUrl = new URL(url);
                            SharePre sharePre = new SharePre(context, Common_SharedPreferences_Key.COOKIE_CACHE, Context.MODE_PRIVATE);
                            //获取cookie缓存对象
                            Common_CookieMapBean cookieMapBean = Common_SharePer_UserInfo.sharePre_GetCookieCache();
                            if (null != cookieMapBean) {
                                HashSet<String> hosts_cookie = cookieMapBean.getCookieMap().get(parsedUrl.getHost());
                                if (null != hosts_cookie && hosts_cookie.size() > 0) {
                                    CookieManager cookieManager = CookieManager.getInstance();
                                    //                                cookieManager.removeAllCookie();
                                    cookieManager.setAcceptCookie(true);
                                    //                                cookieManager.removeSessionCookie();
                                    for (String cookie : hosts_cookie) {
                                        if (url.startsWith("http://passport.ddtkj.net")) {//正式服务器
                                            cookieManager.setCookie("http://passport.ddtkj.net/", cookie);
                                        } else {//测试服务器
                                            cookieManager.setCookie("http://account.dadetongkeji.net.cn/", cookie);
                                        }
                                    }
                                }
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    view.loadUrl(url);
                }
                return true;
            }
            return false;
        }

        // 页面开始加载
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            L.e("url:", url);
            progressDeterminate.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        // 页面加载完成
        @Override
        public void onPageFinished(WebView view, String url) {
            progressDeterminate.setVisibility(View.GONE);
            String t = view.getTitle();
            if (null == t || t.equals("")) {
                return;
            }
            if (t.length() > 10) {
                t = t.substring(0, 10) + "...";
            }
            if (null != bar_name && !"".equals(bar_name)) {
                settvTitleStr(tvMainTitle, bar_name, R.color.account_text_gray);
                L.e("bar_name:", bar_name);
            } else {
                settvTitleStr(tvMainTitle, t, R.color.account_text_gray);
                L.e("t:", t);
            }
            super.onPageFinished(view, url);
        }
    }

    private final static int FILECHOOSER_RESULTCODE = 1;

    /**
     * 自定义WebChromeClient
     */
    private class SampleWebChromeClient extends WebChromeClient {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;

        @Override
        // 播放网络视频时全屏会被调用的方法
        public void onShowCustomView(View view, CustomViewCallback callback) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            webView.setVisibility(View.GONE);
            // 如果一个视图已经存在，那么立刻终止并新建一个
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            videoview.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            videoview.setVisibility(View.VISIBLE);
        }

        @Override
        // 视频播放退出全屏会被调用的
        public void onHideCustomView() {

            if (xCustomView == null)// 不是全屏播放状态
                return;
            // Hide the custom view.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            videoview.removeView(xCustomView);
            xCustomView = null;
            videoview.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();

            webView.setVisibility(View.VISIBLE);

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            super.onProgressChanged(view, newProgress);
            progressDeterminate.setProgress(newProgress);
            if (newProgress >= 100) {
                progressDeterminate.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 返回按钮的操作
     */
    @Override
    public void finishAct() {
        if (marker != null && !marker.equals("") && marker.equals("earn")
                && webView.canGoBack()) {// 表示“赚”活动的界面
            webView.goBack();
            return;
        }
        webView.loadUrl("about:blank");//加载空页面
        FinishA();
    }

    /**
     * webview重新加载
     */
    @Override
    public void reload() {
        webView.reload();
    }

    /**
     * H5界面请求用户登录
     */
    public void upLoginOnAndroid() {
        //判断用户登录状态 ，如果没有登录，则跳转至登录页面
        if (Common_Application.getInstance().getUseInfoVo() == null) {
            getIntentTool().intent_RouterTo(context, Common_RouterUrl.userinfo_UserLogingRouterUrl);
            return;
        }
        mProjectUtil_presenter_implement.syncCookie(context, url, new Common_ProjectUtil_Implement.SyncCookieListener() {
            @Override
            public void onResult(boolean isSucc) {
                if (isSucc) {
                    //将cookie同步到WebView
                    webView.reload();//刷新webview
                }
            }
        });
    }

    /**
     * webview加载
     *
     * @return
     */
    @Override
    public void webViewLoadUrl(final String url) {
        webViewHandle.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                webView.loadUrl(url);
            }
        });
    }

    /**
     * 判断是否是全屏
     *
     * @return
     */
    @Override
    public boolean inCustomView() {
        return (xCustomView != null);
    }

    /**
     * 全屏时按返加键执行退出全屏方法
     */
    @Override
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onMyPause() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.stopLoading();
            webView.removeAllViews();
            webView.destroy();
        }
        if (uploadBitmap != null) {
            uploadBitmap.recycle();
        }
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                hideCustomView();
                return true;
            } else if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                webView.loadUrl("about:blank");
                FinishA();
            }
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
