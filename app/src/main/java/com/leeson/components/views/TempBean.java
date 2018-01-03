package com.leeson.components.views;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 * lisen
 */

public class TempBean {

    private int index;

    private String content;
    private String validData;//有效数据（后台请求接口使用的ID之类的数据）
    private List<TempBean> items;

    private boolean isChecked;

    private int level;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValidData() {
        return validData;
    }

    public void setValidData(String validData) {
        this.validData = validData;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public List<TempBean> getItems() {
        return items;
    }

    public void setItems(List<TempBean> items) {
        this.items = items;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TempBean{" +
                "index='" + index + '\'' +
                ", content='" + content + '\'' +
                ", validData='" + validData + '\'' +
                ", items=" + items +
                ", isChecked=" + isChecked +
                ", level=" + level +
                '}';
    }
}
