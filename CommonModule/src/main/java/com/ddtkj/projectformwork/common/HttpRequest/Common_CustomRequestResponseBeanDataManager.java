package com.ddtkj.projectformwork.common.HttpRequest;

import android.app.Dialog;
import android.content.Context;
import android.webkit.CookieManager;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.HttpRequest.HttpManager.Common_HttpRequestManager;
import com.ddtkj.projectformwork.common.HttpRequest.RequestBody.Common_RequestBodyApi;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.projectformwork.common.Public.Common_PublicMsg;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.R;
import com.ddtkj.projectformwork.common.Util.IntentUtil;
import com.ddtkj.projectformwork.common.Util.LoadingDialog;
import com.utlis.lib.AppUtils;
import com.utlis.lib.L;
import com.utlis.lib.ToastUtils;
import com.utlis.lib.ToolsUtils;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.Map;

/**
 * Rxjava+okhttp请求回调类
 * Created by Administrator on 2017/2/24.
 */

public class Common_CustomRequestResponseBeanDataManager {
    private Common_Application mCommonApplication;
    Common_ProjectUtil_Interface mCommon_projectUtil_interface;
    Dialog dialog;

    public Common_CustomRequestResponseBeanDataManager() {
        mCommonApplication = Common_Application.getInstance();
        if (mCommon_projectUtil_interface == null) {
            mCommon_projectUtil_interface =  Common_ProjectUtil_Implement.getInstance();
        }
    }

    public static Common_CustomRequestResponseBeanDataManager commonCustomRequestResponseBeanDataManager;

    public static Common_CustomRequestResponseBeanDataManager getInstance() {
        if (commonCustomRequestResponseBeanDataManager == null) {
            //第一次check，避免不必要的同步
            synchronized (Common_CustomRequestResponseBeanDataManager.class) {//同步
                if (commonCustomRequestResponseBeanDataManager == null) {
                    //第二次check，保证线程安全
                    commonCustomRequestResponseBeanDataManager = new Common_CustomRequestResponseBeanDataManager();
                }
            }
        }
        return commonCustomRequestResponseBeanDataManager;
    }

