
package com.qs.widget;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.NumberKeyListener;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
public class WidgetBaseUtils {
    /**
     * 得到SpannableString用于修改字符串中的指定字符颜色
     *
     * @param textColor
     * @param text
     * @param start
     * @param end
     * @return SpannableString
     */
    public static SpannableString getSpannableString(String textColor, String text, int start, int end) {

        SpannableString msp = new SpannableString(text);
        msp.setSpan(new ForegroundColorSpan(android.graphics.Color.parseColor(textColor)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return msp;
    }

    /**
     * 得到SpannableString用于修改字符串中的指定字符颜色（多处颜色）
     *
     * @param textColor 字体颜色
     * @param text      文字
     * @param startList 起点list
     * @param endList   终点list （与起点list一一对应）
     * @return SpannableString
     */
    public static SpannableString getSpannableString(String textColor, String text, List<Integer> startList, List<Integer> endList) throws IndexOutOfBoundsException {

        SpannableString msp = new SpannableString(text);
        for (int i = 0; i < startList.size(); i++) {
            int start = startList.get(i);
            int end = endList.get(i);
            msp.setSpan(new ForegroundColorSpan(android.graphics.Color.parseColor(textColor)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return msp;
    }

    public static String md5(String string) {

        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {

        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    public static String getTopActivity(Context context) {

        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null)
            return runningTaskInfos.get(0).topActivity.getClassName();
        else
            return "";
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    @SuppressWarnings("unchecked")
    public static boolean isMobile(String str) {

        if (str == null || str.length() != 11) {
            return false;
        }

        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * Email验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isEmail(String str) {

        if (str == null) {
            return false;
        }

        Pattern p = null;
        Matcher m = null;
        boolean b = false;

        p = Pattern.compile("^[a-zA-Z0-9._%+-]+@(?!.*\\.\\..*)[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        m = p.matcher(str);
        b = m.matches();

        return b;
    }

    /**
     * 身份证验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isIDCard(String str) {

        Pattern p = null;
        Matcher m = null;
        boolean b = false;

        if (str.length() == 15) {
            p = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
            m = p.matcher(str);
            b = m.matches();
        } else if (str.length() == 18) {
            p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
            m = p.matcher(str);
            b = m.matches();
        }

        return b;
    }

    // 获取当前应用的版本code：
    @SuppressWarnings("unused")
    public static int getVersionCode(Context context) {

        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();

        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;

        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        int versionCode = packInfo.versionCode;
        return versionCode;

    }

    // 获取当前应用的版本名：
    @SuppressWarnings("unused")
    public static String getVersionName(Context context) {

        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();

        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;

        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = packInfo.versionName;
        return versionName;
    }

    public static String getPackageName(Context context) {

        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();

        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;

        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        String packageName = packInfo.packageName;
        return packageName;
    }

    /**
     * 返回排序后的请求参数
     *
     * @param map 参数
     * @return string
     */
    public static String getSortUrlParameter(Map<String, String> map) {

        List<Map.Entry<String, String>> mappingList = null;
        //通过ArrayList构造函数把map.entrySet()转换成list
        mappingList = new ArrayList<Map.Entry<String, String>>(map.entrySet());

        Collections.sort(mappingList, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> mapping1, Map.Entry<String, String> mapping2) {
                return mapping1.getKey().compareTo(mapping2.getKey());
            }
        });

        String text = "";
        for (Map.Entry<String, String> mapping : mappingList) {

            String value = mapping.getValue();
//            try {
//                value = URLEncoder.encode(value, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            text += mapping.getKey() + "=" + value + "&";
        }

        if (text.substring(text.length() - 1, text.length()).equals("&"))
            text = text.substring(0, text.length() - 1);

        return text;
    }

    /**
     * 获取当前的时间 截取掉毫秒
     *
     * @return
     */
    public static String getCurrentTime() {
        String time = new Date().getTime() + "";
        time = time.substring(0, time.length() - 3);
        return time;
    }

    /**
     * 限制只能输入字母、数字，_
     *
     * @param editText
     */
    public static void setEditTextInputType(EditText editText) {
        editText.setKeyListener(new NumberKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }

            @Override
            protected char[] getAcceptedChars() {

                char[] numberChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '_',
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
                return numberChars;
            }
        });
    }

    /**
     * 获取屏幕宽度
     *
     * @param context context
     * @return
     */
    public static int getScreenWidth(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int mScreenWidth = outMetrics.widthPixels;

        return mScreenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @param context context
     * @return
     */
    public static int getScreenHeight(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int mScreenHeight = outMetrics.heightPixels;

        return mScreenHeight;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 保存返回的response到手机
     *
     * @param response
     */
    public static void saveCallbackResponse(String response) {

        String directory = getInnerSDCardPath() + "/Food";
        File file = new File(directory);

        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(directory + "/log.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writeSDFile(file.getAbsolutePath(), response, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存CustomException到手机
     *
     * @param text
     */
    public static void saveCustomException(String text) {

        String directory = getInnerSDCardPath() + "/Food";
        File file = new File(directory);

        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(directory + "/exceptionlog.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            writeSDFile(file.getAbsolutePath(), text, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 写文件
     *
     * @param fileName  文件名
     * @param write_str 信息
     * @param flag      是否为追加标记
     * @throws IOException
     */
    public static void writeSDFile(String fileName, String write_str, boolean flag) throws IOException {

        File file = new File(fileName);

        FileOutputStream fos = new FileOutputStream(file, flag);

        byte[] bytes = write_str.getBytes();

        fos.write(bytes);

        fos.close();
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public static String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 得到俩位小数点的String
     *
     * @param num float参数
     * @return String
     */
    public static String getFloatToString_2(float num) {
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(num);
    }

    /**
     * 得到double
     *
     * @param num String
     * @return
     */
    public static double getDouble(String num) {
        double n;
        try {
            n = Double.parseDouble(num);
        } catch (NumberFormatException e) {
            n = 0;
        }
        return n;
    }


    /**
     * 得到double
     *
     * @param num String
     * @return
     */
    public static long getLong(String num) {
        long n;
        try {
            n = Long.parseLong(num);
        } catch (NumberFormatException e) {
            n = 0;
        }
        return n;
    }

    /**
     * 得到float
     *
     * @param num String
     * @return
     */
    public static float getFloat(String num) {
        float n;
        try {
            n = Float.parseFloat(num);
        } catch (NumberFormatException e) {
            n = 0;
        }
        return n;
    }

    /**
     * 得到int
     *
     * @param num String
     * @return
     */
    public static int getInt(String num) {
        int n;
        try {
            n = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            n = 0;
        }
        return n;
    }

    /**
     * 获取排序后的List
     *
     * @param iterator
     * @param sortord  1 升序， 2降序
     * @return
     */
    public static List<Integer> getSortList(Iterator<String> iterator, final int sortord) {

        List<Integer> sortList = new ArrayList<>();
        while (iterator.hasNext()) {
            int id = Integer.parseInt(iterator.next());
            sortList.add(id);
        }
        Collections.sort(sortList, new Comparator<Integer>() {
            public int compare(Integer arg0, Integer arg1) {
                return sortord == 1 ? arg1.compareTo(arg0) : arg0.compareTo(arg1);
            }
        });

        return sortList;
    }

    /**
     * 设置隐藏标题栏
     *
     * @param activity
     */
    public static void setNoTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置半透明标题栏
     *
     * @param activity
     */
    public static void setTranslucentStatus(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 取消全屏
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获取是否存在NavigationBar
     *
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = context.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            boolean result = realSize.y != size.y;
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 获取虚拟功能键高度
     *
     * @param context
     * @return
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }


    public static void openFlashlight(Camera camera) {
        try {
            camera = Camera.open();
            Camera.Parameters mParameters;
            mParameters = camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(mParameters);
        } catch (Exception e) {
        }

    }

    public static void closeFlashlight(Camera camera) {
        try {
            Camera.Parameters mParameters;
            mParameters = camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(mParameters);
            camera.release();
        } catch (Exception e) {
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);//ACTION_CALL
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}