package com.ddtkj.projectformwork.MVP.Presenter.Implement.Activity;


import android.Manifest;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.projectformwork.Base.My_Application;
import com.ddtkj.projectformwork.MVP.Contract.Activity.Main_WelcomePage_Contract;
import com.ddtkj.projectformwork.MVP.Model.Bean.CommonBean.Main_WelcomePageBean;
import com.ddtkj.projectformwork.MVP.Presenter.Implement.Project.Main_ProjectUtil_Implement;
import com.ddtkj.projectformwork.MVP.Presenter.Interface.Project.Main_ProjectUtil_Interface;
import com.ddtkj.projectformwork.Util.Main_SharePer_SdCard_Info;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpPath;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.utlis.lib.L;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_WelcomePage_Presenter extends Main_WelcomePage_Contract.Presenter {
    Common_Base_HttpRequest_Interface mCommon_base_httpRequest_interface;
    My_Application mMy_application;
    Main_ProjectUtil_Interface mainProjectUtilInterface;
    public Main_WelcomePage_Presenter(){
        mCommon_base_httpRequest_interface=new Common_Base_HttpRequest_Implement();
        mMy_application=My_Application.getInstance();
        mainProjectUtilInterface=new Main_ProjectUtil_Implement();
    }
    /**
     * 请求启动页更新
     */
    @Override
    public void requestStartPageUpdate(){
        requestStartPageUpdate(getStartPageUpdate_Params());
    }
    /**
     * 获取启动页更新的Params
     * @return
     */
    public Map<String, Object> getStartPageUpdate_Params() {
        Map<String, Object> params = new HashMap<String, Object>();
        return params;
    }
    /**
     * 请求启动页更新
     * @param params
     */
    public void requestStartPageUpdate(Map<String, Object> params) {
        mCommon_base_httpRequest_interface.requestData(context, Common_HttpPath.URL_API_STARTPAGEUPDATE, params, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                Main_WelcomePageBean welcomePageBean=null;
                if (isSucc) {
                    if(request_bean.getData()!=null){
                        welcomePageBean = JSONObject.parseObject(request_bean.getData().toString(),
                                Main_WelcomePageBean.class);
                        //获取本地存储启动页缓存
                        Main_WelcomePageBean share_PageBean= Main_SharePer_SdCard_Info.sharePre_GetWelcomePageBean();
                        if(null!=share_PageBean){
                            // 获取到的图片路径
                            String start_page_url =share_PageBean.getUrl();
                            // 取得下载路径的文件名和本地图片名称比对，若本地路径中有不下载，没有则执行下载
                            String webFileName =welcomePageBean.getUrl().substring(start_page_url
                                    .lastIndexOf("/") + 1);
                            //获取本地文件名
                            String localFileName = start_page_url.substring(start_page_url
                                    .lastIndexOf("/") + 1);
                            if (!webFileName.equals(localFileName)) {
                                //启动service下载图片
                                startWelcomePageService(welcomePageBean);
                            }
                        }else{
                            //启动service下载图片
                            startWelcomePageService(welcomePageBean);
                        }
                    }
                }
            }
        },false, Common_HttpRequestMethod.POST);
    }

    /**
     * 启动下载服务
     * @param welcomePageBean
     */
    private void startWelcomePageService(final Main_WelcomePageBean welcomePageBean){
        mainProjectUtilInterface.onePermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new String[]{"SD卡"}, new Common_ProjectUtil_Implement.PermissionsResultListener() {
            @Override
            public void onResult(boolean isSucc, List<String> failPermissions) {
                if (!isSucc) {
                    for (String str : failPermissions) {
                        L.e("失败权限", str);
                    }
                }else {
                    //启动service下载图片
                    mView.startWelcomePageService(welcomePageBean);
                }
            }
        },false);
    }
    @Override
    public void onDestroy() {
super.onDestroy();
    }
}