    /**
     * pos请求
     *
     * @param context
     * @param url
     * @param mParams
     * @param resultListener
     * @param isLoadingDialog
     */
    public void requestPost(final Context context, final String url, Map<String, Object> mParams, final Common_ResultDataListener resultListener, final boolean isLoadingDialog) {
        long time = System.currentTimeMillis() / 1000;
        mParams.put(Common_PublicMsg.Post_PARAM_TS, String.valueOf(time));
        mParams.put(Common_PublicMsg.Post_PARAM_SIGNA, ToolsUtils.encryption(ToolsUtils.encryption(String.valueOf(Common_PublicMsg.Post_APPSECRET_ANDROID + time)) + Common_PublicMsg.Post_APPKEY_ANDROID).toUpperCase());
        mParams.put(Common_PublicMsg.Post_PARAM_APPKEY, Common_PublicMsg.Post_APPKEY_ANDROID);
        mParams.put(Common_PublicMsg.Post_APP_VERSION, AppUtils.getAppVersionName(context));

        Common_HttpRequestManager httpRequestManager = new Common_HttpRequestManager(new HttpOnNextListener() {
            @Override
            public void onNext(String resulte, String mothead) {
                JSONObject jsonObject = JSONObject.parseObject(resulte);
                int resCode = jsonObject.getIntValue("res_code");
                String res_Msg = jsonObject.getString("res_msg");
                boolean isSucc = false;
                switch (resCode) {
                    case 1:
                        //请求成功
                        isSucc = true;
                        break;
                    case -1:
                        //登录超时,需要重新登陆
                        //清理当前存放的用户信息
                        Common_Application.getInstance().setUserInfoBean(null);
                        Common_Application.getInstance().getUserSharedPreferences().clear();
                        CookieManager.getInstance().removeAllCookie();
                        break;
                    case 2:
                        if (!"WelcomePage_View_Implement".equals(context.getClass().getSimpleName())) {
                            String updateMessage = jsonObject.getString("message");
                            String updateUrl = jsonObject.getString("url");
                            mCommon_projectUtil_interface.showAppUpdateDialog_Forced(context, updateMessage, updateUrl);
                        } else {
                            //版本不符，需强制更新
                            ToastUtils.WarnImageToast(context, res_Msg);
                        }
                        break;
                    case 3:
                        //服务器连接失败，请求维护页面
                        if (mCommon_projectUtil_interface == null) {
                            mCommon_projectUtil_interface = new Common_ProjectUtil_Implement();
                        }
                        mCommon_projectUtil_interface.setServerState(context, res_Msg);
                        //ToastUtil.WarnImageToast(context,res_Msg);
                        break;
                    case 4:
                        //普通更新
                        if (!"WelcomePage_View_Implement".equals(context.getClass().getSimpleName())) {
                            String updateMessage = jsonObject.getString("message");
                            String updateUrl = jsonObject.getString("url");
                            mCommon_projectUtil_interface.showAppUpdateDialog_Common(context, updateMessage, updateUrl);
                        } else {
                            //版本不符，需强制更新
                            ToastUtils.WarnImageToast(context, res_Msg);
                        }
                        break;
                    case 0:
                        //服务器返回失败
                        ToastUtils.WarnImageToast(context, res_Msg);
                        break;
                    case 10011:
                        //登录超时,需要重新登陆
                        ToastUtils.WarnImageToast(context, res_Msg);
                        //清理当前存放的用户信息
                        Common_Application.getInstance().setUserInfoBean(null);
                        Common_Application.getInstance().getUserSharedPreferences().clear();
                        CookieManager.getInstance().removeAllCookie();
                        break;
                    default:
                        // 操作失败 或 错误码未知
                        ToastUtils.WarnImageToast(context, res_Msg);
                        break;
                }
                Common_RequestBean request_Bean = JSONObject.parseObject(resulte, Common_RequestBean.class);
                resultListener.onResult(isSucc, request_Bean.getRes_msg(), request_Bean);
                dialogDismiss(dialog);
                if (resCode == 10011) {
                    new IntentUtil().intent_RouterTo(context, Common_RouterUrl.userinfo_UserLogingRouterUrl);
                }
            }

            @Override
            public void onError(ApiException e) {
                e.printStackTrace();
                L.e("请求失败", "请求失败" + e.hashCode());
                resultListener.onResult(false, context.getResources().getString(R.string.ERROR_MESSAGE), null);
                //请求维护页面
                if (mCommon_projectUtil_interface == null) {
                    mCommon_projectUtil_interface = new Common_ProjectUtil_Implement();
                }
                mCommon_projectUtil_interface.setServerState(context, context.getResources().getString(R.string.ERROR_MESSAGE));
                dialogDismiss(dialog);
            }
        }, context);
        //存储ip与域名的关系
        String key = url.substring(0, ToolsUtils.getCharacterPosition(url, "/", 4));
        Common_Application.httpdnsHosMap.put(key, Common_HttpPath.HOSTS);
        //====================显示进度加载=============================
        try {
            dialog = (Dialog) LoadingDialog.initProgressDialog(context).getDialog();
            if (isLoadingDialog) {
                dialog.show();
            }
        } catch (Exception e) {

        }

        Common_RequestBodyApi requestBodyApi = new Common_RequestBodyApi(url, "POST");
        requestBodyApi.setParams(mParams);
        requestBodyApi.setShowProgress(false);
        requestBodyApi.setDialog(dialog);
        httpRequestManager.doHttpDeal(requestBodyApi);
    }

    /**
     * get请求
     *
     * @param context
     * @param url
     * @param resultListener
     * @param isLoadingDialog
     */
    public void requestGet(final Context context, String url, Map<String, Object> mParams, final Common_ResultDataListener resultListener, final boolean isLoadingDialog) {
        Common_HttpRequestManager httpRequestManager = new Common_HttpRequestManager(new HttpOnNextListener() {
            @Override
            public void onNext(String resulte, String mothead) {
                Common_RequestBean request_Bean = JSONObject.parseObject(resulte, Common_RequestBean.class);
                resultListener.onResult(true, request_Bean.getRes_msg(), request_Bean);
                dialogDismiss(dialog);
            }

            @Override
            public void onError(ApiException e) {
                resultListener.onResult(false, context.getResources().getString(R.string.ERROR_MESSAGE), null);
                dialogDismiss(dialog);
            }
        }, context);
        //====================显示进度加载=============================
        try {
            dialog = (Dialog) LoadingDialog.initProgressDialog(context).getDialog();
            if (isLoadingDialog) {
                dialog.show();
            }
        } catch (Exception e) {

        }

        Common_RequestBodyApi requestBodyApi = new Common_RequestBodyApi(url, "GET");
        requestBodyApi.setParams(mParams);
        requestBodyApi.setShowProgress(false);
        requestBodyApi.setDialog(LoadingDialog.initProgressDialog(context).getDialog());
        httpRequestManager.doHttpDeal(requestBodyApi);
    }

    private void dialogDismiss(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void onDestroy() {
        mCommonApplication = null;
        mCommon_projectUtil_interface = null;
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = null;
        commonCustomRequestResponseBeanDataManager = null;
    }
}