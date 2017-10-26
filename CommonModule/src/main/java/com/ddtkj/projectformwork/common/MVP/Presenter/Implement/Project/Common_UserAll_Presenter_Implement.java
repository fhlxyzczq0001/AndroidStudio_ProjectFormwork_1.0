package com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpPath;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_UserInfoBean;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus.Common_OtherLoginSuccess_Eventbus;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Project.Common_UserAll_Presenter_Interface;
import com.utlis.lib.L;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**用户所有信息通用获取接口
 * @author: Administrator 杨重诚
 * @date: 2016/10/12:17:07
 */

public class Common_UserAll_Presenter_Implement implements Common_UserAll_Presenter_Interface {
    Common_Application mCommonApplication;
    Common_Base_HttpRequest_Interface mCommonBaseHttpRequestInterface;//请求网络数据的model实现类
    String TAG="Common_UserAll_Presenter_Implement";
    public Common_UserAll_Presenter_Implement(){
        this.mCommonBaseHttpRequestInterface=new Common_Base_HttpRequest_Implement();
        mCommonApplication= Common_Application.getInstance();
    }

    public interface RefreshUserInfoListener {
        /**
         * 刷新用户信息Listener
         * @param userInfoBean
         */
        public void requestListener(boolean isSucc, Common_UserInfoBean userInfoBean);
    }
    /**
     * 刷新用户信息
     */
    @Override
    public void refreshUserInfo(Context context,final RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog){
        refreshUserInfo(context,getRefreshUserInfo_Params(),refreshUserInfoListener,isLoadingDialog);
    }
    /**
     * 获取刷新用户信息的Params
     * @return
     */
    public Map<String, Object> getRefreshUserInfo_Params() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("operation", "refresh");
        params.put("username", "null");
        params.put("password", "null");
        return params;
    }

    /**
     * 刷新用户信息
     * @param params
     */
    public void refreshUserInfo(Context context,Map<String, Object> params,final RefreshUserInfoListener refreshUserInfoListener, boolean isLoadingDialog) {
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_USER_INFO, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                Common_UserInfoBean infoBean=null;
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        infoBean = JSONObject.parseObject(request_bean.getData().toString(),
                                Common_UserInfoBean.class);
                    }
                    //更新用户信息
                    mCommonApplication.setUserInfoBean(infoBean);
                }
                refreshUserInfoListener.requestListener(isSucc,infoBean);
            }
        },isLoadingDialog, Common_HttpRequestMethod.POST);
    }
    /**
     * 获取第三方登录用户信息
     */
    @Override
    public void refreshOtherLogin(Context context,String content, String loginType, String openId,final RefreshUserInfoListener refreshUserInfoListener,boolean isLoadingDialog){
        refreshOtherLogin(context,getOtherLogin_Params(content),loginType,openId,refreshUserInfoListener,isLoadingDialog);
    }
    /**
     * 获取第三方登录用户信息的Params
     * @return
     */
    public Map<String, Object> getOtherLogin_Params(String content) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("operation", "login");
        params.put("content", content);
        return params;
    }

    /**
     * 获取第三方登录用户信息
     * @param params
     */
    public void refreshOtherLogin(Context context,Map<String, Object> params,final String loginType, final String openId,final RefreshUserInfoListener refreshUserInfoListener,boolean isLoadingDialog) {
        mCommonBaseHttpRequestInterface.requestData(context, Common_HttpPath.URL_API_USER_INFO, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                try{
                    if(request_bean.getData()==null){
                        return;
                    }
                    JSONObject jsonObject=JSONObject.parseObject(request_bean.getData().toString());
                    if (null != jsonObject){
                        String login_status = jsonObject.getString("login_status");
                        Common_UserInfoBean infoBean=null;
                        if (login_status!=null&&login_status.equals("-1") ){
                            //进入绑定手机界面
                            L.e(TAG,"三方登录授权通过，未绑定手机号");
                            //进入绑定手机界面
                            Bundle bundle=new Bundle();
                            bundle.putString("loginType",loginType);
                            bundle.putString("openId",openId);
                            //进入三方账号绑定页面
                            //new IntentUtil().intent_RouterTo(mContext, RouterUrl.userinfo_ThiryUserLogingRouterUrl,bundle);
                        }else {
                            infoBean = JSONObject.parseObject(request_bean.getData().toString(),
                                    Common_UserInfoBean.class);
                            //更新用户信息
                            mCommonApplication.setUserInfoBean(infoBean);
                            //发送其它方式登录成功的通知
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //这里发送粘性事件
                                    EventBus.getDefault().post(new Common_OtherLoginSuccess_Eventbus(true));
                                }
                            }, 300);

                        }
                        refreshUserInfoListener.requestListener(isSucc,infoBean);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },isLoadingDialog,Common_HttpRequestMethod.POST);
    }
}
