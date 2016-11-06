package info.ipeanut.googletrainingcoursedemos.customviews.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chenshao on 16/11/4.
 */
public final class ViewUtils {


    /**
     * set margin left
     *
     * @param view the view
     * @param left value of margin left
     */
    public static void setMarginLeft(View view, int left) {
        setMargins(view, left, 0, 0, 0);
    }

    /**
     * set margin top
     *
     * @param view the view
     * @param top  value of margin top
     */
    public static void setMarginTop(View view, int top) {
        setMargins(view, 0, top, 0, 0);
    }

    /**
     * set margin right
     *
     * @param view  the view
     * @param right value of margin right
     */
    public static void setMarginRight(View view, int right) {
        setMargins(view, 0, 0, right, 0);
    }

    /**
     * set margin bottom
     *
     * @param view   the view
     * @param bottom value of margin bottom
     */
    public static void setMarginBottom(View view, int bottom) {
        setMargins(view, 0, 0, 0, bottom);
    }

    /**
     * set view margin
     *
     * @param view   the view
     * @param left   value of margin left
     * @param top    value of margin top
     * @param right  value of margin right
     * @param bottom value of margin bottom
     */
    public static void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();       //请求重绘
        }
    }
}
