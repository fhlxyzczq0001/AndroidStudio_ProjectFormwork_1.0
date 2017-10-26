package com.ddtkj.projectformwork.common.MVP.Model.Interface;

import android.content.Context;

import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by ${杨重诚} on 2017/10/12.
 */

public interface Common_Base_HttpRequest_Interface {
    /**
     * 请求网络数据接口
     * @param context 上下文
     * @param URL 路径
     * @param params 参数
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载进度
     * @param requestMethod 请求类型
     */
    public void requestData(Context context, String URL, Map<String, Object> params, Common_ResultDataListener resultListener, boolean isLoadingDialog, Common_HttpRequestMethod requestMethod);
    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    public void FileDownloader(String saveFilePath, String uploadFilePath, Common_ResultDataListener resultListener);
    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    public void FileDownloader(String saveFilePath, String uploadFilePath, Common_ResultDataListener resultListener, Common_Base_HttpRequest_Implement.ProgressResultListener onProgressChange);
    /**
     * 上传文件
     * @param url
     * @param requestBody
     * @param resultListener
     * @param isLoadingDialog
     */
    public void uploadFile(String url, RequestBody requestBody, Common_ResultDataListener resultListener, boolean isLoadingDialog);
}
