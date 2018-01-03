package com.leeson.components.views.dialogs;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.leeson.components.R;
import com.leeson.components.functionInterface.IWarningDialog;
import com.leeson.components.utils.CommonUtils;
import com.leeson.components.views.DragLayout;
import com.leeson.components.views.MaxScorllView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/6/6.
 * lisen
 * 询问确定  取消
 */

public class DialogWarning implements DragLayout.DragListener {

    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.scrollView)
    MaxScorllView scrollView;
    @BindView(R.id.dragLayout)
    DragLayout dragLayout;
    private Context context;
    private IWarningDialog iWarningDialog;

    private View view;
    private String negativeMsg = "取消";
    private String positiveMsg = "同意";

    private AlertDialog dialog;

    public void setNegativeMsg(String negativeMsg) {
        this.negativeMsg = negativeMsg;
    }

    public void setPositiveMsg(String positiveMsg) {
        this.positiveMsg = positiveMsg;
    }

    public DialogWarning(Context context, IWarningDialog iWarningDialog) {
        this.context = context;
        this.iWarningDialog = iWarningDialog;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_warning, null);
        ButterKnife.bind(this, view);
        dragLayout.setListener(this);
    }

    public void setMsgGravity(int gravity) {
        tvMsg.setGravity(gravity);
    }
    public void showTitle(int visible){
        tvTitle.setVisibility(visible);
    }
    public void setTitle(String title){
        tvTitle.setText(title);
    }

    /**
     *
     * @param height  px
     */
    public void setMsgHeight(int height){
        scrollView.setMaxHeight(height);
    }

    public void show(String content) {

        tvMsg.setText(content);
        tvCancel.setText(negativeMsg);
        tvOk.setText(positiveMsg);

        dialog = new AlertDialog.Builder(context, R.style.RegisterNextDialog)
                .setView(view).create();
        dialog.show();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dialog.dismiss();
                if (iWarningDialog != null) {
                    iWarningDialog.onCancelListener();
                }
                break;
            case R.id.tv_ok:
                dialog.dismiss();
                if (iWarningDialog != null) {
                    iWarningDialog.onConfirmListener();
                }
                break;
        }
    }

    @Override
    public boolean isCanPullDown() {
        return CommonUtils.isReachTop(scrollView);
    }

    @Override
    public boolean isCanPullUp() {
        return CommonUtils.isReachBottom(scrollView);
    }
}
