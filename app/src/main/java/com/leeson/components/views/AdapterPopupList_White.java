package com.leeson.components.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leeson.components.R;
import com.leeson.components.functionInterface.IClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜房中页面中的 筛选popupwindow
 * Created by Administrator on 2017/9/11.
 * lisen
 */

public class AdapterPopupList_White extends RecyclerView.Adapter<AdapterPopupList_White.Holder> {

    private Context context;

    /**
     * 被选择过的条目
     */
    private TempBean selectedBean;

    public TempBean getSelectedBean() {
        return selectedBean;
    }
    /**
     * 被选择过的条目
     */
    public void setSelectedBean(TempBean selectedBean) {
        this.selectedBean = selectedBean;
    }

    public AdapterPopupList_White(Context context) {
        this.context = context;
    }

    private List<TempBean> datas = new ArrayList<>();

    public List<TempBean> getDatas() {
        return datas;
    }

    public void setDatas(List<TempBean> datas) {
        if (datas != null){
            this.datas = datas;
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popup_white, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tv_content;

        private View item;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.item = itemView;
        }

        public void setData(final int position) {

            if (datas.get(position).isChecked()){
                item.setBackground(ContextCompat.getDrawable(context, R.color.press_color));
            }else{
                item.setBackground(ContextCompat.getDrawable(context,R.drawable.selector_pressed));
            }

            tv_content.setText(datas.get(position).getContent());
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iClickListener != null){
                        resetDatasStatus();
                        datas.get(position).setChecked(true);
                        notifyDataSetChanged();
                        iClickListener.onClick(datas.get(position),position);
                    }
                }
            });
        }
    }
    private IClickListener<TempBean> iClickListener;

    public void setiClickListener(IClickListener<TempBean> iClickListener) {
        this.iClickListener = iClickListener;
    }
    private void resetDatasStatus(){
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setChecked(false);
        }
    }
}
