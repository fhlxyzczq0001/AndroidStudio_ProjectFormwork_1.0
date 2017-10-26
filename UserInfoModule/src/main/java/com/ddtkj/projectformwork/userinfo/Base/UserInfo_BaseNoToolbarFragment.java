package com.ddtkj.projectformwork.userinfo.Base;

import com.andexert.library.RippleView;
import com.ddtkj.projectformwork.common.Base.Common_BaseNoToolbarFragment;
import com.ddtkj.projectformwork.common.Base.Common_BasePresenter;
import com.ddtkj.projectformwork.common.Base.Common_BaseView;
import com.ddtkj.projectformwork.userinfo.Base.Application.UserInfo_Application_Interface;
import com.utlis.lib.TUtil;

/**
 * Created by ${杨重诚} on 2017/6/8.
 */

public abstract class UserInfo_BaseNoToolbarFragment<T extends Common_BasePresenter,TT extends Common_BasePresenter> extends Common_BaseNoToolbarFragment {
    protected UserInfo_Application_Interface mUserInfoApplicationInterface;
    public T mPresenter;
    RippleView acbr_left1_icon_RippleView;
    @Override
    protected void initApplication() {
        mUserInfoApplicationInterface = UserInfo_Application_Utils.getApplication();
        mPresenter = TUtil.getT(this, 1);
        if (this instanceof Common_BaseView) mPresenter.setVM(context, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        mPresenter=null;
        context=null;
    }
}
