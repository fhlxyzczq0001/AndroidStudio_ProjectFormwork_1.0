package com.ddtkj.projectformwork.business.MVP.View.Implement.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.projectformwork.business.Base.Business_BaseActivity;
import com.ddtkj.projectformwork.business.MVP.Contract.Activity.Business_MainActivity_Contract;
import com.ddtkj.projectformwork.business.MVP.Presenter.Implement.Activity.Business_MainActivity_Presenter;
import com.ddtkj.projectformwork.business.MVP.Presenter.Implement.Project.Business_ProjectUtil_Implement;
import com.ddtkj.projectformwork.business.MVP.Presenter.Interface.Project.Business_ProjectUtil_Interface;
import com.ddtkj.projectformwork.business.R;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.CustomView.Common_BottomNavigationViewEx;
import com.ddtkj.projectformwork.common.MVP.Presenter.Implement.Project.Common_ProjectUtil_Implement;
import com.ddtkj.projectformwork.common.Public.Common_Main_PublicCode;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.Util.MediaUtils;
import com.utlis.lib.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(Common_RouterUrl.business_MainActivityRouterUrl)
public class Business_MainActivity extends Business_BaseActivity<Business_MainActivity_Contract.Presenter, Business_MainActivity_Presenter> implements Business_MainActivity_Contract.View {
    LinearLayout parentLayout;
    //FragmentManager管理器
    FragmentManager mFragmentManager = null;
    //FragmentTransaction事务
    FragmentTransaction mTransaction = null;
    //当前展示的fragment
    Fragment currentFragment = null;
    //所有的fragment集合
    List<Fragment> mFragmentList;
    int menuIndex = -1;
    Common_BottomNavigationViewEx navigation;
    //存储底部tab对应的id
    int[] tabIds = {R.id.business_title_home, R.id.business_title_project_list,
            R.id.business_title_f_code, R.id.business_title_myinfo};
    //存储底部tab对应的name
    String[] tabNames = {Common_Main_PublicCode.Business_TAB_HOME.toString(), Common_Main_PublicCode.Business_TAB_HELP.toString(),
            Common_Main_PublicCode.Business_TAB_MSG.toString(), Common_Main_PublicCode.Business_TAB_MYINFO.toString()};
    //存储name和id的对应关系
    Map<String, Integer> tabMaps;


