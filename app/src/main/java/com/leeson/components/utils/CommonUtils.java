package com.leeson.components.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/26.
 * lisen
 */

@SuppressWarnings("all")
public class CommonUtils {
    public static String saveBitmap(Context context,Bitmap bitmap){
        try {
            File file = new File(context.getCacheDir() + "/tempBitmap/");
            if (!file.exists()){
                file.mkdirs();
            }
            File tempBitmap = new File(file,"tempBitmap.png");
            if (tempBitmap.exists()){
                tempBitmap.delete();
                tempBitmap.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(tempBitmap,false);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
                out.flush();
                out.close();
            }
            return tempBitmap.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /**
     *
     * @param context
     * @param view
     * @return 图片的路径
     */
    public static String saveBitmap(Context context, View view){
        try {
            File file = new File(context.getCacheDir() + "/tempBitmap/");
            if (!file.exists()){
                file.mkdirs();
            }
            File tempBitmap = new File(file,"tempBitmap.png");
            FileOutputStream out = new FileOutputStream(tempBitmap);
            Bitmap bitmap = createViewBitmap(view);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 50, out)){
                out.flush();
                out.close();
            }
            return tempBitmap.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得当天0点时间
     * @return 毫秒
     */
    public static long getTodayStartTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获得当天24点时间
     * @return 毫秒
     */
    public static long getTodayEndTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 通过秒数获取是几天
     * @param time
     * @return
     */
    public static int getDayCounts(long time){
        return (int) (time/60/60/24);
    }
    public static int getDayCounts(double time){
        return (int) (Math.ceil(time/60/60/24));
    }

    /**
     * 将天数转为秒
     * @param days
     * @return
     */
    public static long getDaysToSec(String days){
        long time = 0;
        if (TextUtils.isEmpty(days)) {
            days = "0";
        }
        float day = Float.parseFloat(days);
        time = (long) (day * 24 *60 * 60);

        return time;
    }
    public static long getDaysToSec(int days){
        long time = 0;
        time = (long) (days * 24 *60 * 60);

        return time;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 将时间转为时间戳
     * @param time 2008-08-08 23:59:59
     * @return 时间戳（单位毫秒）
     */
    public static long parseStringToTime(String time,int type) {
        SimpleDateFormat simpleDateFormat = null;
        if(TextUtils.isEmpty(time)){
            return 0;
        }else {
            switch (type){
                case 0:
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                    break;
                case 1:
                    simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
                    break;
            }
            try {
                if (simpleDateFormat != null){
                    Date date = simpleDateFormat.parse(time);
                    return date.getTime();
                }else{
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }


    public static long getCurrentTime(){
        return System.currentTimeMillis();
    }

    /**
     * 关闭输入法
     * @param context
     */
    public static void closeKeyBoard(Context context){
        InputMethodManager inputManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * 键盘是否显示
     * @param view
     * @return
     */
    public static boolean isKeyboardShown(View view) {
        View rootView = view.getRootView();
        final int softKeyboardHeight = 50;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    /**
     *
     * @param time 毫秒
     * @param type
     * @return
     */
    public static String parseTimeinSecToString(long time, int type) {

        SimpleDateFormat format = null;
        if (type == 1) {

            format = new SimpleDateFormat("MM-dd",Locale.CHINA);
        } else if (type == 2) {

            format = new SimpleDateFormat("MM月",Locale.CHINA);
        } else if (type == 3) {

            format = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        } else if (type == 4) {

            format = new SimpleDateFormat("HH:mm",Locale.CHINA);
        } else if (type == 5) {

            format = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        } else if (type == 6) {
            format = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);
        } else if (type == 7) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
        } else if (type == 8) {

            format = new SimpleDateFormat("yyyyMMdd",Locale.CHINA);
        }else if (type == 9) {

            format = new SimpleDateFormat("yyyy.MM.dd",Locale.CHINA);
        }else if (type == 10){
            format = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);
        }
        return format.format(new Date(time));
    }

    /**
     *
     * @param num
     * @return
     */
    public static int parseInt(String num){
        if (TextUtils.isEmpty(num)){
            return 0;
        }
        try{
            return Integer.parseInt(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static long parseLong(String num){
        if (TextUtils.isEmpty(num)){
            return 0;
        }
        try{
            return Long.parseLong(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static boolean parseBoolean(String bool){
        if (TextUtils.isEmpty(bool)){
            return false;
        }
        try{
            return Boolean.parseBoolean(bool);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     *
     * @param num
     * @return
     */
    public static double parseDouble(String num){
        if (TextUtils.isEmpty(num)){
            return 0;
        }
        try{
            return Double.parseDouble(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static float parseFloat(String num){
        if (TextUtils.isEmpty(num)){
            return 0;
        }
        try{
            return Float.parseFloat(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 显示金额格式化
     * @param db
     * @return
     */
    public static String formatToString(double db){
        DecimalFormat df= new DecimalFormat("0.00");
        return df.format(db);
    }

    /**
     * 判断是否是手机号
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone){
        String regex = "1[345678]\\d{9}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }
    private static long lastClickTime ;
    /**
     * 快速点击
     * @return
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 400) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     *
     * @param text
     * @param ratio 比例
     */
    public static SpannableString warpPayText(String text,float ratio){

        int splitIndex = text.indexOf(".");
        if (splitIndex < 0){
            splitIndex = 0;
        }

        SpannableString spannableString = new SpannableString(text);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(ratio);
        spannableString.setSpan(sizeSpan,splitIndex,text.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
    public static SpannableString warpFirstText(String text,float ratio){

        SpannableString spannableString = new SpannableString(text);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(ratio);
        spannableString.setSpan(sizeSpan,1,text.length(),Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public static String wrapNull(String data){
        return TextUtils.isEmpty(data) ? "" : data;
    }


    public static String convertToBankNo(String text,int a){
        int start = 0;
        StringBuilder stringBuilder = new StringBuilder();

        String r = "\\d{4}";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()){
            count++;
            start = matcher.start();
            stringBuilder.append(matcher.group());
            stringBuilder.append(" ");
        }
        String last = text.substring(start+count,text.length());
        stringBuilder.append(last);
        return stringBuilder.toString();
    }

    /**
     * 当有汉字和数字时，值取汉字  如：一口价112，只取 ： 一口价
     * @param content
     * @return
     */
    public static String filterStr(String content){
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()){
            return content.substring(0,matcher.start());
        }
        return content;
    }

    /**
     * 格式化银行卡号
     * @param text
     * @return
     */
    public static String convertToBankNo(String text){

        int length = text.length();
        int lastLength = length%4;

        StringBuilder stringBuilder = new StringBuilder();

        String r = "\\d{4}";
        Pattern pattern = Pattern.compile(r);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()){
            stringBuilder.append(matcher.group());
            stringBuilder.append(" ");
        }
        String last = text.substring(length-lastLength,text.length());
        stringBuilder.append(last);
        return stringBuilder.toString();
    }

    public static Bitmap getBitmapFormView(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static void saveBitmapToSdcard(Bitmap bitmap,File file){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件复制
     * @param oldFile
     * @param newFile
     */
    public static void cloneFile(File oldFile, File newFile) {

        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(oldFile);
            fo = new FileOutputStream(newFile);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (fi != null){
                    fi.close();
                }
                if (in != null){
                    in.close();
                }
                if (fo != null){
                    fo.close();
                }
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断网络连接是否有效
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo net
                = ((ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
        return net != null && net.isConnected();
    }

    //除
    public static double div(double value1, double value2) {
        if (value2 == 0) {
            return 0;
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2,3,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    //乘
    public static double getValues(double value1,double value2){

        BigDecimal bigDecimalNum = new BigDecimal(value1);
        BigDecimal bigDecimalPrice = new BigDecimal(value2);

        return bigDecimalNum.multiply(bigDecimalPrice).doubleValue();
    }
    public static double getValues(String value1,String value2){

        BigDecimal bigDecimalNum = new BigDecimal(value1);
        BigDecimal bigDecimalPrice = new BigDecimal(value2);

        return bigDecimalNum.multiply(bigDecimalPrice).doubleValue();
    }
    //加
    public static double add(double value1,double value2){
        BigDecimal b1=new BigDecimal(Double.toString(value1));
        BigDecimal b2=new BigDecimal(Double.toString(value2));
        return b1.add(b2).doubleValue();
    }

    //减
    public static double sub(double value1,double value2){
        BigDecimal b1=new BigDecimal(Double.toString(value1));
        BigDecimal b2=new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).doubleValue();
    }

    public static void startAnimaActivity(Context context, View view, List<String> imageList, int position){
//        Intent intent = new Intent(context,PhotosActivity.class);
//        intent.putExtra(PhotosActivity.CURRENTPOSITION,position);
//        intent.putStringArrayListExtra(PhotosActivity.IMAGES, (ArrayList<String>) imageList);
//
//        ActivityOptionsCompat compat =
//                ActivityOptionsCompat.makeScaleUpAnimation(view,view.getWidth()/2,view.getHeight()/2,0,0);
//        ActivityCompat.startActivity(context,intent,compat.toBundle());
    }


    /**
     * 判断view是否滚动到达顶部
     * @param view
     * @return true 到达顶部
     */
    public static boolean isReachTop(View view){
        if (view instanceof ScrollView || view instanceof NestedScrollView){
            return view.getScrollY() ==0 ; //scrollView判断到达顶部
        }else if (view instanceof RecyclerView ){
            return  !view.canScrollVertically(-1);//表示是否能向下滚动，false表示已经滚动到顶部
        }
        return true;
    }

    /**
     * 判断view是否滚动到达底部
     * @param view
     * @return true 到达底部
     */
    public static boolean isReachBottom(View view){
        if (view instanceof ScrollView || view instanceof NestedScrollView){
            return view.getMeasuredHeight()+view.getScrollY() >= ((ViewGroup)view).getChildAt(0).getMeasuredHeight();
        }else if (view instanceof RecyclerView ){
            return  !view.canScrollVertically(1);//表示是否能向上滚动，false表示已经滚动到底部
        }
        return true;
    }


    private static long pressedTime = 0;
    public static boolean doublePressForExit(Context context){
        if (System.currentTimeMillis() - pressedTime > 2000){
            ToastUtil.showShort(context.getApplicationContext(),"再按一次返回桌面");
            pressedTime = System.currentTimeMillis();
            return false;
        }else{
            return true;
        }
    }

    /**
     *
     * @param context
     * @param tip 提示语
     * @return
     */
    public static boolean doublePressTip(Context context,String tip){
        if (System.currentTimeMillis() - pressedTime > 2000){
            ToastUtil.showShort(context.getApplicationContext(),tip);
            pressedTime = System.currentTimeMillis();
            return false;
        }else{
            return true;
        }
    }
}
