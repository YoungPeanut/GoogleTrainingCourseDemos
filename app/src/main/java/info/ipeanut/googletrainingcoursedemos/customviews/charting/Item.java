package info.ipeanut.googletrainingcoursedemos.customviews.charting;


import android.graphics.Shader;

/**
 * Maintains the state for a data item.
 */
public class Item {
    public String mLabel;
    public float mValue;
    public int mColor;

    // computed values
    public int mStartAngle;
    public int mEndAngle;

    public int mHighlight;
    public Shader mShader;
}
