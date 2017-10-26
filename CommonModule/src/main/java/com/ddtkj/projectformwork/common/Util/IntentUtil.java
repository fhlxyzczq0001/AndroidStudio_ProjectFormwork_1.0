package com.ddtkj.projectformwork.common.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chenenyu.router.Router;
import com.ddtkj.projectformwork.common.R;

public class IntentUtil {

	int defaultStartAnim= R.anim.common_slide_in_right;
	int defaultEndAnim=R.anim.common_slide_out_left;
//==================================路由机制=================================================
public void intent_RouterTo(Context context, String classUrl) {
	Router.build(classUrl).anim(defaultStartAnim,
			defaultEndAnim).go(context);
}
	public void intent_RouterTo(Context context, String classUrl,int flag) {
		Router.build(classUrl).requestCode(flag).anim(defaultStartAnim,
				defaultEndAnim).go(context);
	}

	public void intent_RouterTo(Context context,String classUrl, int startAnim,
						  int endAnim) {
		Router.build(classUrl).anim(startAnim,
				endAnim).go(context);
	}

	public void intent_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).with(bundle).anim(defaultStartAnim,
				defaultEndAnim).go(context);
	}

	public void intent_RouterTo(Context context, String classUrl, Bundle bundle,int flag) {

		Router.build(classUrl).requestCode(flag).with(bundle).anim(defaultStartAnim,
				defaultEndAnim).go(context);
	}

	public void intent_newTask_RouterTo(Context context, String classUrl) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).go(context);
	}

	public void intent_newTask_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).with(bundle).go(context);
	}
	public void intent_newTaskClearTop_RouterTo(Context context, String classUrl) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).anim(defaultStartAnim,defaultEndAnim).go(context);
	}
	/**
	 * 没动画的跳转，无缝对接
	 * @param context
	 */
	public void intent_no_animation_RouterTo(Context context, String classUrl) {
		Router.build(classUrl).anim(0,0).go(context);
	}
	/**
	 * 没动画的跳转，无缝对接
	 * @param context
	 */
	public void intent__no_animation_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).with(bundle).anim(0,0).go(context);
	}

	/**
	 * 跳转activity并销毁其它activity的方法
	 * @param context
	 */
	public void intent_destruction_other_activity_RouterTo(Context context, String classUrl) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).anim(defaultStartAnim,defaultEndAnim).go(context);
	}

	public void intent_destruction_other_activity_RouterTo(Context context, String classUrl,int flag) {

		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).requestCode(flag).anim(defaultStartAnim,defaultEndAnim).go(context);
	}
	/**
	 * 跳转activity并销毁其它activity的方法
	 * @param context
	 */
	public void intent_destruction_other_activity_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).with(bundle).anim(defaultStartAnim,defaultEndAnim).go(context);
	}
	public void intent_destruction_other_activity_RouterTo(Context context, String classUrl, Bundle bundle,int flag) {
		Router.build(classUrl).requestCode(flag).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).with(bundle).anim(defaultStartAnim,defaultEndAnim).go(context);
	}

	/**
	 * 跳转activity并销毁其它activity的方法，没动画的
	 * @param context
	 */
	public void intent_destruction_other_activity_no_animation_RouterTo(Context context,  String classUrl) {

		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).anim(0,0).go(context);
	}

	/**
	 * 跳转activity并销毁其它activity的方法，没动画的
	 * @param context
	 */
	public void intent_destruction_other_activity_no_animation_RouterTo(Context context, String classUrl, Bundle bundle) {
		Router.build(classUrl).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).
				addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).with(bundle).anim(0,0).go(context);
	}
}
