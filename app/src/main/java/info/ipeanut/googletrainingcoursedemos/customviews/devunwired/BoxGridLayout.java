package info.ipeanut.googletrainingcoursedemos.customviews.devunwired;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * lays out all its child views with equal spacing in a 3x3 grid
 * 可以嵌套LinearLayout达到同样效果，但是自定义view可以减轻view层级
 *
 * Created by chenshao on 16/11/7.
 */
public class BoxGridLayout extends ViewGroup{


    public BoxGridLayout(Context context) {
        super(context);
    }

    public BoxGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BoxGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
