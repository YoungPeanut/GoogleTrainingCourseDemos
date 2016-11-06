package info.ipeanut.googletrainingcoursedemos.graphics;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import info.ipeanut.googletrainingcoursedemos.R;

/**
 * https://medium.com/@manuelvicnt/your-app-icon-as-animatedvectordrawable-92325ba07c8e#.vely1s6cf
 * Thx
 *
 */
public class DrawableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        ImageView logo = (ImageView) findViewById(R.id.logo);
        Drawable b = logo.getDrawable();
        if (b instanceof Animatable){
            ((Animatable) b).start();
        }

    }
}
