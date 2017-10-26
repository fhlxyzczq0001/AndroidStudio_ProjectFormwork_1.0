package com.ddtkj.projectformwork.Base;


import com.ddtkj.projectformwork.common.Base.Common_BaseActivity;
import com.ddtkj.projectformwork.common.Base.Common_BasePresenter;
import com.ddtkj.projectformwork.common.Base.Common_BaseView;
import com.utlis.lib.TUtil;

public abstract class Main_BaseActivity<T extends Common_BasePresenter,TT extends Common_BasePresenter> extends Common_BaseActivity {
    protected My_Application myApplication;
    public T mPresenter;
    @Override
    protected void initApplication() {
        myApplication = My_Application.getInstance();//获取Application
        mPresenter = TUtil.getT(this, 1);
        if (this instanceof Common_BaseView) mPresenter.setVM(this, this);
    }

    @Override
    protected void onMyPause() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //JPushInterface.onResume(this);
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
