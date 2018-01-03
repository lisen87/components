package com.leeson.components.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.leeson.components.views.RoundCornerTextView;


public class ToastUtil {
    private static Toast toast;

    public static void showShort(Context context, String content){
        if (toast == null) {
            toast = new Toast(context.getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            RoundCornerTextView textView = new RoundCornerTextView(context);
            textView.setText(content);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
            textView.setCorner(10);
            textView.setPadding(20,20,20,20);
            textView.setSolidColor(Color.parseColor("#99000000"));
            toast.setView(textView);

        } else {
            ((TextView)(toast.getView())).setText(content);
        }
        toast.show();
    }
}
