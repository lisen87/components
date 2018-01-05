package com.leeson.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leeson.components.utils.CommonUtils;
import com.leeson.components.views.DragLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lisen on 2018/1/5.
 *  惯性
 * @author lisen < 4533548588@qq.com >
 */

public class RecyActivity extends AppCompatActivity implements DragLayout.DragListener{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.dragLayout)
    DragLayout dragLayout;

    private List<String> datas = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy);
        ButterKnife.bind(this);

        dragLayout.setListener(this);
        for (int i = 0; i < 30; i++) {
            datas.add(i+"");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new InertiaAdapter(this,datas));

    }

    @Override
    public boolean isCanPullDown() {
        return CommonUtils.isReachTop(recyclerView);
    }

    @Override
    public boolean isCanPullUp() {
        return CommonUtils.isReachBottom(recyclerView);
    }
}
