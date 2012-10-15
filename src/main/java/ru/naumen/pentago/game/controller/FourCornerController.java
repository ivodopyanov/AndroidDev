/**
 * 
 */
package ru.naumen.pentago.game.controller;

import ru.naumen.pentago.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * @author ivodopyanov
 * @since 10.10.2012
 * 
 */
public class FourCornerController extends RelativeLayout
{
    public FourCornerController(Context context)
    {
        super(context);
        init();
    }

    public FourCornerController(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.foursquare, this, true);
        this.setBackgroundDrawable(getResources().getDrawable(R.drawable.square_big_1024));
    }
}