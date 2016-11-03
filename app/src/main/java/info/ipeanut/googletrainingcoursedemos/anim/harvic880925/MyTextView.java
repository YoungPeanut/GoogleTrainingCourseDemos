package info.ipeanut.googletrainingcoursedemos.anim.harvic880925;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by chenshao on 16/10/31.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 为了用属性动画
     */
    public void setCharText(Character text) {
        setText(String.valueOf(text));
    }

}
