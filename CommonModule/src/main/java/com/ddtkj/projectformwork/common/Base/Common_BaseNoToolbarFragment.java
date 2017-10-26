package com.ddtkj.projectformwork.common.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ddtkj.projectformwork.common.MVP.View.Implement.Activity.Common_Act_NetworkError_Implement;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.R;
import com.ddtkj.projectformwork.common.Util.IntentUtil;
import com.utlis.lib.NetworkUtils;
import com.utlis.lib.ToastUtils;

import butterknife.ButterKnife;

public abstract class Common_BaseNoToolbarFragment extends Fragment implements
        OnClickListener {
    protected View public_view;
    protected IntentUtil intentTool;
    protected Context context;
    protected int contentView=-555;
    protected boolean isShowSystemBarTintManager=true;//是否显示沉浸式状态栏
    protected int systemBarTintManagerColor= R.color.black;//沉浸式状态栏背景色
    protected LayoutInflater inflater;
    protected Bundle mBundle;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v.getId()==R.id.tvMainTitle){
            FinishA();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (public_view == null) {
            this.inflater=inflater;
            public_view = setContentView();
            initMyView();
            context = public_view.getContext();
            initApplication();
            mBundle=getArguments();
            intentTool = new IntentUtil();
            ButterKnife.bind(this, public_view);
            init();
            setListeners();
        }

        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) public_view.getParent();
        if (parent != null) {
            parent.removeView(public_view);
        }
        if(contentView!= R.layout.common_act_network_error_layout){
            checkNetwork();//检查网络是否正常
        }
        return public_view;
    }
    protected abstract void initMyView();
    protected abstract void initApplication();

    protected abstract View setContentView();

    /**
     * @category 初始化网络组件和数据组件以及LogUtil所需的clazz参数，设置布局
     *
     */
    protected abstract void init();

    /**
     * @category 初始化Android组件并实现组件并可以对组件赋值
     * @TODO 初始化Android组件并实现组件并可以对组件赋值
     */
    // protected abstract void findViews();

    /**
     * @category 设置监听
     *
     */
    protected abstract void setListeners();

    /**
     * @category 网络数据
     *
     */
    //protected abstract void getDataList(HashMap<String, Object> paramList);

    public void onResume() {
        super.onResume();
        // --------------------------设置沉浸式状态栏----------------------------------
        //WindowUtils.setSystemBarTintManager(context, acbr_Parent_Layout, getResources().getColor(systemBarTintManagerColor));
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public IntentUtil getIntentTool() {
        return intentTool;
    }
    /**
     * 检查网络是否正常
     */
    private void checkNetwork() {
        NetworkUtils.NetworkStatus(context, new NetworkUtils.NetworkListener() {
            @Override
            public void onResult(boolean Status, String msg, NetworkUtils.NoNetworkType noNetworkType) {
                if (!Status) {
                    ToastUtils.ErrorImageToast(context, msg);
                    //跳转到网络异常页面
                    Bundle bundle=new Bundle();
                    if(noNetworkType.equals(NetworkUtils.NoNetworkType.NO_NETWORK)){
                        //无网络
                        bundle.putInt("errorType", Common_Act_NetworkError_Implement.NO_NETWORK);
                    }else if(noNetworkType.equals(NetworkUtils.NoNetworkType.NO_PING)){
                        //不能访问外网
                        bundle.putInt("errorType",Common_Act_NetworkError_Implement.NO_PING);
                    }
                    getIntentTool().intent__no_animation_RouterTo(context, Common_RouterUrl.mainNetworkErrorRouterUrl,bundle);
                }
            }
        });
    }
    /**
     * finish当前Activity
     */
    public void FinishA() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.common_slide_in_left,
                R.anim.common_slide_out_right);
    }
}
