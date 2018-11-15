package com.moible.bksx.xcb.retrofitmvpdemo.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;

/**设备，dp-px转换类
 * Created by burro on 17-2-23.
 */
public class ControlsUtils {

    private static DisplayMetrics _dm = null;

    private static DisplayMetrics getDm(Activity activity) {
        if (_dm == null) {
            _dm = new DisplayMetrics();
            activity .getWindowManager().getDefaultDisplay().getMetrics(_dm);
        }
        return _dm;
    }

    public static float getDensity(Activity activity) {
        return  getDm(activity).density;
    }

    public static int getScreenWidthPx(Activity activity) {
        return getDm(activity).widthPixels;
    }

    public static int getScreenHeightPx(Activity activity) {
        return getDm(activity).heightPixels;
    }

    public static int getScreenWidthDp(Activity activity) {
        return px2Dp(activity, getDm(activity).widthPixels);
    }

    public static int getScreenHeightDp(Activity activity) {
        return px2Dp(activity, getDm(activity).heightPixels);
    }

    public static int px2Dp(Activity activity, int pxValue) {
        final float scale = getDm(activity).density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2Px(Activity activity, int dpValue) {
        final float scale = getDm(activity).density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getViewHeight(final View view) {
        int _height = -1;
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        _height = view.getMeasuredHeight();
        return _height;
    }

    public static int getViewWidth(final View view) {
        int _width = -1;
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        _width = view.getMeasuredWidth();
        return _width;
    }
}
