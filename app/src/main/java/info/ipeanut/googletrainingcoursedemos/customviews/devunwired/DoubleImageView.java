package info.ipeanut.googletrainingcoursedemos.customviews.devunwired;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import info.ipeanut.googletrainingcoursedemos.R;

/**
 * https://newcircle.com/s/post/1663/tutorial_enhancing_android_ui_with_custom_views_dave_smith_video
 *https://github.com/devunwired/custom-view-examples
 * Thx
 * 展示一对logo和比分
 *
 * 属性较多时，可以用builder模式最后构建的时候调一把updateContentBounds
 *
 * Created by chenshao on 16/11/7.
 */
public class DoubleImageView extends View {

    /* Image Contents */
    private Drawable mLeftDrawable, mRightDrawable;

    /* Text Contents */
    private CharSequence mText;
    private StaticLayout mTextLayout;

    /* Text Drawing */
//    private TextPaint mTextPaint;
    private Point mTextOrigin;
    private int mSpacing;


    public DoubleImageView(Context context) {
        this(context,null);
    }

    public DoubleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DoubleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TextPaint  mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextOrigin = new Point(0,0);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DoubleImageView,0,defStyleAttr);

        Drawable d = array.getDrawable(R.styleable.DoubleImageView_android_drawableLeft);
        if (null != d){
            setLeftDrawable(d);
        }

        d = array.getDrawable(R.styleable.DoubleImageView_android_drawableRight);
        if (null != d){
            setRightDrawable(d);
        }

        CharSequence text = array.getText(R.styleable.DoubleImageView_android_text);
        if (TextUtils.isEmpty(text)){
            text = "";
        }
        setText(text);
        int color = array.getColor(R.styleable.DoubleImageView_android_textColor,0);
        mTextPaint.setColor(color);
        int size = array.getDimensionPixelSize(R.styleable.DoubleImageView_android_textSize,10);
        mTextPaint.setTextSize(size);

        int s = array.getDimensionPixelSize(R.styleable.DoubleImageView_android_spacing,0);
        setSpacing(s);

        array.recycle();

        float textWidth = mTextPaint.measureText(text,0,text.length());
        mTextLayout = new StaticLayout(mText,mTextPaint, (int) textWidth, Layout.Alignment.ALIGN_CENTER,1f,0f,true);

    }

    public void setLeftDrawable(Drawable mLeftDrawable) {
        this.mLeftDrawable = mLeftDrawable;
        updateContentBounds();

    }

    public void setRightDrawable(Drawable mRightDrawable) {
        this.mRightDrawable = mRightDrawable;
        updateContentBounds();

    }

    public void setText(CharSequence text) {
        if (!TextUtils.equals(this.mText,text)){
            this.mText = text;
            updateContentBounds();
        }

    }

    public void setSpacing(int mSpacing) {
        this.mSpacing = mSpacing;
        updateContentBounds();

    }

    private void updateContentBounds(){

        int left = (getWidth() - getDesiredWidth()) / 2;
        int top = (getHeight() - getDesiredHeight()) / 2;

        if (null != mLeftDrawable){
            mLeftDrawable.setBounds(left,top,left + mLeftDrawable.getIntrinsicWidth(),top+mLeftDrawable.getIntrinsicHeight());

            left += (mLeftDrawable.getIntrinsicWidth() * 0.33f);
            top += (mLeftDrawable.getIntrinsicHeight() * 0.33f);
        }

        if (null != mRightDrawable){
            mRightDrawable.setBounds(left,top,left + mRightDrawable.getIntrinsicWidth(),top+mRightDrawable.getIntrinsicHeight());
            left = mRightDrawable.getBounds().right + mSpacing;
        }

        top = (int) ((getHeight() - mTextLayout.getHeight())*0.5);
        mTextOrigin.set(left,top);

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//空方法
        int widthSize = View.resolveSize(getDesiredWidth(), widthMeasureSpec);
        int heightSize = View.resolveSize(getDesiredHeight(),heightMeasureSpec);

        setMeasuredDimension(widthSize,heightSize);
    }

    public int getDesiredWidth() {

        int leftw = mLeftDrawable!=null? mLeftDrawable.getIntrinsicWidth():0;
        int rightw = mRightDrawable!=null? mRightDrawable.getIntrinsicWidth():0;

        int textw = mTextLayout.getWidth();

        return (int) (leftw*0.33 + rightw +textw);
    }

    public int getDesiredHeight() {

        int leftH = mLeftDrawable!=null? mLeftDrawable.getIntrinsicHeight():0;
        int rightH = mRightDrawable!=null? mRightDrawable.getIntrinsicHeight():0;

        return (int) (leftH*0.33 + rightH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh){
            updateContentBounds();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (null != mLeftDrawable){
            mLeftDrawable.draw(canvas);
        }

        if (null != mRightDrawable){
            mRightDrawable.draw(canvas);
        }

        if (null != mTextLayout){
            canvas.save();
            canvas.translate(mTextOrigin.x,mTextOrigin.y);//改变画布的左上角坐标
            mTextLayout.draw(canvas);
            canvas.restore();

        }

    }




    /*
    减轻view hierarchy
Custom View
1 Measurement
measure动作从root view开始，传递到每个child view，framework回调onMeasure()
目的：必须把测量结果保存到setMeasuredDimension()
MeasureSpec：一个代表尺寸的int值，高位是mode，地位是size
mode有三种：UNSPECIFIED：view想多大多大  AT_MOST：view最大不超过给定的spec  EXACTLY：view大小就是给定的值
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //Get the width measurement
    int widthSize = View.resolveSize(getDesiredWidth(), widthMeasureSpec);

    //Get the height measurement
    int heightSize = View.resolveSize(getDesiredHeight(), heightMeasureSpec);

    //MUST call this to store the measurements
    setMeasuredDimension(widthSize, heightSize);
}
TIP: Favor efficiency over flexibility! 你的自定义view不用像framework widgets那么大而全，只有满足app需求即可。

2 Drawing
系统通过onDraw()给了你一个空白的Canvas画布，大小就是刚才的测量结果大小。可以draw shapes, colors, text, bitmaps, and more.
有些系统组件比如，Drawable images and text Layouts，自己提供了draw() 方法，直接把画布给它，它的content就画到你的view上了。

3 Custom Attributes
声明<declare-styleable/>，定义属性类型，有的attr可以使用系统自带的，然后就可以在xml里使用这些属性了。
view初始化的时候通过obtainStyledAttributes()得到属性值。返回一个TypedArrays，使用完记得回收。

Custom ViewGroup
1 Measurement
ViewGroups可以用getDefaultSize() 来测量自己，测量结果保存到setMeasuredDimension()，
然后通过measureChildren()把构造的MeasureSpec传递给子view，通知它进行测量
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthSize, heightSize;

    //Get the width based on the measure specs
    widthSize = getDefaultSize(0, widthMeasureSpec);

    //Get the height based on measure specs
    heightSize = getDefaultSize(0, heightMeasureSpec);

    int majorDimension = Math.min(widthSize, heightSize);
    //Measure all child views
    int blockDimension = majorDimension / mColumnCount;
    int blockSpec = MeasureSpec.makeMeasureSpec(blockDimension,
            MeasureSpec.EXACTLY);
    measureChildren(blockSpec, blockSpec);

    //MUST call this to save our own dimensions
    setMeasuredDimension(majorDimension, majorDimension);
}

2 Layout
ViewGroup在onLayout()的时候给设置子view的BOUNDS，调用child.layout(left,top,left + child.getMeasuredWidth(),top + child.getMeasuredHeight());

3 Drawing
ViewGroup的draw 有两种场景：
第一，在dispatchDraw()方法里绘制，
这个时候，子view已经绘制完毕，ViewGroup在上面绘制一些额外的装饰，比如分割线。
第二，在onDraw()里绘制，
这时，子view还没绘制，所以这里ViewGroup绘制的内容将来会在子view的下面，可以用来画背景或选中状态。
注意：要在在onDraw()里绘制，必须setWillNotDraw(false)，因为ViewGroup默认是WillNotDraw的。

4 Custom Attributes
<declare-styleable name="BoxGridLayout">
        <attr name="separatorWidth" format="dimension" />
        <attr name="separatorColor" format="color" />
        <attr name="numColumns" format="integer" />
    </declare-styleable>


一般引起invalidate()操作的函数如下：

1、直接调用invalidate()方法，请求重新draw()，但只会绘制调用者本身。

2、setSelection()方法 ：请求重新draw()，但只会绘制调用者本身。

3、setVisibility()方法 ： 当View可视状态在INVISIBLE转换VISIBLE时，会间接调用invalidate()方法，继而绘制该View。

4 、setEnabled()方法 ： 请求重新draw()，但不会重新绘制任何视图包括该调用者本身。

requestLayout()方法 ：会导致调用measure()过程 和 layout()过程 。
requestFocus()   :请求View树的draw()过程，但只绘制“需要重绘”的视图。

     */
}
