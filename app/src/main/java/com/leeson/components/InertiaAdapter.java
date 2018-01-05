package com.leeson.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leeson.components.views.underLineView.UnderLineTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lisen on 2018/1/5.
 *
 * @author lisen < 4533548588@qq.com >
 */

public class InertiaAdapter extends RecyclerView.Adapter<InertiaAdapter.Holder> {

    private List<String> datas;
    private Context context;

    public InertiaAdapter(Context context, List<String> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inertia, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setDatas(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        UnderLineTextView tv;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setDatas(int position) {
            tv.setText(datas.get(position));
        }
    }
}
