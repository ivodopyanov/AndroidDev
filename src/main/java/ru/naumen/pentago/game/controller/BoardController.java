/**
 * 
 */
package ru.naumen.pentago.game.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.ChildLayoutDescriptor;
import ru.naumen.pentago.game.model.Game;
import ru.naumen.pentago.game.model.Quarter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author ivodopyanov
 * @since 10.10.2012
 * 
 */
public class BoardController extends ViewGroup
{
    private static final Map<Integer, ChildLayoutDescriptor> LAYOUT_DESCS;

    static
    {
        LAYOUT_DESCS = new HashMap<Integer, ChildLayoutDescriptor>();
        LAYOUT_DESCS.put(R.id.ltc, new ChildLayoutDescriptor(0.3f, 0.1f, 0.15f, 0));
        LAYOUT_DESCS.put(R.id.rtcc, new ChildLayoutDescriptor(0.3f, 0.1f, 0.55f, 0));
        LAYOUT_DESCS.put(R.id.lbcc, new ChildLayoutDescriptor(0.3f, 0.1f, 0.15f, 0.9f));
        LAYOUT_DESCS.put(R.id.rbc, new ChildLayoutDescriptor(0.3f, 0.1f, 0.55f, 0.9f));

        LAYOUT_DESCS.put(R.id.ltcc, new ChildLayoutDescriptor(0.1f, 0.3f, 0, 0.15f));
        LAYOUT_DESCS.put(R.id.rtc, new ChildLayoutDescriptor(0.1f, 0.3f, 0.9f, 0.15f));
        LAYOUT_DESCS.put(R.id.lbc, new ChildLayoutDescriptor(0.1f, 0.3f, 0, 0.55f));
        LAYOUT_DESCS.put(R.id.rbcc, new ChildLayoutDescriptor(0.1f, 0.3f, 0.9f, 0.55f));

        LAYOUT_DESCS.put(R.id.corners, new ChildLayoutDescriptor(0.8f, 0.8f, 0.1f, 0.1f));
    }

    private static final float SIZE_PERCENT = 1.00f;
    private static final float[] ANGLES = new float[] { 0.0f, 90.0f, -90.0f, 180.0f };
    //Список пар изображений (повернуть по часовой, повернуть против часовой), разбитый по квадрантам
    private static final int[][] IMAGES = new int[][] { new int[] { R.id.ltc, R.id.ltcc },
            new int[] { R.id.rtc, R.id.rtcc }, new int[] { R.id.lbc, R.id.lbcc }, new int[] { R.id.rbc, R.id.rbcc } };
    private static final int[] CORNERS = new int[] { R.id.topleft, R.id.topright, R.id.bottomleft, R.id.bottomright };

    public BoardController(Context context)
    {
        super(context);
        initLayout();
    }

    public BoardController(Context context, AttributeSet attrSet)
    {
        super(context, attrSet);
        initLayout();
    }

    public void init(Game game)
    {
        View corners = findViewById(R.id.corners);
        for (int i = 0; i < 4; i++)
        {
            boolean top = i < 2;
            boolean left = i % 2 == 0;
            Set<RotateImageDescription> images = new HashSet<RotateImageDescription>();
            //@formatter:off
            images.add(new RotateImageDescription(
                    (ImageView)findViewById(IMAGES[i][0]), 
                    R.string.rotateClockwise,
                    true));
            
            images.add(new RotateImageDescription(
                    (ImageView)findViewById(IMAGES[i][1]), 
                    R.string.rotateCounterClockwise,
                    false));
            //@formatter:on
            Quarter area = Quarter.create(left, top);

            CornerController cornerView = (CornerController)corners.findViewById(CORNERS[i]);
            cornerView.init(new CornerViewDescription(area, images, ANGLES[i]), game);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int w = r - l;
        int h = b - t;
        int size = Math.min(w, h);
        int deltax = (w - size) / 2;
        int deltay = (h - size) / 2;
        for (Entry<Integer, ChildLayoutDescriptor> entry : LAYOUT_DESCS.entrySet())
        {
            View view = findViewById(entry.getKey());
            int childl = deltax + (int)(size * entry.getValue().getLeftPercent());
            int childr = childl + (int)(size * entry.getValue().getWidthPercent());
            int childt = deltay + (int)(size * entry.getValue().getTopPercent());
            int childb = childt + (int)(size * entry.getValue().getHeightPercent());
            view.layout(childl, childt, childr, childb);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        size = (int)(size * SIZE_PERCENT);
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
        LayoutInflater.from(getContext()).inflate(R.layout.boardwitharrows, this);
    }
}