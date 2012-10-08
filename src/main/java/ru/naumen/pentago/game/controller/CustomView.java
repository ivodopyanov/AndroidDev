/**
 * 
 */
package ru.naumen.pentago.game.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author ivodopyanov
 * @since 05.09.2012
 * 
 */
public class CustomView extends ImageView
{
    private float angle = 0;
    private boolean flip = false;

    public CustomView(Context context)
    {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setAngle(float angle)
    {
        this.angle = angle;
    }

    public void setFlip(boolean flip)
    {
        this.flip = flip;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();
        canvas.rotate(angle, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        if (flip)
        {
            canvas.scale(-1, 1);
            canvas.translate(-getMeasuredWidth(), 0);
        }
        super.onDraw(canvas);
        canvas.restore();
    }
}