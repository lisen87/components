package com.leeson.components.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.leeson.components.R;
import com.leeson.components.functionInterface.IClickListener;
import com.leeson.components.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 * lisen
 *
 *      [
             {
             "content": "0 选项",
             "id": "f55b7038-f360-40d2-8348-001e4a8aa067",
             "isChecked": false,
             "items": [
             {
             "content": "0 -1子选项",
             "id": "556e39d7-dec0-4149-8e85-781925ad30bb",
             "isChecked": false,
             "items": [
             {
             "content": "0 -2子选项",
             "id": "a6b87adc-9510-4860-bb85-9e9cbe17479d",
             "isChecked": false,
             "items": [
             {
             "content": "0 -3子选项",
             "id": "738b0790-772b-40f2-b810-5054c7d2d599",
             "isChecked": false,
             "level": 3
             }
             ],
             "level": 2
             }
             ],
             "level": 1
             },
             ],
             "level": 0,
             "title": "0 title"
             }
             ]
 *
 *
 *
 *==============================================使用==========================================================
 *    private void initFilterLayout() {
        List<TempBean> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {

        TempBean tempBean = new TempBean();
        tempBean.setTitle(i+" title");
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
 fliterLayout.setDatas(list);
 }
 *
 *
 *
 *
 *
 */
public class FilterLayout extends LinearLayout {

    public static final int CHANGE_MODE = 0;
    public static final int RESET_MODE = 1;
    public static final int NULL_MODE = 2;
    public static final int ALWAYS_MODE = 3;

    private int mode = CHANGE_MODE;

    public FilterLayout(Context context) {
        this(context,null);
    }

    public FilterLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    private LayoutInflater inflater;

    private LinearLayout layoutcontainer;
    private HorizontalScrollView scrollView;

    private int scrollViewWidth;
    private int screenHeight;
    private int screenWidth;

    private int index;//当前选中的 标题的角标
    private CheckBox checkBox;//当前选中的checkbox

    private List<TempBean> datas = new ArrayList<>();
    private Rect rect = new Rect();

    /**
     *
     * @param mode CHANGE_MODE 只要点击选择后相应的title就会改变文字
     *             RESET_MODE 点击选择后其他的title都复原，只要选中的改变文字
     *             NULL_MODE
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    public FilterLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.filter_layout, this);

        scrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView);
        layoutcontainer = (LinearLayout) view.findViewById(R.id.container);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        getGlobalVisibleRect(rect);
        scrollViewWidth = scrollView.getMeasuredWidth();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        screenHeight = outMetrics.heightPixels;
        screenWidth = outMetrics.widthPixels;
    }
    public void setDatas(List<TempBean> datas){
        if (datas == null || datas.size() == 0){
            return;
        }
        this.datas = datas;
        layoutcontainer.removeAllViews();
        for (int i = 0; i < datas.size(); i++) {//设置顶部标签
            View view = inflater.inflate(R.layout.item_filterlayout, layoutcontainer,false);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            checkBox.setText(datas.get(i).getContent());
            checkBox.setChecked(false);
            checkBox.setOnClickListener(new Listener(i));//显示popuwindow
            layoutcontainer.addView(view);
        }
    }
    private class Listener implements OnClickListener{
        private int position;
        public Listener(int position){
            this.position = position;
        }
        @Override
        public void onClick(View v) {
            if (CommonUtils.isKeyboardShown(getRootView())){
                CommonUtils.closeKeyBoard(getContext());
            }
//            cleanAll();
            CheckBox checkBox = (CheckBox) v;
            checkBox.setChecked(true);
            scrollToTarget(position);

            List<TempBean> items = datas.get(position).getItems();
            if (items == null || items.size() == 0){

                return;
            }
            index = position;
            FilterLayout.this.checkBox = checkBox;
            Log.e(""," ========== "+position);
            //选择后显示pop

            View view = inflater.inflate(R.layout.filterlayout_popup,null);
            final PopupWindow popupWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(screenHeight - rect.top-layoutcontainer.getHeight());
            LinearLayout recyclerContainer = (LinearLayout) view.findViewById(R.id.recyclerContainer);


            LinearLayout layout_first = (LinearLayout) view.findViewById(R.id.layout_first);
            fillContainer(layout_first,items,popupWindow,true,recyclerContainer);

            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setBackgroundDrawable(new ColorDrawable());

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    cleanAll();
                }
            });

            popupWindow.setAnimationStyle(R.style.PopupWindow_FilterLayout);
            popupWindow.showAtLocation(layoutcontainer, Gravity.TOP, 0, rect.top+layoutcontainer.getHeight());
        }
    }

    private void fillContainer(final LinearLayout layout, List<TempBean> items,
                               final PopupWindow popupWindow, boolean isWhite, final LinearLayout recyclerContainer) {
        final View view = genMenu(items, popupWindow, isWhite, recyclerContainer);
        view.setTag("first");

        layout.addView(view);
    }

    @NonNull
    private View genMenu(final List<TempBean> items,
                         final PopupWindow popupWindow,
                         boolean isWhite, final LinearLayout recyclerContainer) {
        final View view = inflater.inflate(R.layout.popup_recyclerview, null);
        final MaxSizeRecyclerView recyclerView= (MaxSizeRecyclerView) view.findViewById(R.id.recyclerView);
        if (isWhite){
            recyclerView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        AdapterPopupList_White adapterPopupList_white = new AdapterPopupList_White(getContext());
        adapterPopupList_white.setDatas(items);
        recyclerView.setAdapter(adapterPopupList_white);
        adapterPopupList_white.setiClickListener(new IClickListener<TempBean>() {
            @Override
            public void onClick(TempBean tempBeen, int positon) {

                List<TempBean> beans = tempBeen.getItems();
                if (beans != null && beans.size() != 0){

                    resetDatasStatus(beans);
                    if (recyclerContainer.getChildCount() > 0){
                        if ("first".equals(view.getTag())){
                            recyclerContainer.removeAllViews();
                        }else{
                            reset(recyclerContainer,tempBeen);
                        }
                    }
                    final View content = genMenu(beans,popupWindow,true,recyclerContainer);
                    recyclerContainer.addView(content);

                    //当大于第二级列表的高度大于第二级列表时，高度有问题

                    //设置横向滚动的view的最大宽度为屏幕的2/3
                    int areaNum = recyclerContainer.getChildCount()+1;
                    if (areaNum > 3){
                        areaNum = 3;
                    }
                    ((View)(recyclerContainer.getParent())).getLayoutParams().width
                            = (areaNum - 1)*screenWidth / areaNum;

                    recyclerContainer.post(new Runnable() {
                        @Override
                        public void run() {
                            resetLayout(recyclerContainer);
                        }
                    });
                }else{

                    if(checkBox != null){
                        switch (mode){
                            case CHANGE_MODE://只要点击的不是最后条目的第一项，选择后相应的title就会改变文字
                                if (items.indexOf(tempBeen) != 0){
                                    checkBox.setText(tempBeen.getContent());
                                }else {
                                    checkBox.setText(datas.get(index).getContent());
                                }
                                break;
                            case RESET_MODE://点击选择后其他的title都复原，只要选中的改变文字
                                resetTitles();
                                checkBox.setText(tempBeen.getContent());
                                break;
                            case NULL_MODE:


                                break;
                            case ALWAYS_MODE://只要点击选择后相应的title就会改变文字
                                checkBox.setText(tempBeen.getContent());
                                break;
                        }
                    }

                    if (iClickListener != null){
                        /**
                         * index 标题角标 0,1,2,3
                         */
                        iClickListener.onClick(tempBeen,index);
                    }
                    Log.e("",tempBeen.getContent()+"==================接口");
                    popupWindow.dismiss();
                }
            }
        });

        return view;
    }

    /**
     *  通过列表的级别，将布局中（除了index是级别的view）的view删除
     * @param recyclerContainer
     * @param tempBeen
     */
    private void reset(LinearLayout recyclerContainer,TempBean tempBeen){
        int count = recyclerContainer.getChildCount();
        for (int i = count -1; i > 0; i--) {
            if (i > tempBeen.getLevel()-2){//为什么要减2？因为container中的第一个孩子的level是2
                recyclerContainer.removeViewAt(i);
            }
        }
    }

    /**
     * 重新设置列表宽度
     * @param container
     */
    private void resetLayout(LinearLayout container){
        int count = container.getChildCount();
        LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT,1f);
        for (int i = 0; i <count; i++) {
            container.getChildAt(i).setLayoutParams(layoutParams);
            container.getChildAt(i).requestLayout();
        }
    }
    /**
     * 将所有的取消选择
     */
    private void cleanAll(){
        for (int i = 0; i < layoutcontainer.getChildCount(); i++) {
            View view = layoutcontainer.getChildAt(i);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            checkBox.setChecked(false);
        }
    }

    /**
     * 重置标题
     */
    private void resetTitles(){
        for (int i = 0; i < layoutcontainer.getChildCount(); i++) {
            View view = layoutcontainer.getChildAt(i);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            checkBox.setText(datas.get(i).getContent());
        }
    }

    /**
     * scrollView滚动到中间项
     * @param position
     */
    private void scrollToTarget(int position) {

        if (layoutcontainer.getChildCount() == 0) {
            return;
        }

        int location = -getPaddingLeft();
        for (int j = 0; j <= position; j++) {
            View view = layoutcontainer.getChildAt(j);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            location += view.getMeasuredWidth()+layoutParams.rightMargin;
        }
        location -= layoutcontainer.getChildAt(position).getMeasuredWidth()/2;

        scrollView.smoothScrollTo(location - scrollViewWidth/2, 0);
    }

    private void resetDatasStatus(List<TempBean> datas){
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setChecked(false);
        }
    }
    private IClickListener<TempBean> iClickListener;

    public void setiClickListener(IClickListener<TempBean> iClickListener) {
        this.iClickListener = iClickListener;
    }
}
