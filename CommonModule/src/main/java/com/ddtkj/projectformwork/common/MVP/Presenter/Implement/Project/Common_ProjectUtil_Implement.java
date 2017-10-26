package com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpPath;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_VersionBean;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus.Common_SyncCookie_EventBus;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.R;
import com.ddtkj.projectformwork.common.Util.Common_CustomDialogBuilder;
import com.ddtkj.projectformwork.common.Util.IntentUtil;
import com.fenjuly.library.ArrowDownloadButton;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.utlis.lib.AppUtils;
import com.utlis.lib.L;
import com.utlis.lib.StringUtils;
import com.utlis.lib.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 应用需求工具类实现
 *
 * @author: Administrator 杨重诚
 * @date: 2016/11/2:14:10
 */

public class Common_ProjectUtil_Implement implements Common_ProjectUtil_Interface {
    Common_Application mCommonApplication;
    IntentUtil intentUtil;
    Common_Base_HttpRequest_Interface mCommon_base_httpRequest_interface;//请求网络数据的model实现类
    boolean isServerState = false;//标识是否启动了维护页面
    String TAG = "Common_ProjectUtil_Implement";
    Common_CustomDialogBuilder customDialogBuilder;
    NiftyDialogBuilder dialogBuilder = null;

    public Common_ProjectUtil_Implement() {
        mCommonApplication = Common_Application.getInstance();
        intentUtil = new IntentUtil();
    }

    public static Common_ProjectUtil_Implement commonProjectUtilInterface;

    public static Common_ProjectUtil_Interface getInstance() {
        if (commonProjectUtilInterface == null) {
            //第一次check，避免不必要的同步
            synchronized (Common_ProjectUtil_Implement.class) {//同步
                if (commonProjectUtilInterface == null) {
                    //第二次check，保证线程安全
                    commonProjectUtilInterface = new Common_ProjectUtil_Implement();
                }
            }
        }
        return commonProjectUtilInterface;
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
        L.e("menuUrl:", menuUrl);
        final Bundle bundle = new Bundle();
        if (menuUrl.startsWith("http")) {
            //跳转至WebView页面
            bundle.putString("barname", title);
            bundle.putString("url", menuUrl);
            bundle.putString("shareLink", "");
            bundle.putString("shareContent", "");
            bundle.putString("shareTitle", "");
            intentUtil.intent_RouterTo(context, Common_RouterUrl.mainWebViewRouterUrl, bundle);
        } else if (menuUrl.contains(Common_RouterUrl.RouterHTTP) && menuUrl.contains("tab=")) {
            //不同module跳转到对应的不同tab
            intentUtil.intent_destruction_other_activity_RouterTo(context, menuUrl);
        } else if (menuUrl.contains(Common_RouterUrl.RouterHTTP)) {
            //路由跳转
            intentUtil.intent_RouterTo(context, menuUrl);
        }
    }

    public interface SyncCookieListener {
        /**
         * 同步cookie回调
         *
         * @param isSucc
         */
        public void onResult(boolean isSucc);
    }

    /**
     * 将cookie同步到WebView
     *
     * @param mContext
     * @param url
     * @param syncCookieListener
     */
    @Override
    public void syncCookie(final Context mContext, final String url, final SyncCookieListener syncCookieListener) {
        setCookie(mContext, url, syncCookieListener);
    }

