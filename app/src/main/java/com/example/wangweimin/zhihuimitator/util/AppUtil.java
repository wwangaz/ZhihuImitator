package com.example.wangweimin.zhihuimitator.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.wangweimin.zhihuimitator.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URI;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AppUtil {

    public static String getJsonStringValue(JSONObject jsonObject, String key) {
        return getJsonStringValue(jsonObject, key, "");
    }

    public static String getJsonStringValue(JSONObject jsonObject, String key, String defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                String value = jsonObject.getString(key).trim();
                if (value.equals("null")) {
                    value = "";
                }
                return value;
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static int getJsonIntegerValue(JSONObject json, String key) {
        return getJsonIntegerValue(json, key, 0);
    }

    public static int getJsonIntegerValue(JSONObject jsonObject, String key, int defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getInt(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }


    public static Long getJsonLongValue(JSONObject jsonObject, String key, Long defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getLong(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }


    public static boolean getJsonBooleanValue(JSONObject jsonObject, String key, boolean defaultValue) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getBoolean(key);
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return defaultValue;
    }

    public static JSONObject getJsonObject(JSONObject jsonObject, String key) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getJSONObject(key);
            }
        } catch (Exception e) {
            return new JSONObject();
        }
        return new JSONObject();
    }

    public static JSONArray getJsonArray(JSONObject jsonObject, String key) {
        try {
            if (jsonObject != null && jsonObject.has(key)) {
                return jsonObject.getJSONArray(key);
            }
        } catch (Exception e) {
            return new JSONArray();
        }
        return new JSONArray();
    }

    public static void removeDuplicate(ArrayList arrayList) {
        HashSet h = new HashSet(arrayList);
        arrayList.clear();
        arrayList.addAll(h);
    }

    public static JSONArray removeDuplicate(JSONArray jsonArray) {
        HashSet set = new HashSet();
        JSONArray newArray = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Object element = jsonArray.get(i);
                if (!set.contains(element)) {
                    set.add(element);
                    newArray.put(element);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return newArray;
    }

    public static boolean checkEmail(String email) {
        try {
            String check = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMobilePhoneNumber(String number) {
        String regx = "^(13[0-9]|15[0-9]|18[0-9]|14[5|7]|17[0-9])\\d{8}$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

    public static boolean isVinNumber(String str) {
        String regx = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static void showLongMessage(Context mContext, CharSequence text) {
        if (text != null && text.length() > 0) {
            Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
        }
    }

    public static void showShortMessage(Context mContext, CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showShortMessage(Context mContext, int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param filePath
     * @return String
     */
    public static String getFileContent(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuffer sbContent = new StringBuffer();
        String sLine;

        while ((sLine = br.readLine()) != null) {
            String s = sLine + "\n";
            sbContent = sbContent.append(s);
        }

        fis.close();
        isr.close();
        br.close();

        return sbContent.toString();
    }

    public static String TimeStampToString(Long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(timeStamp));
    }

    public static int convertPxToDp(float px) {
        WindowManager wm = (WindowManager) MyApplication.mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float logicalDensity = metrics.density;
        float dp = px / logicalDensity;
        return (int) dp;
    }

    public static int convertDpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, MyApplication.mContext.getResources().getDisplayMetrics());
    }

    public static ProgressDialog showProgress(Activity activity, String hintText, boolean cancelable) {
        Activity mActivity = null;
        if (activity.getParent() != null) {
            mActivity = activity.getParent();
            if (mActivity.getParent() != null) {
                mActivity = mActivity.getParent();
            }
        } else {
            mActivity = activity;
        }
        final Activity finalActivity = mActivity;
        if (finalActivity.isFinishing()) {
            return null;
        }
        ProgressDialog window = ProgressDialog.show(finalActivity, "", hintText);
        window.getWindow().setGravity(Gravity.CENTER);

        window.setCancelable(cancelable);
        return window;
    }

    public static ProgressDialog showProgress(Activity activity) {
        return showProgress(activity, "努力加载中，请稍候...", true);
    }

    public static void dialPhone(Activity activity, String telephone) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + telephone));
            activity.startActivity(callIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        Writer writer = new StringWriter();

        char[] buffer = new char[2048];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        return writer.toString();
    }

    public static String getHtmlTemplate(Context context, String assetsFileName) {
        String html = "";
        try {
            html = convertStreamToString(context.getAssets().open(assetsFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public static Long paraseLong(String str, Long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return defaultValue;
    }

    public static boolean isNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable();
    }


    public static void cancelTask(AsyncTask task) {
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
            task = null;
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, convertDpToPx(reqWidth), convertDpToPx(reqHeight));

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static DisplayMetrics getWindowMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        try {
            metrics = MyApplication.mContext.getResources().getDisplayMetrics();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return metrics;
    }

    public static int getScreenWidth() {
        return getWindowMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return getWindowMetrics().heightPixels;
    }

    public static int getScreenDensity() {
        return getWindowMetrics().densityDpi;
    }

    public static int getImgType() {
        int imgType;
        switch (getScreenDensity()) {
            case DisplayMetrics.DENSITY_XHIGH:
                imgType = 5;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                imgType = 7;
                break;
            default:
                imgType = 1;
                break;
        }
        return imgType;
    }

    public static boolean isAfterHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static void setWallpaper(Context context, Bitmap bitmap) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try {
            /**
             * Change the current system wallpaper to a bitmap. The given bitmap
             * is converted to a PNG and stored as the wallpaper. On success,
             * the intent {@link android.content.Intent#ACTION_WALLPAPER_CHANGED} is broadcast.
             *
             * <p>
             * This method requires the caller to hold the permission
             * {@link android.Manifest.permission#SET_WALLPAPER}.
             *
             * @param bitmap
             *            The bitmap to save.
             *
             * @throws java.io.IOException
             *             If an error occurs reverting to the default
             *             wallpaper.
             */
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            AppUtil.showShortMessage(context, "设置失败");
        }
    }

    /*
     * 让Gallery上能马上看到图片
     */
    public static void scanPhoto(Context context, String imgFileName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static boolean isValidateUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        return !TextUtils.isEmpty(uri.getHost());
    }

    public static String encodeUTF8Str(String param) {
        if (!TextUtils.isEmpty(param)) {
            try {
                param = URLEncoder.encode(param, "utf8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return param;
    }

    public static String getImei(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imte = TelephonyMgr.getDeviceId();
        String pseudoUniqueID = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10 + Build.USER.length() % 10;
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String wlanMACID = wm.getConnectionInfo().getMacAddress();

        String longId = imte + pseudoUniqueID + androidID + wlanMACID;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (m != null) {
            m.update(longId.getBytes(), 0, longId.length());
            byte pMd5Data[] = m.digest();
            String uniqueID = new String();
            for (int i = 0; i < pMd5Data.length; i++) {
                int b = (0xFF & pMd5Data[i]);
                if (b <= 0xF)
                    uniqueID += "0";
                uniqueID += Integer.toHexString(b);
            }
            uniqueID = uniqueID.toUpperCase();
            return uniqueID;
        } else {
            return null;
        }
    }

    public static int getNetworkType(Context context) {
        final int UNKNOWN = -1;
        final int WIFI = 1;
        final int GPRS = 2;
        final int TD_SCDMA = 3;
        final int LTE = 4;

        if (context == null) {
            return UNKNOWN;
        }

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return UNKNOWN;
        }
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return WIFI;
        } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyManager == null) {
                return UNKNOWN;
            }

            int networkType = mTelephonyManager.getNetworkType();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return GPRS;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return TD_SCDMA;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return LTE;
                default:
                    return UNKNOWN;
            }
        }

        return UNKNOWN;
    }

    public static Map<String, String> getRequestParamsMap(String url, String splitChar) {
        Map<String, String> map = new HashMap<>();
        try {
            URI urlObj = new URI(url);
            String queryMapStr = urlObj.getQuery();
            map = getRequestParamsMapByString(queryMapStr, splitChar);
        } catch (Exception e) {
            e.printStackTrace();
            // 不做处理
        }
        return map;
    }

    public static Map<String, String> getRequestParamsMapByString(String queryMapStr, String splitChar) {
        Map<String, String> map = new HashMap<>();
        if (queryMapStr != null) {
            String[] params = queryMapStr.split(splitChar);
            for (String param : params) {
                int firstEqual = param.indexOf("=");
                if (firstEqual > 0) {
                    String name = param.substring(0, firstEqual);
                    String value = param.substring(firstEqual + 1, param.length());
                    map.put(name, value);
                }
            }
        }
        return map;
    }

    public static float getFloatValueFromString(String priceValue) {
        float floatValue = 0;
        if (!TextUtils.isEmpty(priceValue)) {
            try {
                floatValue = Float.valueOf(priceValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return floatValue;
    }

    public static boolean isExistApp(String appName) {
        PackageManager manager = MyApplication.mContext.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase(appName))
                return true;
        }

        return false;
    }

    public static boolean isEmulator() {
        return Build.FINGERPRINT.contains("generic");
    }

    public static String signatureByMD5(String source) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.reset();
            md.update(source.getBytes());

            byte[] mdbytes = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < mdbytes.length; i++) {
                String hex = Integer.toHexString(0xff & mdbytes[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static long getAvailMemory(Activity activity) {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存

        return mi.availMem / 1024 / 1024;// 将获取的内存大小规格化
    }
}
