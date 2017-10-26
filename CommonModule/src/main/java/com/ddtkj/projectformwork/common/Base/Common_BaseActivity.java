package com.ddtkj.projectformwork.common.Base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ddtkj.projectformwork.common.HttpRequest.Common_CustomRequestResponseBeanDataManager;
import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.projectformwork.common.MVP.View.Implement.Activity.Common_Act_NetworkError_Implement;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.R;
import com.ddtkj.projectformwork.common.Util.IntentUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.umeng.socialize.UMShareAPI;
import com.utlis.lib.NetworkUtils;
import com.utlis.lib.ToastUtils;
import com.utlis.lib.WindowUtils;

import butterknife.ButterKnife;

public abstract class Common_BaseActivity extends RxAppCompatActivity implements
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

    protected Bundle mBundle;
    protected IntentUtil intentTool;
    protected Context context;
    protected int contentView = -555;
    protected boolean isShowSystemBarTintManager = true;//是否显示沉浸式状态栏
    protected int systemBarTintManagerColor = R.color.app_color;//沉浸式状态栏背景色

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.tvLeftTitle) {
            FinishA();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApplication();
        context = this;//获取上下文
        intentTool = new IntentUtil();//获取intentTool
        mBundle = getIntent().getExtras();
        getBundleValues(mBundle);//获取页面传值
        setContentView();//设置布局
        ButterKnife.bind(this);//初始化ButterKnife
        initActionbarView();
        initMyView();
        setTitleBar();//设置title
        init();//初始化数据
        setListeners();//设置监听事件
        getData();
        if (contentView != R.layout.common_act_network_error_layout) {
            checkNetwork();//检查网络是否正常
        }
    }

    public void initActionbarView() {
        //标题栏父布局
        lyToolbarParent = (LinearLayout) findViewById(R.id.lyToolbarParent);
        //Toolbar布局
        simpleToolbar = (Toolbar) findViewById(R.id.simpleToolbar);
        //标题栏左边返回控件
        tvLeftTitle = (TextView) findViewById(R.id.tvLeftTitle);
        //标题栏居中控件
        tvMainTitle = (TextView) findViewById(R.id.tvMainTitle);
        //标题栏右边图标文字空间
        tvRightTitleRight = (TextView) findViewById(R.id.tvRightTitleRight);
        tvRightTitleMiddle = (TextView) findViewById(R.id.tvRightTitleMiddle);
        tvRightTitleLeft = (TextView) findViewById(R.id.tvRightTitleLeft);
        //分割线
        viewLine = (View) findViewById(R.id.viewLine);
    }

    protected abstract void initMyView();

    protected abstract void initApplication();

    protected abstract void setContentView();

    /**
     * @category 初始化网络组件和数据组件以及LogUtil所需的clazz参数，设置布局
     */
    protected abstract void init();

    /**
     * @category 初始化Android组件并实现组件并可以对组件赋值
     * @TODO 初始化Android组件并实现组件并可以对组件赋值
     */
    // protected abstract void findViews();

    /**
     * @category 设置监听
     */
    protected abstract void setListeners();

    /**
     * 获取页面传值
     *
     * @param mBundle
     */
    public void getBundleValues(Bundle mBundle) {
    }

    ;

    /**
     * 设置标题栏
     *
     * @category 设置标题栏
     */
    protected abstract void setTitleBar();

    public void setActionbarBar(String title, int bg, int titleColor, boolean showReturn, boolean showLineView) {
        // 设置Toolbar背景
        simpleToolbar.setBackgroundResource(bg);
        //设置标题
        tvMainTitle.setText(title);
        tvMainTitle.setTextColor(context.getResources().getColor(titleColor));
        //显示返回按钮
        if (showReturn)
            tvLeftTitle.setVisibility(View.VISIBLE);
        tvLeftTitle.setOnClickListener(this);
        //显示分割线
        if (showLineView)
            viewLine.setVisibility(View.VISIBLE);
    }

    //设置title右边图标
    public void settvRightTitleDrawable(TextView tv, int res) {
        Drawable dwRight = ContextCompat.getDrawable(context, res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        tv.setCompoundDrawables(null, null, dwRight, null);
        tv.setVisibility(View.VISIBLE);
    }

    //设置title右边文字
    public void settvTitleStr(TextView tv, String str, int color) {
        tv.setText(str);
        tv.setTextColor(getResources().getColor(color));
        tv.setVisibility(View.VISIBLE);
    }

    /**
     * 设置底部导航栏颜色
     *
     * @param res
     */
    public void setNavigationBarColor(int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(res));
        }
    }

    /**
     * @category 网络数据
     */
    //protected abstract void getDataList(HashMap<String, Object> paramList);
    protected abstract void getData();

    public void onResume() {
        super.onResume();
        // --------------------------设置沉浸式状态栏----------------------------------
        if (isShowSystemBarTintManager) {
            WindowUtils.setSystemBarTintManager(context, getResources().getColor(systemBarTintManagerColor));
        }
    }

    protected abstract void onMyPause();

    protected void onPause() {
        super.onPause();
        onMyPause();
    }

    public IntentUtil getIntentTool() {
        return intentTool;
    }

    /**
     * 返回键的监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            // 退出
            finish();
            overridePendingTransition(R.anim.common_slide_in_left,
                    R.anim.common_slide_out_right);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    long lastClickTime = System.currentTimeMillis();

    public boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 500) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //UMShareAPI.get(this).release();//在使用分享或者授权的Activity中，重写onDestory()方法：内存泄漏
        if(null!= Common_CustomRequestResponseBeanDataManager.commonCustomRequestResponseBeanDataManager)
        Common_CustomRequestResponseBeanDataManager.commonCustomRequestResponseBeanDataManager.onDestroy();
        if(null!= Common_ProjectUtil_Implement.commonProjectUtilInterface)
        Common_ProjectUtil_Implement.commonProjectUtilInterface.onDestroy();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * finish当前Activity
     */
    public void FinishA() {
        finish();
        overridePendingTransition(R.anim.common_slide_in_left,
                R.anim.common_slide_out_right);
    }

    /**
     * 设置隐藏Actionbar
     */
    public void setActionbarGone() {
        //设置Actionbar的高度
        lyToolbarParent.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        try {
            UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    Bundle bundle = new Bundle();
                    if (noNetworkType.equals(NetworkUtils.NoNetworkType.NO_NETWORK)) {
                        //无网络
                        bundle.putInt("errorType", Common_Act_NetworkError_Implement.NO_NETWORK);
                    } else if (noNetworkType.equals(NetworkUtils.NoNetworkType.NO_PING)) {
                        //不能访问外网
                        bundle.putInt("errorType", Common_Act_NetworkError_Implement.NO_PING);
                    }
                    getIntentTool().intent__no_animation_RouterTo(context, Common_RouterUrl.mainNetworkErrorRouterUrl, bundle);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            UMShareAPI.get(this).onSaveInstanceState(outState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
