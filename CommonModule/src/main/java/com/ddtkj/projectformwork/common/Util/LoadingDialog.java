package com.ddtkj.projectformwork.common.Util;

import android.content.Context;

import com.mingle.widget.ShapeLoadingDialog2;

/**
 * 进度对话框
 * @ClassName: ShapeLoadingDialog
 * @author: Administrator杨重诚
 * @date: 2016/6/6 15:43
 */
public class LoadingDialog {
    //进度对话框
    public static ShapeLoadingDialog2 shapeLoadingDialog=null;

    //显示进度对话框

    public static void showProgressDialog(Context context){
        if(null != shapeLoadingDialog){
            shapeLoadingDialog.dismiss();
            shapeLoadingDialog = null;
        }
        shapeLoadingDialog = new ShapeLoadingDialog2(context);
        shapeLoadingDialog.setCanceledOnTouchOutside(true);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.show();
    }

    public static ShapeLoadingDialog2 initProgressDialog(Context context){
        if(null != shapeLoadingDialog){
            shapeLoadingDialog.dismiss();
            shapeLoadingDialog = null;
        }
        shapeLoadingDialog = new ShapeLoadingDialog2(context);
        shapeLoadingDialog.setCanceledOnTouchOutside(true);
        shapeLoadingDialog.setLoadingText("加载中...");
        //shapeLoadingDialog.show();
        return  shapeLoadingDialog;
    }

    public static void showProgressDialog(Context context,String str){
        if(null != shapeLoadingDialog){
            shapeLoadingDialog.dismiss();
            shapeLoadingDialog = null;
        }
        shapeLoadingDialog = new ShapeLoadingDialog2(context);
        shapeLoadingDialog.setCanceledOnTouchOutside(true);
        shapeLoadingDialog.setLoadingText(str);
        shapeLoadingDialog.show();
    }
    public static ShapeLoadingDialog2 initProgressDialog(Context context,String str){
        if(null != shapeLoadingDialog){
            shapeLoadingDialog.dismiss();
            shapeLoadingDialog = null;
        }
        shapeLoadingDialog = new ShapeLoadingDialog2(context);
        shapeLoadingDialog.setCanceledOnTouchOutside(true);
        shapeLoadingDialog.setLoadingText(str);
        //shapeLoadingDialog.show();
        return  shapeLoadingDialog;
    }

    public static void showProgressOnTouchDialog(Context context, boolean b){
        if(null != shapeLoadingDialog){
            shapeLoadingDialog.dismiss();
            shapeLoadingDialog = null;
        }
        shapeLoadingDialog = new ShapeLoadingDialog2(context);
        shapeLoadingDialog.setCanceledOnTouchOutside(b);
        shapeLoadingDialog.setLoadingText("加载中...");
        shapeLoadingDialog.show();
    }

    public static void showProgressOnTouchDialog(Context context,String str, boolean b){
        if(null != shapeLoadingDialog){
            shapeLoadingDialog.dismiss();
            shapeLoadingDialog = null;
        }
        shapeLoadingDialog = new ShapeLoadingDialog2(context);
        shapeLoadingDialog.setCanceledOnTouchOutside(b);
        shapeLoadingDialog.setLoadingText(str);
        shapeLoadingDialog.show();
    }

    //销毁进度对话框
    public static void hideProgressDialog(){
        try {
            if(null != shapeLoadingDialog){
                shapeLoadingDialog.dismiss();
                shapeLoadingDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
