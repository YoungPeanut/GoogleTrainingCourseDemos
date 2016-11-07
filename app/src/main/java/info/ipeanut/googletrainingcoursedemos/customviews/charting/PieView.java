package info.ipeanut.googletrainingcoursedemos.customviews.charting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal child class that draws the pie chart onto a separate hardware layer
 * when necessary.
 */
public class PieView extends View {
    // Used for SDK < 11
    private float mRotation = 0;
    private Matrix mTransform = new Matrix();
    private PointF mPivot = new PointF();
    private List<Item> mData = new ArrayList<Item>();
    private Paint mPiePaint;

    public PieView(Context context,float mTextHeight) {
        super(context);

        // Set up the paint for the pie slices
        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setStyle(Paint.Style.FILL);
        mPiePaint.setTextSize(mTextHeight);
    }


    public void setData(List<Item> mData) {
        this.mData = mData;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 为了SDK_INT < 11的旋转，rotateTo
        // 通过 Matrix 操作 canvas
        if (Build.VERSION.SDK_INT < 11) {
            mTransform.set(canvas.getMatrix());
            mTransform.preRotate(mRotation, mPivot.x, mPivot.y);
            canvas.setMatrix(mTransform);


//            canvas.rotate(mRotation,mPivot.x,mPivot.y);

        }

        for (Item it : mData) {
            mPiePaint.setShader(it.mShader);//颜色渐变的着色器
            canvas.drawArc(mBounds,
                    360 - it.mEndAngle,//0度是钟表3点方向，起始角度默认顺时针，被360减，说明它是逆时针画的，
                    it.mEndAngle - it.mStartAngle,
                    true, //true表示 弧线与圆点连接闭合
                    mPiePaint);//mPiePaint 是填充的
        }
    }

    /**
     * 这是一个神奇的回调方法啊，这样就拿到了mBounds，。。。太便宜了
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mBounds = new RectF(0, 0, w, h);
    }

    RectF mBounds;

    public void rotateTo(float pieRotation) {
        mRotation = pieRotation;
        if (Build.VERSION.SDK_INT >= 11) {
            setRotation(pieRotation);
        } else {
            invalidate();
        }
    }

    public void setPivot(float x, float y) {
        mPivot.x = x;
        mPivot.y = y;
        if (Build.VERSION.SDK_INT >= 11) {
            setPivotX(x);
            setPivotY(y);
        } else {
            invalidate();
        }
    }
}
