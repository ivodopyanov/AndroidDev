/**
 * 
 */
package ru.naumen.pentago.game.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.ChildLayoutDescriptor;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author ivodopyanov
 * @since 25.10.2012
 * 
 */
public class FourSquareController extends ViewGroup
{
    private static final Map<Integer, ChildLayoutDescriptor> LAYOUT_DESCS;

    static
    {
        LAYOUT_DESCS = new HashMap<Integer, ChildLayoutDescriptor>();
        LAYOUT_DESCS.put(R.id.topleft, new ChildLayoutDescriptor(0.45f, 0.45f, 0.04f, 0.04f));
        LAYOUT_DESCS.put(R.id.topright, new ChildLayoutDescriptor(0.45f, 0.45f, 0.51f, 0.04f));
        LAYOUT_DESCS.put(R.id.bottomleft, new ChildLayoutDescriptor(0.45f, 0.45f, 0.04f, 0.51f));
        LAYOUT_DESCS.put(R.id.bottomright, new ChildLayoutDescriptor(0.45f, 0.45f, 0.51f, 0.51f));
    }

    public FourSquareController(Context context)
    {
        super(context);
        initLayout();
    }

    public FourSquareController(Context context, AttributeSet attrSet)
    {
        super(context, attrSet);
        initLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int w = r - l;
        int h = b - t;
        int size = Math.min(w, h);
        for (Entry<Integer, ChildLayoutDescriptor> entry : LAYOUT_DESCS.entrySet())
        {
            View view = findViewById(entry.getKey());
            int childl = (int)(size * entry.getValue().getLeftPercent());
            int childr = childl + (int)(size * entry.getValue().getWidthPercent());
            int childt = (int)(size * entry.getValue().getTopPercent());
            int childb = childt + (int)(size * entry.getValue().getHeightPercent());
            view.layout(childl, childt, childr, childb);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(size, size);
        for (Entry<Integer, ChildLayoutDescriptor> entry : LAYOUT_DESCS.entrySet())
        {
            View view = findViewById(entry.getKey());
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int)(size * entry.getValue().getWidthPercent()),
                    MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec((int)(size * entry.getValue().getHeightPercent()),
                    MeasureSpec.EXACTLY);
            view.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    private void initLayout()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.foursquare, this);
        setBackgroundDrawable(getResources().getDrawable(R.drawable.bigsquare));
    }
}