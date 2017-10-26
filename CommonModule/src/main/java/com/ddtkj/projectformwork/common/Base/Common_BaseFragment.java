package com.ddtkj.projectformwork.common.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddtkj.projectformwork.common.MVP.View.Implement.Activity.Common_Act_NetworkError_Implement;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.R;
import com.ddtkj.projectformwork.common.Util.IntentUtil;
import com.utlis.lib.NetworkUtils;
import com.utlis.lib.ToastUtils;

import butterknife.ButterKnife;

public abstract class Common_BaseFragment extends Fragment implements
        OnClickListener {
    //标题栏父布局
    public LinearLayout lyToolbarParent;
    //Toolbar布局
    public Toolbar simpleToolbar;
    //标题栏左边返回控件
    public TextView tvLeftTitle;
    //标题栏居中控件
    public TextView tvMainTitle;
    //标题栏右边图标文字空间
    public TextView tvRightTitleRight;
    public TextView tvRightTitleMiddle;
    public TextView tvRightTitleLeft;
    //分割线
    public View viewLine;

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
            initActionbarView();
            initMyView();
            context = public_view.getContext();
            initApplication();
            mBundle=getArguments();
            intentTool = new IntentUtil();
            ButterKnife.bind(this, public_view);
            setTitleBar();
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
    public void initActionbarView(){
        //标题栏父布局
        lyToolbarParent=(LinearLayout) public_view.findViewById(R.id.lyToolbarParent);
        //Toolbar布局
        simpleToolbar=(Toolbar)public_view. findViewById(R.id.simpleToolbar);
        //标题栏左边返回控件
        tvLeftTitle=(TextView)public_view. findViewById(R.id.tvLeftTitle);
        //标题栏居中控件
        tvMainTitle=(TextView)public_view. findViewById(R.id.tvMainTitle);
        //标题栏右边图标文字空间
        tvRightTitleRight=(TextView) public_view. findViewById(R.id.tvRightTitleRight);
        tvRightTitleMiddle=(TextView) public_view. findViewById(R.id.tvRightTitleMiddle);
        tvRightTitleLeft=(TextView) public_view. findViewById(R.id.tvRightTitleLeft);
        //分割线
        viewLine=(View)public_view. findViewById(R.id.viewLine);
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
     * 设置标题栏
     *
     * @category 设置标题栏
     */
    protected abstract void setTitleBar();
    public void setActionbarBar(String title, int bg, int titleColor, boolean showReturn,boolean showLineView) {
        // 设置Toolbar背景
        simpleToolbar.setBackgroundResource(bg);
        //设置标题
        tvMainTitle.setText(title);
        tvMainTitle.setTextColor(context.getResources().getColor(titleColor));
        tvMainTitle.setOnClickListener(this);
        //显示返回按钮
        if (showReturn)
            tvLeftTitle.setVisibility(View.VISIBLE);
        //显示分割线
        if (showLineView)
            viewLine.setVisibility(View.VISIBLE);
    }

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
     * 设置隐藏Actionbar
     */
    public void setActionbarGone() {
        //设置Actionbar的高度
        lyToolbarParent.setVisibility(View.GONE);
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