    TextView tvPermissions1;
    TextView tvPermissions2;
    Business_ProjectUtil_Interface mBusinessProjectUtilInterface;
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tvPermissions1) {
            //申请权限
            mBusinessProjectUtilInterface.onePermissions(context, new String[]{Manifest.permission.CAMERA}, new String[]{"拍照权限"}, new Common_ProjectUtil_Implement.PermissionsResultListener() {
                @Override
                public void onResult(boolean isSucc, List<String> failPermissions) {
                    if (!isSucc) {
                        for (String str : failPermissions) {
                            L.e("失败权限", str);
                        }
                    }else {
                        MediaUtils.openMedia(context,false);
                    }
                }
            },false);
        }else if(v.getId()==R.id.tvPermissions2) {
            mBusinessProjectUtilInterface.onePermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},
                    new String[]{"定位服务","相机服务","SD卡"}, new Common_ProjectUtil_Implement.PermissionsResultListener() {
                @Override
                public void onResult(boolean isSucc, List<String> failPermissions) {
                    if(!isSucc){
                        for(String str:failPermissions){
                            L.e("失败权限",str);
                        }
                    }
                }
            },true);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.business_act_main_layout);
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
        initTabMaps();
        initFragment();
        menuIndex = 0;
        setNavigationItemClick(menuIndex);
        //初始化P层
        mPresenter.initP();
        mBusinessProjectUtilInterface=new Business_ProjectUtil_Implement();
        //记录主app已经启动
        Common_Application.getInstance().setMainAppIsStart();
    }


    /**
     * 初始化底部tab的map存储对应关系name——id
     */
    private void initTabMaps() {
        tabMaps = new HashMap<>();
        for (int i = 0; i < tabNames.length; i++) {
            tabMaps.put(tabNames[i], tabIds[i]);
        }
    }

    @Override
    protected void initMyView() {
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        navigation = (Common_BottomNavigationViewEx) findViewById(R.id.employears_mainActivity_navigation);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setTextSize(12);
        //        Centure_BottomNavigationViewHelper.disableShiftMode(navigation);
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.app_navigation),
                getResources().getColor(R.color.app_color)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        navigation.setItemTextColor(csl);
        navigation.setItemIconTintList(csl);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

         tvPermissions1= (TextView) findViewById(R.id.tvPermissions1);
         tvPermissions2= (TextView) findViewById(R.id.tvPermissions2);
    }

    @Override
    protected void setListeners() {
        tvPermissions1.setOnClickListener(this);
        tvPermissions2.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    @Override
    protected void setTitleBar() {
        //setActionbarGone();
        setActionbarBar("众包", R.color.white, R.color.app_color, true, true);
        tvLeftTitle.setText("返回");
        tvMainTitle.setText("众包雇主");
        settvRightTitleDrawable(tvRightTitleRight, R.mipmap.common_icon_launcher);
        settvRightTitleDrawable(tvRightTitleMiddle, R.mipmap.common_icon_close_x);
        settvTitleStr(tvRightTitleLeft, "保存", R.color.app_text_normal_color);
        //设置底部导航栏颜色
        setNavigationBarColor(R.color.app_color);
    }

    @Override
    protected void getData() {
    }

    /**
     * 初始化导航数据
     */
    public void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new Fragment());
        mFragmentList.add(new Fragment());
        mFragmentList.add(new Fragment());
        mFragmentList.add(new Fragment());
    }

    /**
     * 需加载的底部导航位置
     *
     * @param menuIndex
     */
    public void loadFragmentIndex(int menuIndex) {
        //初始化FragmentTransaction
        mTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag("" + menuIndex);
        if (currentFragment != null) {
            mTransaction.hide(currentFragment);
        }
        if (fragment != null) {
            mTransaction.attach(fragment);
            mTransaction.show(fragment);
        } else {
            fragment = mFragmentList.get(menuIndex);
            mTransaction.add(R.id.employears_mainActivity_content, fragment, "" + menuIndex);
        }
        currentFragment = fragment;
        mTransaction.commitAllowingStateLoss();
    }

    /**
     * 底部按钮的点击处理事件
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == tabIds[0]) {
                menuIndex = 0;
                setNavigationItemClick(menuIndex);
                return true;
            } else if (item.getItemId() == tabIds[1]) {
                menuIndex = 1;
                setNavigationItemClick(menuIndex);
                getIntentTool().intent_RouterTo(context, Common_RouterUrl.userinfo_UserLogingRouterUrl);
                getIntentTool().intent_RouterTo(context,Common_RouterUrl.userinfo_UserLogingRouterUrl);
                getIntentTool().intent_RouterTo(context,Common_RouterUrl.userinfo_UserLogingRouterUrl);
                return true;
            } else if (item.getItemId() == tabIds[2]) {
                menuIndex = 2;
                setNavigationItemClick(menuIndex);
                return true;
            } else if (item.getItemId() == tabIds[3]) {
                menuIndex = 3;
                setNavigationItemClick(menuIndex);
                return true;
            }
            return false;
        }
    };

    /**
     * 设置navigage点击事件
     *
     * @param menuIndex 当前点击的索引
     */
    public void setNavigationItemClick(int menuIndex) {
        loadFragmentIndex(menuIndex);
    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
        setNavigationTabIndex(mBundle);
    }

    /**
     * 接收再次进入主activity的传参
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        if (intent.getExtras() != null) {
            setNavigationTabIndex(intent.getExtras());
        }
    }

    /**
     * 设置tab被动切换位置
     *
     * @param mBundle
     */
    private void setNavigationTabIndex(Bundle mBundle) {
        if (mBundle != null) {
            if (mBundle.getString("tab") != null) {
                String tabName = mBundle.getString("tab");
                if (tabMaps == null) {
                    for (int i = 0; i < tabNames.length; i++) {
                        if (tabName.contains(tabNames[i])) {
                            menuIndex = i;
                        }
                    }
                } else {
                    navigation.setSelectedItemId(tabMaps.get(tabName));
                }
            }
        }
    }

    @Override
    protected void onMyPause() {

    }
}
