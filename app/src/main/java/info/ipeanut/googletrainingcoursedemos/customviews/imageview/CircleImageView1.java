package info.ipeanut.googletrainingcoursedemos.customviews.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import info.ipeanut.googletrainingcoursedemos.R;

/**
 * Paint.setXfermode
 *
 * D for Destination, S for Source
 *
 * mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
 * http://blog.csdn.net/harvic880925/article/details/51264653
 *
 * Created by chenshao on 16/11/16.
 *
 * https://github.com/MostafaGazar/CustomShapeImageView
 *
 */
public class CircleImageView1 extends ImageView {

    private int width = 0;
    private int height = 0;
    private Bitmap circledBitmap = null;

    private boolean isDrawBorder;
    private int borderColor;
    private int borderWidth;
    private Paint mBorderPaint;
    private RectF borderRect,borderRect1;

    public CircleImageView1(Context context) {
        this(context,null);
    }

    public CircleImageView1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    void init(Context context, AttributeSet attrs, int defStyleAttr){

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView1);
        isDrawBorder = array.getBoolean(R.styleable.CircleImageView1_drawBorder, true);
        borderColor = array.getColor(R.styleable.CircleImageView1_borderColor, Color.GRAY);
        //xml里定义的是dp,这里borderWidth是px,说明getDimension()转了
        borderWidth = (int) (array.getDimension(R.styleable.CircleImageView1_borderWidth,0)+.5f);
        array.recycle();

        if (isDrawBorder){
            mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            borderRect = new RectF();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (null != circledBitmap && !circledBitmap.isRecycled()){
            circledBitmap.recycle();
            circledBitmap = null;
        }

    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        onImageSrcChanged();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        onImageSrcChanged();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        onImageSrcChanged();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        if (null == circledBitmap || w !=oldw){
            borderRect = new RectF(0,0,w,h);
            borderRect1 = new RectF(0+borderWidth,0+borderWidth,w-borderWidth,h-borderWidth);

            circledBitmap = getCircledBitmap(initOriginBitmap());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);你别动，我来处理android:src

//        int layerId = canvas.saveLayer(0,0,width,height,paint,Canvas.ALL_SAVE_FLAG);

        if (null != circledBitmap){

            if (isDrawBorder && mBorderPaint!=null){

                mBorderPaint.setColor(borderColor);
                canvas.drawOval(borderRect,mBorderPaint);
                mBorderPaint.setColor(Color.RED);
                mBorderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                canvas.drawOval(borderRect1,mBorderPaint);

            }

            canvas.drawBitmap(circledBitmap,0,0,null);

        }

//        paint.setXfermode(null);
//        canvas.restoreToCount(layerId);

    }

    private Bitmap initOriginBitmap(){

        return (null != getDrawable()) ? ((BitmapDrawable) getDrawable()).getBitmap() : null;

    }

    private Bitmap getCircledBitmap(Bitmap origin){
        if (null == origin || width < 1 || height < 1)
            return null;

        int canvasWidth = 0;
        int canvasHeight = 0;
        int borderw = 0;
        if (isDrawBorder){
            borderw = borderWidth;
            canvasWidth = width - borderw * 2;
            canvasHeight = height - borderw * 2;
        }

        if (canvasWidth < 1 || canvasHeight < 1)
            return null;

        //画笔
        Paint paint = new Paint();

        //画布
        Bitmap blank = Bitmap.createBitmap(canvasWidth,canvasHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blank);
        canvas.drawARGB(0, 255, 255, 255);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(origin,canvasWidth,canvasHeight,false);

        RectF rect = new RectF(borderw,borderw,canvasWidth + borderw,canvasHeight + borderw);
        canvas.drawOval(rect,paint);//画圆 D
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//SRC_IN: D的形状，S的内容
        canvas.drawBitmap(scaledBitmap,rect.left + borderw,rect.top + borderw,paint);// S

        return blank;

    }

    private void onImageSrcChanged(){
        circledBitmap = getCircledBitmap(initOriginBitmap());

        invalidate();
    }

}
