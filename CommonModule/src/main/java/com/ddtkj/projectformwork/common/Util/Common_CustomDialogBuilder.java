package com.ddtkj.projectformwork.common.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.ddtkj.projectformwork.common.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * 确认对话框
 *
 * @ClassName: com.ddt.supaiwang.Util
 * @Description:
 * @author: Administrator杨重诚
 * @date: 2016/6/6 17: 34
 */
public class Common_CustomDialogBuilder {
    Context context;
    NiftyDialogBuilder dialogBuilder = null;
    private Effectstype effect = Effectstype.RotateBottom;

    public Common_CustomDialogBuilder(Context context) {
        this.context = context;
        if (dialogBuilder == null) {
            dialogBuilder = NiftyDialogBuilder.getInstance(context);
        }
    }
    /**
     * 软件更新
     * @param titleMsg
     * @param contentMsg
     * @param contentColor
     * @param isCancelableOnTouchOutside
     * @param button1Text1
     * @param but1Drawable
     * @param button1Text2
     * @param but2Drawable
     * @return
     */
    public NiftyDialogBuilder showDialog(String titleMsg, String contentMsg, int contentColor,boolean isCancelableOnTouchOutside, String button1Text1, int but1Drawable, String button1Text2, int but2Drawable) {
        dialogBuilder
                .withTitle(titleMsg)
                .withTitleColor(context.getResources().getColor(R.color.black))
                .withCloseImage(R.mipmap.common_icon_close_x)
                .withDividerColor(context.getResources().getColor(R.color.line_gray))
                .withMessage(null)
                .withMessageColor(context.getResources().getColor(R.color.account_text_gray))
                .withDialogColor(context.getResources().getColor(R.color.white))
                .isCancelableOnTouchOutside(isCancelableOnTouchOutside)
                .withDuration(700)
                .withEffect(effect)
                .withButton1Text(button1Text1)
                .withButton2Text(button1Text2)
                .withButtonDrawable(but1Drawable, but2Drawable)
                .setCustomView(R.layout.common_dialog_custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
        setContentMsg(dialogBuilder, contentMsg, contentColor);//设置弹出内容
        return dialogBuilder;
    }
//---------------------------------------------------------------------------------------------------------------------------------------
    /**
     * 设置弹出内容
     *
     * @param dialogBuilder
     * @param contentMsg
     * @param contentColor
     */
    private void setContentMsg(NiftyDialogBuilder dialogBuilder, String contentMsg, int contentColor) {
        TextView content = (TextView) dialogBuilder.findViewById(R.id.content);
        content.setText(contentMsg);//设置内容信息
        content.setTextColor(context.getResources().getColor(contentColor));//设置内容
    }

    /**
     * 获取对话框是否已经显示
     * @return
     */
    public boolean isShowing(){
        return dialogBuilder.isShowing();
    }

    /**
     * 监听对话框被销毁
     * @param onDismissListener
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener){
        dialogBuilder.setOnDismissListener(onDismissListener);
    }
}
