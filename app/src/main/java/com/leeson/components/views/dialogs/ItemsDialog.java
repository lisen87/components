package com.leeson.components.views.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leeson.components.R;
import com.leeson.components.views.MaxScorllView;
import com.leeson.components.views.underLineView.UnderLineTextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 * lisen
 *
 *
 *              new ItemsDialog.Buidler(MainActivity.this)
                     .addItem("这是map",map)
                     .addItem("这是string","你好！")
                     .addItem("这是蓝色字体","这是蓝色",R.color.colorPrimary)
                     .addItem("红色字体",R.color.colorAccent)
                     .setSimpleListener(new ItemsDialog.SimpleSheetListener() {
                        @Override
                        public void onSheetItemClick(int position, ItemsDialog.ItemBean itemBean) {

                        }).build();
                    }
 *
 *
 *
 */

@SuppressWarnings("all")
public class ItemsDialog extends BottomSheetDialog {

    private Context context;
    private ItemsDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = getScreenHeight(context);
        int statusBarHeight = getStatusBarHeight(context);
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
    }

    /**
     * 泛型添加在类上，所有的方法都必须是指定的泛型类 类型
     */
    public static class Buidler {
        private Context context;
        private View view;
        private BottomSheetDialog dialog ;

        private TextView tv_cancel;

        private boolean disableCancel;

        public Buidler disableCancel(boolean disableCancel) {
            this.disableCancel = disableCancel;
            return this;
        }

        private int textSize = 16;
        private SimpleSheetListener simpleListener;
        private int maxHeight = -1;

        public Buidler(Context context) {
            this.context = context;
            dialog = new ItemsDialog(context);
            view = LayoutInflater.from(context).inflate(R.layout.ios_dialog,null);

            tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        }

        private List<ItemBean> itemBeans = new ArrayList<>();

        /**
         * 泛型使用在方法上
         * @param itemName
         * @return
         */
        public Buidler addItem(CharSequence itemName){
            ItemBean itemBean = new ItemBean();
            itemBean.setName(itemName);
            itemBeans.add(itemBean);
            return this;
        }

        public Buidler addItem(String itemName, Object t){
            ItemBean itemBean = new ItemBean();
            itemBean.setName(itemName);
            itemBean.setT(t);
            itemBeans.add(itemBean);
            return this;
        }

        public Buidler addItem(String itemName,@ColorRes int color){
            ItemBean itemBean = new ItemBean();
            itemBean.setName(itemName);
            itemBean.setTextColor(color);
            itemBeans.add(itemBean);
            return this;
        }

        public Buidler addItem(String itemName,Object t,@ColorRes int color){
            ItemBean itemBean = new ItemBean();
            itemBean.setName(itemName);
            itemBean.setT(t);
            itemBean.setTextColor(color);
            itemBeans.add(itemBean);
            return this;
        }

        /**
         *
         * @param textSize sp
         * @return
         */
        public Buidler setTextSize(int textSize){
            this.textSize = textSize;
            return this;
        }

        public Buidler setSimpleListener(SimpleSheetListener simpleListener) {
            this.simpleListener = simpleListener;
            return this;
        }

        /**
         *  信息选项部分最大高度不超过height
         * @param height px
         * @return
         */
        public Buidler setDialogHeight(int height){
            maxHeight = height;
            return this;
        }

        public Buidler build(){

            if (itemBeans.size() > 0){
                MaxScorllView maxScorllView = (MaxScorllView) view.findViewById(R.id.maxScorllView);
                if (maxHeight > -1){
                    maxScorllView.setMaxHeight(maxHeight);
                }

                LinearLayout layout_container = (LinearLayout) view.findViewById(R.id.layout_container);

                for (int i = 0; i < itemBeans.size(); i++) {

                    final ItemBean itemBean = itemBeans.get(i);
                    UnderLineTextView tv = new UnderLineTextView(context);
                    tv.setText(itemBean.name);
                    tv.setTextColor(ContextCompat.getColor(context,itemBean.textColor));

                    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.height = dp2px(context,40);
                    tv.setLayoutParams(p);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

                    tv.setBackgroundResource(R.drawable.selector_pressed);

                    final int position = i;
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (simpleListener != null){
                                simpleListener.onSheetItemClick(position,itemBean);
                            }
                            dialog.dismiss();
                        }
                    });

                    layout_container.addView(tv);
                }
            }

            if (disableCancel){
                tv_cancel.setVisibility(View.GONE);
            }

            dialog.setContentView(view);
            dialog.show();
            ((View)(view.getParent())).setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });
            return this;
        }

    }


    public static class ItemBean{
        private CharSequence name;
        private int textColor = android.R.color.black;

        private Object t;

        public ItemBean() {
        }


        public CharSequence getName() {
            return name;
        }

        public void setName(CharSequence name) {
            this.name = name;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        public Object getT() {
            return t;
        }

        public void setT(Object t) {
            this.t = t;
        }

        @Override
        public String toString() {
            return "ItemBean{" +
                    "name='" + name + '\'' +
                    ", textColor=" + textColor +
                    ", t=" + t +
                    '}';
        }
    }

    private static int dp2px(Context context,int dp) {

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics());
    }

    public interface SimpleSheetListener{
         void onSheetItemClick(int position, ItemBean itemBean);
    }
    public int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
