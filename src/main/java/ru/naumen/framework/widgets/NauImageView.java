/**
 * 
 */
package ru.naumen.framework.widgets;

import ru.naumen.storybook.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author ivodopyanov
 * @since 26.10.2012
 * 
 */
public class NauImageView extends ImageView
{

    public NauImageView(Context context)
    {
        super(context);
    }

    public NauImageView(Context context, AttributeSet attrSet)
    {
        super(context, attrSet);

        TypedArray ta = context.obtainStyledAttributes(attrSet, R.styleable.NauImageView, 0, 0);

        try
        {
            boolean flip = ta.getBoolean(R.styleable.NauImageView_flip, false);
            int angle = ta.getInteger(R.styleable.NauImageView_angle, 0);
            int drawable = ta.getResourceId(R.styleable.NauImageView_source, 0);
            if (drawable != 0)
            {
                Matrix matrix = new Matrix();
                matrix.setRotate(angle);
                if (flip)
                    matrix.postScale(-1, 1);
                Bitmap source = BitmapFactory.decodeResource(getResources(), drawable);
                Bitmap updated = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
                setImageBitmap(updated);
            }
        }
        finally
        {
            ta.recycle();
        }
    }
}