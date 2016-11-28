package info.ipeanut.googletrainingcoursedemos.customviews.harvic880925;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import info.ipeanut.googletrainingcoursedemos.R;

/**
 * 印章工具
 * 18 http://blog.csdn.net/harvic880925/article/details/52039081
 *
 * Created by chenshao on 16/11/24.
 */
public class TelescopeView extends View {
    private Paint mPaint;
    private Bitmap mBitmap,mBitmapBG;
    private int mDx = -1, mDy = -1;
    public TelescopeView(Context context) {
        super(context);
        init();
    }

    public TelescopeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TelescopeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scenery);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();

                postInvalidate();

                return true;
            case MotionEvent.ACTION_MOVE:
//                ViewConfiguration.getTouchSlop();

                mDx = (int) event.getX();
                mDy = (int) event.getY();

                break;
            case MotionEvent.ACTION_UP:
                mDx = mDy = -1;
                break;
        }
        postInvalidate();

        return super.onTouchEvent(event);
    }
    BitmapShader shader;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //一层新画布，没有显示
        if (null == mBitmapBG){
            mBitmapBG = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            Canvas bgCanvas = new Canvas(mBitmapBG);
            bgCanvas.drawBitmap(mBitmap,0,0,mPaint);
            shader = new BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }

        canvas.save();

        //view的画布canvas，显示
        if (mDx != -1 && mDy != -1){
            mPaint.setShader(shader);
            canvas.drawCircle(mDx,mDy,150,mPaint);
            return;
        }
        canvas.restore();


    }
}
