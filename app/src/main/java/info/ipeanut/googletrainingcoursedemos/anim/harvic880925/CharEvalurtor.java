package info.ipeanut.googletrainingcoursedemos.anim.harvic880925;

import android.animation.TypeEvaluator;

/**
 * Created by chenshao on 16/10/31.
 */
public class CharEvalurtor implements TypeEvaluator<Character> {
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
//        int total = (int)endValue - (int)startValue;
//        int curent = (int) (total * fraction);
//        return (char)curent;


        int startInt  = (int)startValue;
        int endInt = (int)endValue;
        int curInt = (int)(startInt + fraction *(endInt - startInt));
        char result = (char)curInt;
        return result;
    }
}
