package com.ddtkj.projectformwork.MVP.View.Implement.Activity;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.projectformwork.Base.Main_BaseActivity;
import com.ddtkj.projectformwork.MVP.Contract.Activity.Main_MainActivity_Contract;
import com.ddtkj.projectformwork.MVP.Presenter.Implement.Activity.Main_MainActivity_Presenter;
import com.ddtkj.projectformwork.R;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.projectformwork.common.MVP.Presenter.Interface.Project.Common_ProjectUtil_Interface;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;

import java.util.HashMap;
import java.util.Map;

@Route(Common_RouterUrl.mainMainActivityRouterUrl)
public class Main_MainActivity extends Main_BaseActivity<Main_MainActivity_Contract.Presenter,Main_MainActivity_Presenter> implements Main_MainActivity_Contract.View{

    @Override
    protected void initMyView() {

    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_main_layout);
        Common_Base_HttpRequest_Interface common_base_httpRequest_interface=new Common_Base_HttpRequest_Implement();
        Map<String, Object> map = new HashMap<>();
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
    protected void init() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void setTitleBar() {

    }

    @Override
    protected void getData() {

    }
}
