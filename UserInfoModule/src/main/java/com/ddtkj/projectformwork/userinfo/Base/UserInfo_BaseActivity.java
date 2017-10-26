package com.ddtkj.projectformwork.userinfo.Base;

import com.ddtkj.projectformwork.common.Base.Common_BaseActivity;
import com.ddtkj.projectformwork.common.Base.Common_BasePresenter;
import com.ddtkj.projectformwork.common.Base.Common_BaseView;
import com.ddtkj.projectformwork.userinfo.Base.Application.UserInfo_Application_Interface;
import com.utlis.lib.TUtil;

public abstract class UserInfo_BaseActivity<T extends Common_BasePresenter,TT extends Common_BasePresenter> extends Common_BaseActivity {
    protected UserInfo_Application_Interface mUserInfoApplicationInterface;
    public T mPresenter;
    @Override
    protected void initApplication() {
        mUserInfoApplicationInterface = UserInfo_Application_Utils.getApplication();
        mPresenter = TUtil.getT(this, 1);
        if (this instanceof Common_BaseView) mPresenter.setVM(this, this);
    }

    @Override
    protected void onMyPause() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.onDestroy();
            mPresenter=null;
        }
        context=null;
    }
}
