package com.ddtkj.projectformwork.common.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

/**媒体工具类
 * @author: Administrator 杨重诚
 * @date: 2016/11/11:10:26
 */

public class MediaUtils {
    /**
     *打开相机或相册
     * @param directlyOpenCamera 是否直接启动相机
     */
    public static void openMedia(Context context,boolean directlyOpenCamera) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagepickerImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(false);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(false);
        imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        Integer radius = 150;
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);

        imagePicker.setOutPutX(Integer.valueOf(radius * 2));
        imagePicker.setOutPutY(Integer.valueOf(radius * 2));

        Intent intent = new Intent(context, ImageGridActivity.class);
        ((Activity)context).startActivityForResult(intent, 100);
    }

    /**
     *打开相机或相册
     * @param directlyOpenCamera 是否直接启动相机
     *style 裁切样式
     */
    public static void openMedia(Context context,boolean directlyOpenCamera,CropImageView.Style style) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagepickerImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(false);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(false);
        imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

        imagePicker.setStyle(style);
        Integer radius = 150;
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);

        imagePicker.setOutPutX(Integer.valueOf(radius * 2));
        imagePicker.setOutPutY(Integer.valueOf(radius * 2));

        Intent intent = new Intent(context, ImageGridActivity.class);
        ((Activity)context).startActivityForResult(intent, 100);
    }
    /**
     *打开相机或相册
     * @param directlyOpenCamera 是否直接启动相机
     * @param isCrop 是否裁切
     *
     */
    public static void openMedia(Context context,boolean directlyOpenCamera,boolean isCrop) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagepickerImageLoader());
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(false);
        imagePicker.setCrop(isCrop);
        imagePicker.setSaveRectangle(false);
        imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

        Integer radius = 150;
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);

        imagePicker.setOutPutX(Integer.valueOf(radius * 2));
        imagePicker.setOutPutY(Integer.valueOf(radius * 2));

        Intent intent = new Intent(context, ImageGridActivity.class);
        ((Activity)context).startActivityForResult(intent, 100);
    }

    /**
     * 打开相机或相册，支持多选
     * @param context
     * @param directlyOpenCamera 是否直接启动相机
     * @param selectLimit 选择图片数量
     */
    public static void openMediaMore(Context context,boolean directlyOpenCamera,int selectLimit) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagepickerImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setSelectLimit(selectLimit);
        imagePicker.setShowCamera(false);
        imagePicker.setCrop(false);
        imagePicker.setSaveRectangle(false);
        imagePicker.setDirectlyOpenCamera(directlyOpenCamera);

        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        Integer radius = 150;
        radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, context.getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(radius * 2);
        imagePicker.setFocusHeight(radius * 2);

        imagePicker.setOutPutX(Integer.valueOf(radius * 2));
        imagePicker.setOutPutY(Integer.valueOf(radius * 2));

        Intent intent = new Intent(context, ImageGridActivity.class);
        ((Activity)context).startActivityForResult(intent, 100);
    }
}
