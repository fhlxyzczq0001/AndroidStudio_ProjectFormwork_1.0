package com.ddtkj.projectformwork.business.Base;


import com.ddtkj.projectformwork.business.Base.Application.Business_Application_Interface;
import com.ddtkj.projectformwork.common.BuildConfig;

/**
 * Created by Administrator on 2017/7/7.
 * 获取当前的application对象
 */

public class Business_Application_Utils {
    static Business_Application_Interface application_interface;

    public static Business_Application_Interface getApplication(){
        if(BuildConfig.IsBuildMudle){
            application_interface = com.ddtkj.projectformwork.business.Base.Application.debug.Business_Application.getInstance();
        }else {
            application_interface = com.ddtkj.projectformwork.business.Base.Application.release.Business_Application.getInstance();
        }
        return application_interface;
    }
}
