package com.ddtkj.projectformwork.common.MVP.View.Implement.Activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.projectformwork.common.Base.Common_View_BaseActivity;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.R;

/**
 * 网络异常显示界面
 *
 * @author: Administrator 杨重诚
 * @date: 2016/10/25:13:38
 */
@Route(Common_RouterUrl.mainNetworkErrorRouterUrl)
public class Common_Act_NetworkError_Implement extends Common_View_BaseActivity {
    //显示图片
    ImageView mImg;
    //点击重试
    TextView mPromptInformation;

    public static int NO_NETWORK=0;//无网络
    public static int NO_PING=1;//不能访问外网
    public static int NO_REQUEST=2;//无数据

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()== R.id.prompt_information){
            finish();
        }
    }

    @Override
    protected void initMyView() {
        //显示图片
         mImg=(ImageView)findViewById(R.id.img);
        //点击重试
         mPromptInformation=(TextView)findViewById(R.id.prompt_information);
    }

    @Override
    protected void setContentView() {
        contentView= R.layout.common_act_network_error_layout;
        setContentView(contentView);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void setTitleBar() {
        //设置Actionbar
        setActionbarBar("网络异常", R.color.white, R.color.app_text_black, false,true);
    }

    @Override
    protected void getData() {

    }
}
