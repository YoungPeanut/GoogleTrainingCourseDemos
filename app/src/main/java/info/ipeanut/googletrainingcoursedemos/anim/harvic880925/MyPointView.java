package info.ipeanut.googletrainingcoursedemos.anim.harvic880925;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by chenshao on 16/11/6.
 */
public class MyPointView extends View {

    Paint paint;
    Point point;
    int ww,hh;

    public MyPointView(Context context) {
        super(context);
        init();

    }

    public MyPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MyPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        point = new Point();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ww = w;
        hh = h;
        startAnim();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(ww/2,hh/2,point.getRadius(),paint);

    }

    private void startAnim(){
        ValueAnimator animator = ValueAnimator.ofInt(0,ww/2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();

                point.setRadius(value);

                invalidate();


            }
        });

        animator.setDuration(300);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();

    }

    public class Point{
        private int radius;

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }
    }
}
