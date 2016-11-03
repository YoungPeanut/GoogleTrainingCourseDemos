package info.ipeanut.googletrainingcoursedemos.anim.harvic880925;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import info.ipeanut.googletrainingcoursedemos.R;

/**
 *
 * http://blog.csdn.net/harvic880925/article/details/50995268
 * 哥们牛逼，学习。。。
 *
 * 1 PropertyValuesHolder，利用它可以同时控制多个属性的动画
 * 2 Keyframe
 * 3 常见的：rotation/ScaleX/ScaleY/BackgroundColor
 *   没有的：自己setter方法
 *
 * Created by chenshao on 16/10/28.
 */

public class ObjectAnimatorActivity extends Activity {
    TextView textview;

    MyTextView myTextView;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        textview = (TextView) findViewById(R.id.textview);
        myTextView = (MyTextView) findViewById(R.id.myTextView);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTextViewAnim();
            }
        });

        myTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCharChangingAnim();
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImageAnim();
            }
        });
    }
    /**
     * “PropertyValuesHolder 的 ofInt 和 ofFloat ”
     * 与 ObjectAnimator.ofFloat的参数一样，只是少了Object target
     */
    private void startTextViewAnim(){
        //“对应的是 View 类中 SetRotation(float rotation)函数”
        PropertyValuesHolder rotationVH = PropertyValuesHolder.ofFloat("Rotation",60f,-60f,40f,-40f,-20f,20f,10f,-10f,0);
//        “View 类中的 setBackgroundColor(int color)”
        PropertyValuesHolder colorVH = PropertyValuesHolder.ofInt("BackgroundColor",0xffffffff,0xffff00ff,0xffffff00,0xffffffff);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(textview,rotationVH,colorVH);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();

    }

    /**
     * PropertyValuesHolder
     * 与 ObjectAnimator#ofObject(Object target, String propertyName, TypeEvaluator evaluator, Object... values)
     * 一样，只是少了Object target
     */
    private void startCharChangingAnim(){
        PropertyValuesHolder charHolder = PropertyValuesHolder.ofObject("CharText",new CharEvalurtor(),'A','Z');
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(myTextView,charHolder);
        animator.setDuration(10000)
                .setInterpolator(new LinearInterpolator());
        animator.start();

    }

    /**
     * Keyframe.ofObject
     * Keyframe.ofFloat(float fraction, float value)
     *      //fraction是进度，也就是对应Animator的getInterpolation()返回值，默认的线性插值器（LinearInterpolator）
     *
     *      1 至少要有两个帧才行，否则会IndexOutOfBoundsE
     *      2 如果没有进度0的帧，就从最小进度开始，这是正常的
     *      3 如果没有进度为1的帧，就停在最后一帧
     */
    private void startImageAnim() {
//        Keyframe frame0 = Keyframe.ofObject(0f,'A');
//        Keyframe frame1 = Keyframe.ofObject(0f,'M');
//        Keyframe frame2 = Keyframe.ofObject(0f);
//        frame2.setValue('Z');
//
//        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("CharText",frame0,frame1,frame2);
//        holder.setEvaluator(new CharEvalurtor());//一定要记得
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(myTextView2,holder);
//        animator.setDuration(10000)
//                .setInterpolator(new LinearInterpolator());
//        animator.start();

        Keyframe frame0 = Keyframe.ofFloat(0f, 0);//frame0表示在进度为0的时候，旋转角度为0
        Keyframe frame1 = Keyframe.ofFloat(0.5f, 100f);//frame0表示在进度为0的时候，旋转角度为100 正数为顺时针
        Keyframe frame2 = Keyframe.ofFloat(1);
        frame2.setValue(0f);
        frame2.setInterpolator(new BounceInterpolator()); //上一帧到这，也就是frame1 到 frame2
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(imageView1,frameHolder);
        animator.setDuration(3000);
        animator.start();

    }

    void Argb (){
        ValueAnimator animator = ValueAnimator.ofInt(0xffffff00,0xff0000ff);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(3000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int)animation.getAnimatedValue();
//                tv.setBackgroundColor(curValue);

            }
        });

        animator.start();
    }

}

