package com.ddtkj.projectformwork.business.Base.Application;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_UserInfoBean;
import com.utlis.lib.SharePre;

/**
 * Created by Administrator on 2017/7/7.
 */

public interface Business_Application_Interface {

    /**
     * 获取application对象
     * @return
     */
    Application getApplication();

    /**
     * 获取sharePre对象
     * @return
     */
    SharePre getUserSharedPreferences();

    /**
     * 获取用户信息
     * @return
     */
    Common_UserInfoBean getUseInfoVo();

    /**
     * 设置用户信息
     * @param useInfoVo
     */
    void setUseInfoVo(Common_UserInfoBean useInfoVo);

    /**
     * 加载module的配置信息
     */
    void requestProfile(Context mContext);
    /**
     * 设置HttpDnsService
     */
    public HttpDnsService getHttpDnsService();
}
