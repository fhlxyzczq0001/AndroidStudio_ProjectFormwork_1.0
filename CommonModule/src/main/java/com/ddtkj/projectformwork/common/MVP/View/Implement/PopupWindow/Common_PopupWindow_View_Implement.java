package com.ddtkj.projectformwork.common.MVP.View.Implement.PopupWindow;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ddtkj.projectformwork.common.MVP.View.Interface.PopupWindow.Common_PopupWindow_View_Interface;
import com.ddtkj.projectformwork.common.R;


/**
 * PopupWindow实现类
 *
 * @author: Administrator 杨重诚
 * @date: 2016/10/11:16:56
 */

public class Common_PopupWindow_View_Implement implements Common_PopupWindow_View_Interface {
    PopupWindow commonWebviewPopupWindow = null;//通用webview弹窗

    public Common_PopupWindow_View_Implement() {
    }

    /**
     * 通用webview弹窗
     *
     * @param context
     * @param url
     */
    @Override
    public void commonWebviewPopup(int parentLayout, final Context context, String url) {
        View parent = LayoutInflater.from(context).inflate(parentLayout, null);
        View popupwindowView = LayoutInflater.from(context).inflate(R.layout.common_popup_webview, null);
        LinearLayout linearLayout = (LinearLayout) popupwindowView.findViewById(R.id.parentLayout);
        ImageView imgExit = (ImageView) popupwindowView.findViewById(R.id.imgExit);
        WebView mWebView = (WebView) popupwindowView.findViewById(R.id.webView);

        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        //mWebView.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //提高渲染的优先级
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //popupwindow初始化设置
        commonWebviewPopupWindow = setPopupWindow(context, popupwindowView, commonWebviewPopupWindow);
        //点击布局隐藏事件
        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonWebviewPopupWindow.dismiss();
            }
        });
        //点击布局隐藏事件
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonWebviewPopupWindow.dismiss();
            }
        });
        commonWebviewPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);//设置显示位置
    }

    /**
     * 设置PopupWindow
     *
     * @param context
     * @param popupwindowView
     */
    @Override
    public PopupWindow setPopupWindow(Context context, View popupwindowView, PopupWindow popupWindow) {
        //popupwindow初始化设置
        popupWindow = new PopupWindow(popupwindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);//设置点击其它地方隐藏弹出框
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.Common_popwindow_slidebottom_anim_style);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return popupWindow;
    }
}
