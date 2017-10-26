package com.ddtkj.projectformwork.common.Public;

import android.os.Environment;

import com.ddtkj.projectformwork.common.Base.Common_Application;

/**
 * SD卡文件存储路径
 * @ClassName: SD_FilePath 
 * @Description: TODO
 * @author: yzc
 * @date: 2016-4-24 上午10:40:50
 */
public class Common_SD_FilePath {
    public static String sdcardDir = Environment.getExternalStorageDirectory()
            .getPath();// SD卡根目录绝对路径
    public static String appSdcardDir = Environment.getExternalStorageDirectory()
            .getPath()+"/"+ Common_Application.getApplication().getPackageName();// 应用SD卡根目录绝对路径
    public static String welcomePagePath = appSdcardDir+"/welcomePagePath";// 启动页保存路径
    public static String apkPath = appSdcardDir+"/apkPath";// apk下载保存路径
}
