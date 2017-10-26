package com.ddtkj.projectformwork.common.HttpRequest.ResultListener;


import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;

/**
 * 监听请求服务的接口
 * @author Administrator
 */
public interface Common_ResultDataListener {
	/**
	 * 请求服务器的回调接口方法
	 * @param isSucc 请求的结果
	 * @param msg   返回状态类型  ResultCodeEnum
	 * @param request_bean      返回内容
	 * @return
	 */
	public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean);
}
