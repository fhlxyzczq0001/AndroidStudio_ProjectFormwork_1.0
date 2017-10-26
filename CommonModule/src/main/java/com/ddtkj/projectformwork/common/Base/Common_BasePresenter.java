package com.ddtkj.projectformwork.common.Base;

import android.content.Context;


/**
 * T-MVP Presenter基类
 * Created by baixiaokang on 16/4/22.
 */
public abstract class Common_BasePresenter<T> {
    public Context context;
    public T mView;

    public void setVM(Context context,T v) {
        this.mView = v;
        this.context=context;
        this.onStart();
    }

    public abstract void onStart();

    public  void onDestroy(){
        mView=null;
        context=null;
    }
}
