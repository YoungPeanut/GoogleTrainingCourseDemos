package info.ipeanut.googletrainingcoursedemos.customviews.utils;

import android.content.Context;

/**
 *
 * 密度工具
 * Created by chenshao on 16/11/4.
 */
public final class DensityUtils {

    /**
     *
     * @param context
     * @return 屏幕像素密度
     */
    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }


    public static float getFontDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }


    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }


    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * getFontDensity(context) + 0.5);
    }


    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / getFontDensity(context) + 0.5);
    }

}
