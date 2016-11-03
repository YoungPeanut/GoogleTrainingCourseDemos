http://blog.csdn.net/harvic880925/article/details/50995268

“PropertyValuesHolder 与 Keyframe”
创建ObjectAnimator/ValueAnimator实例的方式
* ofInt()
* ofFloat()
* ofObject()
* ofPropertyValuesHolder()

PropertyValuesHolder
ofFloat()的内部实现其实就是将传进来的参数封装成 PropertyValuesHolder 实例来保存动画状态。
```
ObjectAnimator的：
public static ObjectAnimator ofPropertyValuesHolder(Object target
        ,PropertyValuesHolder... values)
```
values是可变长参数，每个 PropertyValuesHolder 实例都会针对一个属性，所以传进去多个实例，
将会对控件的多个属性同时做动画。

Keyframe
一个关键帧必须包含两个原素，第一时间点(进度fraction)，第二位置(值value)。


插值器，也叫加速器，物理中的加速度，表示进度[0,1]与时间的函数关系。
定义一个插值器：实现TimeInterpolator接口
public float getInterpolation(float input)
／／input：一个0到1之间的值，表示动画的进度，就是随着时间的流逝。
／／返回值：表示当前实际你想要显示的进度，注意，还是进度，是你处理过的进度。取值可以超过1也可以小于0，超过1表示已经超过目标值，小于0表示小于开始位置。



Evaluator,估值器: Evaluator就是将从加速器返回的数字进度转成对应的数字值。
图片：/Users/chenshaosina/dev/android/young/GoogleTrainingCourseDemos/screenshots/20160120104933467.png
[!http://img.blog.csdn.net/20160120104933467?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast]

比如：ValueAnimator anim = ValueAnimator.ofInt(100, 400);
当前的值 = 100 + （400 - 100）* 处理过的进度

定义一个估值器： implements TypeEvaluator<T>
public Integer evaluate(float fraction, Integer startValue, Integer endValue)
／／fraction就是加速器中的返回值，表示当前动画的数值进度，百分制的小数表示。
／／startValue和endValue分别对应ofInt(int start,int end)中的start和end的数值；




