    /**
     * 设置Cookie
     *
     * @return
     */
    private void setCookie(Context mContext, String url, SyncCookieListener syncCookieListener) {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.setCookie(StringUtils.urlSetCookie(url), "platform=app");
        if (mCommonApplication.getUseInfoVo() != null && mCommonApplication.getUseInfoVo().getUserId() != null) {
            cookieManager.setCookie(StringUtils.urlSetCookie(url), "islogin=true");
        }
        CookieSyncManager.getInstance().sync();
        syncCookieListener.onResult(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里发送粘性事件
                EventBus.getDefault().post(new Common_SyncCookie_EventBus(true));
            }
        }, 300);
    }

    /**
     * 请求应用版本最新版本
     *
     * @param source
     * @param resultCheckAppListener
     */
    @Override
    public void checkAppVersion(Context context, String source, ResultCheckAppListener resultCheckAppListener) {
        int code = AppUtils.getAppVersionCode(mCommonApplication.getApplication());
        checkAppVersion(context, checkAppVersion_Params(String.valueOf(code)), source, resultCheckAppListener);
    }

    public Map<String, Object> checkAppVersion_Params(String code) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("VersionCode", code);
        return params;
    }

    /**
     * 返回是否更新的标识状态
     */
    public interface ResultCheckAppListener {
        void onResult(boolean isUpdata);
    }

    /**
     * 获取请求接口
     */
    public Common_Base_HttpRequest_Interface getHttpInterface() {
        if (mCommon_base_httpRequest_interface == null) {
            mCommon_base_httpRequest_interface = new Common_Base_HttpRequest_Implement();
        }
        return mCommon_base_httpRequest_interface;
    }

    /**
     * 请求应用版本最新版本
     *
     * @param params
     * @param source 区分进入应用自动请求，手动更新abot
     */
    public void checkAppVersion(final Context context, Map<String, Object> params, final String source, final ResultCheckAppListener resultCheckAppListener) {
        getHttpInterface().requestData(context, Common_HttpPath.CHECK_UPDATE, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                Common_VersionBean versionBean = null;
                if (isSucc) {
                    if (request_bean.getData() != null) {
                        versionBean = JSONObject.parseObject(request_bean.getData().toString(),
                                Common_VersionBean.class);
                    }
                    if (versionBean != null) {
                        final String content = versionBean.getContent();
                        final String url = versionBean.getUrl();
                        L.e("update", "versionBean.getUrl():----" + versionBean.getUrl());
                        if (versionBean.getCode().equals("1")) {
                            //普通更新
                            showAppUpdateDialog_Common(context, content, url);
                            resultCheckAppListener.onResult(true);//将更新标识为有更新状态
                        } else if (versionBean.getCode().equals("2")) {
                            //强制更新
                            showAppUpdateDialog_Forced(context, content, url);
                            resultCheckAppListener.onResult(true);
                        } else if (source.equals("abot")) {
                            ToastUtils.RightImageToast(context, "暂无更新");
                            resultCheckAppListener.onResult(false);
                        }
                    }
                }
            }
        }, false, Common_HttpRequestMethod.POST);
    }

    /**
     * 普通更新
     *
     * @param content
     */
    @Override
    public void showAppUpdateDialog_Common(final Context context, String content, final String url) {
        //普通更新
        //强制更新
        if (customDialogBuilder == null) {
            customDialogBuilder = new Common_CustomDialogBuilder(context);
            customDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    customDialogBuilder = null;
                    L.e(TAG, "强制更新Dialog对话框被销毁");
                }
            });
        }
        if (customDialogBuilder.isShowing()) {
            L.e(TAG, "强制更新Dialog对话框已显示");
            return;
        } else {
            L.e(TAG, "强制更新Dialog对话框未显示");
        }
        final NiftyDialogBuilder dialogBuilder = customDialogBuilder.showDialog("软件更新", content, R.color.app_text_gray, true, "下次再说", R.color.gray, "马上更新", R.color.app_color);
        dialogBuilder.setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //马上更新
                L.e("update", "url:---" + url);
                startUpdateService(context, url, 0);
                dialogBuilder.dismiss();
            }
        });
    }

    /**
     * 强制更新
     *
     * @param content
     */
    @Override
    public void showAppUpdateDialog_Forced(final Context context, final String content, final String url) {
        //强制更新
        if (customDialogBuilder == null) {
            customDialogBuilder = new Common_CustomDialogBuilder(context);
            customDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    customDialogBuilder = null;
                    L.e(TAG, "强制更新Dialog对话框被销毁");
                }
            });
        }
        if (customDialogBuilder.isShowing()) {
            L.e(TAG, "强制更新Dialog对话框已显示");
            return;
        } else {
            L.e(TAG, "强制更新Dialog对话框未显示");
        }
        dialogBuilder = customDialogBuilder.showDialog("软件更新", content, R.color.app_text_gray, false, "下次再说", R.color.gray, "马上更新", R.color.app_color);
        dialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        dialogBuilder.findViewById(R.id.icon_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭应用
                dialogBuilder.dismiss();
                ((Activity) context).finish();
                System.exit(0);
            }
        });
        dialogBuilder.setButton1Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭应用
                dialogBuilder.dismiss();
                ((Activity) context).finish();
                System.exit(0);
            }
        });
        dialogBuilder.setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭应用
                dialogBuilder.withTitle(null)
                        .withDialogColor(context.getResources().getColor(R.color.white))
                        .setCustomView(R.layout.common_popupwindow_download_layout, context)
                        .withButton1Text(null)
                        .withButton2Text(null);
                TextView downloadTitle = (TextView) dialogBuilder.findViewById(R.id.downloadTitle);
                ArrowDownloadButton button = (ArrowDownloadButton) dialogBuilder.findViewById(R.id.arrow_download_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //马上更新
                        startUpdateService(context, url, 1);
                    }
                });
                //马上更新
                startUpdateService(context, url, 1);
            }
        });
    }

    /**
     * 获取下载的DialogBuilder
     *
     * @return
     */
    @Override
    public NiftyDialogBuilder getDownloadDialogBuilder() {
        return dialogBuilder;
    }

    /**
     * 启动下载service
     *
     * @param url
     */
    private void startUpdateService(Context context, String url, int isForced) {
        // 启动service后台下载
        Intent mIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("apkUrl", url);
        bundle.putString("apkName", context.getResources().getString(R.string.app_name));
        bundle.putInt("apkIcon", 0);
        bundle.putInt("isForced", isForced);
        mIntent.putExtras(bundle);

        mIntent.setAction(context.getPackageName()+".Service.UploadService");
        mIntent.setPackage(context.getPackageName());
        context.startService(mIntent);
    }

    /**
     * 设置启动维护页标识的方法
     *
     * @param isServerState
     */
    public void setIsServerState(boolean isServerState) {
        this.isServerState = isServerState;
    }

    /**
     * 设置维护页
     *
     * @param context
     * @param msgs
     */
    @Override
    public void setServerState(final Context context, final String msgs) {
        if (isServerState) {
            return;
        }
        requestServerState(context, new ServerStateResultListener() {
            @Override
            public void onResult(boolean isSucc, String msg, String request) {
                if (isSucc) {
                    JSONObject jsonObject = JSONObject.parseObject(request);
                    if (null != jsonObject && !jsonObject.isEmpty()) {
                        if (null != jsonObject.getString("url") && !jsonObject.getString("url").isEmpty()) {
                            //跳转至WebView页面
                            Bundle bundle = new Bundle();
                            bundle.putString("barname", "维护页面");
                            bundle.putString("url", jsonObject.getString("url"));
                            new IntentUtil().intent_RouterTo(context, Common_RouterUrl.mainWebViewRouterUrl, bundle);

                        } else {
                            ToastUtils.WarnImageToast(context, msg);
                        }
                    } else {
                        ToastUtils.WarnImageToast(context, msg);
                    }
                } else {
                    ToastUtils.ErrorImageToast(context, msgs);
                }
            }
        });
    }

    /**
     * 请求维护页回调
     */
    public interface ServerStateResultListener {
        public void onResult(boolean isSucc, String msg, String request);
    }

    /**
     * 请求维护页面
     */
    public void requestServerState(Context context, final ServerStateResultListener serverStateResultListener) {
        setIsServerState(true);
        getHttpInterface().requestData(context, Common_HttpPath.SERVER_STATUS, null, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                serverStateResultListener.onResult(isSucc, msg, request_bean.getData().toString());
            }
        }, false, Common_HttpRequestMethod.GET);
    }

    /**
     * 设置WebView的参数并返回
     *
     * @param webView
     * @return
     */
    @SuppressLint("NewApi")
    @Override
    public WebView setWebViewSetting(WebView webView) {
        WebView mWebView = webView;
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);//设置自适应任意大小的pc网页

        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
         * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setSupportZoom(true);   // 支持缩放
        mWebView.getSettings().setBuiltInZoomControls(true);// 设置隐藏缩放按钮
        mWebView.getSettings().setDisplayZoomControls(false);//设定缩放控件隐藏
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口

        //mWebView.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //提高渲染的优先级
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(false);

        mWebView.getSettings().setAllowFileAccess(true);//设置可以访问文件

        mWebView.getSettings().setSavePassword(true);
        mWebView.getSettings().setSaveFormData(true);// 保存表单数据

        mWebView.getSettings().setGeolocationEnabled(true);// 启用地理定位
        mWebView.getSettings().setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");// 设置定位的数据库路径

        //把图片加载放在最后来加载渲染
        mWebView.getSettings().setBlockNetworkImage(false);
        mWebView.getSettings().setLoadsImagesAutomatically(true); //自动加载图片
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);

        return mWebView;
    }

    /**
     * 请求权限回调
     */
    public interface PermissionsResultListener {
        /**
         * 回调
         *
         * @param isSucc          成功失败
         * @param failPermissions 失败权限组
         */
        public void onResult(boolean isSucc, List<String> failPermissions);
    }

    /**
     * 请求权限(注：权限组合申请，call方法的执行是在视图授权全部确认结束后才依次返回，不是点击一个授权就回调一次)
     *
     * @param context
     * @param requestPermissions 权限组
     * @param permissionNames 权限名称
     * @param permissionsResultListener
     */
    boolean isAuthoriseSuccess;//标识是否授权成功，有一个没授权成功就返回失败
    List<String> failPermissions;//失败权限组
    int authoriseNumber = 0;//授权完成次数

    @Override
    public void onePermissions(final Context context, final String[] requestPermissions, final String[] permissionNames, final PermissionsResultListener permissionsResultListener, final boolean isNeedAgain) {
        //初始化参数设置
        isAuthoriseSuccess = true;
        authoriseNumber = 0;
        failPermissions = new ArrayList<>();
        RxPermissions rxPermissions = new RxPermissions((Activity) context);
        rxPermissions.requestEach(requestPermissions)
                .subscribe(new Action1<Permission>() {
                               @Override
                               public void call(Permission permission) {
                                   for (int i = 0; i < requestPermissions.length; i++) {
                                       if (!permission.name.equals(requestPermissions[i])) {
                                           continue;
                                       }
                                       if (permission.granted) {
                                           L.e("用户已经同意该权限", "用户已经同意" + permissionNames[i] + "该权限");
                                           authoriseNumber++;
                                       } else if (permission.shouldShowRequestPermissionRationale) {
                                           // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                           L.e("用户已经拒绝该权限", "用户已经拒绝" + permissionNames[i] + "该权限");
                                           ToastUtils.WarnImageToast(context, permissionNames[i] + "权限被拒绝，若无相应权限会影响使用");
                                           authoriseNumber++;
                                           if (isNeedAgain) {
                                               //二次确认权限
                                               onePermissions(context, requestPermissions[i], permissionNames[i], new PermissionsResultListener() {
                                                   @Override
                                                   public void onResult(boolean isSucc, List<String> fail2Permissions) {
                                                       //如果二次授权仍然拒绝
                                                       if (!isSucc) {
                                                           isAuthoriseSuccess = false;
                                                           failPermissions.addAll(fail2Permissions);
                                                           L.e("二次确认权限回调仍然拒绝", isAuthoriseSuccess + "=========" + authoriseNumber);
                                                       }
                                                       if (authoriseNumber == requestPermissions.length) {
                                                           permissionsResultListener.onResult(isAuthoriseSuccess, failPermissions);
                                                       }
                                                   }
                                               });
                                           } else {
                                               isAuthoriseSuccess = false;
                                               failPermissions.add(requestPermissions[i]);
                                           }
                                       } else {
                                           // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                           L.e("权限被拒绝『不再询问』", permissionNames[i] + "权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                                           ToastUtils.WarnImageToast(context, permissionNames[i] + "权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                                           authoriseNumber++;
                                           isAuthoriseSuccess = false;
                                           failPermissions.add(requestPermissions[i]);
                                           if (authoriseNumber == requestPermissions.length) {
                                               permissionsResultListener.onResult(isAuthoriseSuccess, failPermissions);
                                           }
                                       }
                                   }
                                   if (authoriseNumber == requestPermissions.length) {
                                       permissionsResultListener.onResult(isAuthoriseSuccess, failPermissions);
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable t) {
                                L.e("可能是授权异常的情况下的处理", "可能是授权异常的情况下的处理");
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {

                            }
                        }
                );
    }

    /**
     * 请求单条权限
     *
     * @param context
     * @param requestPermission
     * @param permissionName
     * @param permissionsResultListener
     */
    private List<String> oneFailPermissions;//失败权限组
    boolean oneAuthoriseSuccess;//标识是否授权成功，有一个没授权成功就返回失败

    private void onePermissions(final Context context, final String requestPermission, final String permissionName, final PermissionsResultListener permissionsResultListener) {
        oneAuthoriseSuccess = true;
        RxPermissions rxPermissions = new RxPermissions((Activity) context);
        rxPermissions.requestEach(requestPermission)
                .subscribe(new Action1<Permission>() {
                               @Override
                               public void call(Permission permission) {
                                   if (permission.granted) {
                                       L.e("用户已经同意该权限", "用户已经同意" + permissionName + "该权限");
                                   } else if (permission.shouldShowRequestPermissionRationale) {
                                       // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                       L.e("用户已经拒绝该权限", "用户已经拒绝" + permissionName + "该权限");
                                       ToastUtils.WarnImageToast(context, permissionName + "权限被拒绝，若无相应权限会影响使用");
                                       oneAuthoriseSuccess = false;
                                       oneFailPermissions = new ArrayList<>();
                                       oneFailPermissions.add(requestPermission);
                                   } else {
                                       // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                       ToastUtils.WarnImageToast(context, permissionName + "权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用");
                                       oneAuthoriseSuccess = false;
                                       oneFailPermissions = new ArrayList<>();
                                       oneFailPermissions.add(requestPermission);
                                   }
                                   permissionsResultListener.onResult(oneAuthoriseSuccess, oneFailPermissions);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable t) {
                                L.e("可能是授权异常的情况下的处理", "可能是授权异常的情况下的处理");
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {

                            }
                        }
                );
    }
    /**
     * 是否显示密码
     * @param editText 输入框
     * @param imageView 图形控件
     */
    @Override
    public boolean isShowPassword(boolean isShowPassword, EditText editText, ImageView imageView) {
        if(isShowPassword){
            //不显示
            isShowPassword=false;
            imageView.setImageResource(R.mipmap.common_icon_eye_hint);
            // 显示为密码
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = editText.getText();
            Selection.setSelection(etable, etable.length());
        }else {
            //不显示
            isShowPassword=true;
            imageView.setImageResource(R.mipmap.common_icon_eye_show);
            // 显示为普通文本
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            // 使光标始终在最后位置
            Editable etable = editText.getText();
            Selection.setSelection(etable, etable.length());
        }
        return isShowPassword;
    }
    public void onDestroy() {
        mCommonApplication = null;
        if (dialogBuilder != null) {
            dialogBuilder.dismiss();
        }
        dialogBuilder = null;
        commonProjectUtilInterface = null;
    }
}
