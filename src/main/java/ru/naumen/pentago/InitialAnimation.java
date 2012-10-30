/**
 * 
 */
package ru.naumen.pentago;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class InitialAnimation
{
    private static final int ANIMATION_DURATION = 1000;
    private static final int[] ANIMATION_FROM_BOTTOM = new int[] { R.id.textVSComp, R.id.textVSHuman,
            R.id.textBluetooth, R.id.textHowTo };

    private final Activity activity;

    public InitialAnimation(Activity activity)
    {
        this.activity = activity;
    }

    public void start()
    {
        int bottom = activity.findViewById(R.id.main).getHeight();
        int offset = 500;
        for (int viewId : ANIMATION_FROM_BOTTOM)
        {
            View view = activity.findViewById(viewId);
            view.startAnimation(generateInitialAnimation(view, bottom, offset));
            offset += ANIMATION_DURATION;
        }
    }

    private Animation generateInitialAnimation(View view, int startY, int offset)
    {
        int endX = view.getLeft();
        int endY = view.getTop();

        TranslateAnimation result = new TranslateAnimation(0, 0, startY - endY, 0);
        result.setDuration(ANIMATION_DURATION);
        result.setStartOffset(offset);
        return result;
    }
}