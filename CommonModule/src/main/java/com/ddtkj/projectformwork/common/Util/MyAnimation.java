package com.ddtkj.projectformwork.common.Util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import com.ddtkj.projectformwork.common.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 动画
 *
 * @author Administrator
 */
public class MyAnimation {

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    /**
     * 创建顺时针旋转180度动画
     *
     * @param context
     * @return
     */
    public static Animation rotateClockwiseAnimation(Context context) {
        Animation rotate = AnimationUtils.loadAnimation(context, R.anim.common_rotate_clockwise);//创建顺时针旋转180度动画
        rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转
        rotate.setFillAfter(!rotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转
        return rotate;
    }

    /**
     * 创建逆时针旋转180度动画
     *
     * @param context
     * @return
     */
    public static Animation rotateCounterclockwiseAnimation(Context context) {
        Animation rotate = AnimationUtils.loadAnimation(context, R.anim.common_rotate_counterclockwise);//创建顺时针旋转180度动画
        rotate.setInterpolator(new LinearInterpolator());//设置为线性旋转
        rotate.setFillAfter(!rotate.getFillAfter());//每次都取相反值，使得可以不恢复原位的旋转
        return rotate;
    }
    /**
     * 自定义TranslateAnimation位移动画
     * @param min
     * @param max
     * @return
     */
    public static TranslateAnimation getTranslateAnimation(double min,double max){
        Random random=new Random();// 定义随机类
        float randomResult=(float) (random.nextFloat()*(max)+min);// 返回[min,max+0.1)
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT,1f,
                Animation.RELATIVE_TO_PARENT,-1f,
                Animation.RELATIVE_TO_PARENT,randomResult,
                Animation.RELATIVE_TO_PARENT,randomResult);
        translateAnimation.setDuration(8000);//设置动画持续时间
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        return translateAnimation;
    }

    public static AnimatorSet getAnimatorSet(Context context, View view, float fx, float tx, float fy, float ty ){
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator animator_x = ObjectAnimator.ofFloat(view, "translationX", fx,tx);
        animators.add(animator_x);
        ObjectAnimator animator_y = ObjectAnimator.ofFloat(view, "translationY",fy,ty);
        animators.add(animator_y);

        AnimatorSet btnSexAnimatorSet= new AnimatorSet();
        btnSexAnimatorSet.playTogether(animators);
        btnSexAnimatorSet.setDuration(8000);
        btnSexAnimatorSet.setStartDelay(5000);
        btnSexAnimatorSet.start();
        return btnSexAnimatorSet;
    }

    public static AnimatorSet getAnimatorSet(Context context, View view, float fx, float tx, float fy, float ty , boolean isStartDelay){
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator animator_x = ObjectAnimator.ofFloat(view, "translationX", fx,tx);
        animators.add(animator_x);
        ObjectAnimator animator_y = ObjectAnimator.ofFloat(view, "translationY",fy,ty);
        animators.add(animator_y);

        AnimatorSet btnSexAnimatorSet= new AnimatorSet();
        btnSexAnimatorSet.playTogether(animators);
        btnSexAnimatorSet.setDuration(8000);
        if(isStartDelay){
            btnSexAnimatorSet.setStartDelay(5000);
        }
        btnSexAnimatorSet.start();
        return btnSexAnimatorSet;
    }
}
