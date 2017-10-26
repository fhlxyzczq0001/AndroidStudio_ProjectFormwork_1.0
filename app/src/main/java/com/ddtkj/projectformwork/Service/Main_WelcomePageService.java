package com.ddtkj.projectformwork.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.ddtkj.projectformwork.MVP.Model.Bean.CommonBean.Main_WelcomePageBean;
import com.ddtkj.projectformwork.Util.Main_SharePer_SdCard_Info;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.Public.Common_SD_FilePath;
import com.utlis.lib.FileUtils;

import java.io.File;

/**
 * @ClassName: com.ygworld.Service
 * @author: Administrator 杨重诚
 * @date: 2016/10/14:14:39
 */

public class Main_WelcomePageService extends IntentService {
    Common_Base_HttpRequest_Interface mCommon_base_httpRequest_interface;
    private String uploadPath = "";// 下载文件路径
    Main_WelcomePageBean mWelcomePageBean;// 欢迎页page存储数据对象

    public Main_WelcomePageService() {
        super("com.ddtkj.projectformwork.Service.WelcomePageService");
        mCommon_base_httpRequest_interface =new Common_Base_HttpRequest_Implement();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            mWelcomePageBean=intent.getParcelableExtra("WelcomePageBean");
            sendHander();
        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    // -----------------上传服务器的 Handler-----------------------
    private Handler handler = new Handler() {
        @Override
        // 当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            uploadPath = (String) msg.obj;
            handler.post(runnable);
        }

    };

    // 构建Runnable对象，在runnable中更新界面
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            down(Common_SD_FilePath.welcomePagePath, uploadPath);
        }

    };

    /**
     * 下载文件
     * @Title: down
     * @Description: TODO
     * @param saveFilePath
     * @param uploadFilePath
     * @return: void
     */
    private void down(String saveFilePath, String uploadFilePath) {
        if (!new File(saveFilePath).exists()) {
            try {
                FileUtils.createSDCardDir(saveFilePath);// 创建文件夹
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
        } else {
                FileUtils.deleteFile(saveFilePath);// 删除文件夹里面的文件
                Main_SharePer_SdCard_Info.sharePre_PutWelcomePageBean(null);//初始化本地缓存
        }

        String[] str = uploadFilePath.split("/");
        String filePath = saveFilePath + "/"
                + str[str.length - 1];
        mCommon_base_httpRequest_interface.FileDownloader(filePath, uploadFilePath, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                if(isSucc){
                    //存放启动页bean对象
                    Main_SharePer_SdCard_Info.sharePre_PutWelcomePageBean(mWelcomePageBean);
                }
                stopSelf();
            }
        });
    }

    /**
     * 发送Hander
     *
     * @Title: sendHander
     * @Description: TODO
     * @return: void
     */
    private void sendHander() {
        uploadPath = mWelcomePageBean.getUrl();
        Message message = Message.obtain();
        message.obj = uploadPath;
        handler.sendMessage(message);
    }
}
