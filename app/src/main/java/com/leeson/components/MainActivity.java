package com.leeson.components;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.leeson.components.functionInterface.IClickListener;
import com.leeson.components.functionInterface.IWarningDialog;
import com.leeson.components.utils.CommonUtils;
import com.leeson.components.utils.ToastUtil;
import com.leeson.components.views.DragLayout;
import com.leeson.components.views.FilterLayout;
import com.leeson.components.views.TempBean;
import com.leeson.components.views.dialogs.DialogWarning;
import com.leeson.components.views.dialogs.ItemsDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DragLayout.DragListener {

    private String content = "为了给用户创造一种全新的设计语言，我们挑战自我，融合了创新以及科技的潜力。这就是 Material Design。这份文档是动态更新的，我们会不断的探索 Material Design 的理念和规范。";

    @BindView(R.id.filter)
    FilterLayout filterLayout;

    @BindView(R.id.dragLayout)
    DragLayout dragLayout;

    @BindView(R.id.nestScrollView)
    NestedScrollView nestScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dragLayout.setListener(this);
    }

    @Override
    public boolean isCanPullDown() {
        return CommonUtils.isReachTop(nestScrollView);
    }

    @Override
    public boolean isCanPullUp() {
        return CommonUtils.isReachBottom(nestScrollView);
    }

    @OnClick({R.id.tv1, R.id.tv2,R.id.tv3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                new ItemsDialog.Buidler(this)
                        .addItem("JAVA")
                        .addItem("APP", R.color.green)
                        .addItem("APP", R.color.green)
                        .addItem("APP", R.color.green)
                        .addItem("APP", R.color.green)
                        .addItem("APP", R.color.green)
                        .addItem("android","android",R.color.colorAccent)
                        .addItem("android","android",R.color.colorAccent)
                        .addItem("android","android",R.color.colorAccent)
                        .addItem("android","android",R.color.colorAccent)
                        .addItem("android","android",R.color.colorAccent)
                        .setDialogHeight(CommonUtils.getScreenHeight(this)/2)
                        .setSimpleListener(new ItemsDialog.SimpleSheetListener() {
                            @Override
                            public void onSheetItemClick(int position, ItemsDialog.ItemBean itemBean) {
                                switch (position){
                                    case 0:
                                        ToastUtil.showShort(MainActivity.this, itemBean.getName().toString());
                                        break;
                                    case 1:
                                        ToastUtil.showShort(MainActivity.this, itemBean.getName().toString());
                                        break;
                                }
                            }
                        }).build();

                break;
            case R.id.tv2:
                DialogWarning warning = new DialogWarning(this, new IWarningDialog() {
                    @Override
                    public void onConfirmListener() {
                        ToastUtil.showShort(MainActivity.this,content);
                    }

                    @Override
                    public void onCancelListener() {

                    }
                });
                warning.setMsgHeight(CommonUtils.getScreenHeight(this)/2);
                warning.showTitle(View.VISIBLE);
                warning.setTitle("这是标题");
                warning.show(content+"\n"+ content+"\n"+ content);

                break;
            case R.id.tv3:


                initFilterLayout();


                Log.e("===", "onViewClicked: ");
                break;
        }
    }

    private void initFilterLayout() {
        List<TempBean> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {

            TempBean tempBean = new TempBean();
            tempBean.setContent(i+" 选项");
            tempBean.setLevel(0);

            List<TempBean> items = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                TempBean first = new TempBean();
                first.setContent(j+" -1子选项");
                first.setLevel(1);

                List<TempBean> secondItems = new ArrayList<>();
                for (int k = 0; k < 6; k++) {
                    TempBean second = new TempBean();
                    second.setLevel(2);
                    ////---------------
                    List<TempBean> thirdItems = new ArrayList<>();
                    for (int g = 0; g < 6; g++) {
                        TempBean third = new TempBean();

                        List<TempBean> fItems = new ArrayList<>();
                        for (int h = 0; h < 6; h++) {
                            TempBean f = new TempBean();
                            f.setLevel(4);
                            f.setContent(h+" -4子选项");
                            fItems.add(f);
                            third.setItems(fItems);
                        }


                        third.setLevel(3);
                        third.setContent(g+" -3子选项");
                        thirdItems.add(third);
                        second.setItems(thirdItems);
                    }
                    ////---------------

                    second.setContent(k+" -2子选项");
                    secondItems.add(second);
                    first.setItems(secondItems);
                }


                items.add(first);
            }


            tempBean.setItems(items);
            list.add(tempBean);
        }
        filterLayout.setDatas(list);
        filterLayout.setiClickListener(new IClickListener<TempBean>() {
            @Override
            public void onClick(TempBean tempBean, int positon) {

            }
        });
    }

}
