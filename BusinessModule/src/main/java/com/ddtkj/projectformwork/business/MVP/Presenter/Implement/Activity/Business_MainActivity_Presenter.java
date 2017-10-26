package com.ddtkj.projectformwork.business.MVP.Presenter.Implement.Activity;


import com.ddtkj.projectformwork.business.Base.Application.Business_Application_Interface;
import com.ddtkj.projectformwork.business.Base.Business_Application_Utils;
import com.ddtkj.projectformwork.business.MVP.Contract.Activity.Business_MainActivity_Contract;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;

/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Business_MainActivity_Presenter extends Business_MainActivity_Contract.Presenter {
    Common_Base_HttpRequest_Interface common_base_httpRequest_interface;
    Business_Application_Interface mBusinessApplicationInterface;
    public Business_MainActivity_Presenter(){
        common_base_httpRequest_interface=new Common_Base_HttpRequest_Implement();
        mBusinessApplicationInterface= Business_Application_Utils.getApplication();
    }
    /**
     * 初始化P层
     */
    @Override
    public void initP() {
        /**
         * get请求
         */
        common_base_httpRequest_interface.requestData(context, "http://jsontest.dadetongkeji.net.cn/82/api/member/cardList.do", null, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {

            }
        },true, Common_HttpRequestMethod.GET);
        Common_ProjectUtil_Interface common_projectUtil_interface=new Common_ProjectUtil_Implement();
        common_projectUtil_interface.checkAppVersion(context, "main", new Common_ProjectUtil_Implement.ResultCheckAppListener() {
            @Override
            public void onResult(boolean isUpdata) {

            }
        });
    }
    @Override
    public void onDestroy() {

    }
}
