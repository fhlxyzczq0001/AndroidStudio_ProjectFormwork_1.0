package com.ddtkj.projectformwork.userinfo.MVP.Interceptor;

import android.content.Context;

import com.chenenyu.router.RouteInterceptor;
import com.chenenyu.router.RouteRequest;
import com.chenenyu.router.annotation.Interceptor;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.Util.IntentUtil;
import com.ddtkj.projectformwork.userinfo.Base.Application.UserInfo_Application_Interface;
import com.ddtkj.projectformwork.userinfo.Base.UserInfo_Application_Utils;

/**
 * 用户信息拦截器
 * Created by ${杨重诚} on 2017/7/28.
 */
@Interceptor(Common_RouterUrl.interceptor_UserInfo_RouterUrl)
public class UserInfo_UserInfoInterceptor implements RouteInterceptor {
    UserInfo_Application_Interface mUserInfoApplicationInterface;
    @Override
    public boolean intercept(Context context, RouteRequest routeRequest) {
        mUserInfoApplicationInterface= UserInfo_Application_Utils.getApplication();
        if(mUserInfoApplicationInterface.getUseInfoVo()==null){
            new IntentUtil().intent_RouterTo(context,Common_RouterUrl.userinfo_UserLogingRouterUrl);
            //拦截
            return true;
        }
        return false;
    }
}
