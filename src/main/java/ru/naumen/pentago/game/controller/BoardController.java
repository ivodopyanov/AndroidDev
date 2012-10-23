/**
 * 
 */
package ru.naumen.pentago.game.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.naumen.pentago.R;
import ru.naumen.pentago.framework.eventbus.EventBus;
import ru.naumen.pentago.game.controller.GameController.RotateDirection;
import ru.naumen.pentago.game.model.Game;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.game.model.Quarter;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * @author ivodopyanov
 * @since 10.10.2012
 * 
 */
public class BoardController
{
    private static final float[] ANGLES = new float[] { 0.0f, 90.0f, -90.0f, 180.0f };
    //Список пар изображений (повернуть по часовой, повернуть против часовой), разбитый по квадрантам
    private static final int[][] IMAGES = new int[][] { new int[] { R.id.ltc, R.id.ltcc },
            new int[] { R.id.rtc, R.id.rtcc }, new int[] { R.id.lbc, R.id.lbcc }, new int[] { R.id.rbc, R.id.rbcc } };
    private static final int[] CORNERS = new int[] { R.id.topleft, R.id.topright, R.id.bottomleft, R.id.bottomright };

    private final Context context;
    private final View layout;

    public BoardController(Context context, View layout)
    {
        this.context = context;
        this.layout = layout;
    }

    public void init(Game game, List<Player> players, EventBus eventBus)
    {
        View corners = layout.findViewById(R.id.corners);
        for (int i = 0; i < 4; i++)
        {
            boolean top = i < 2;
            boolean left = i % 2 == 0;
            Set<RotateImageDescription> images = new HashSet<RotateImageDescription>();
            //@formatter:off
            images.add(new RotateImageDescription(
                    (ImageView)layout.findViewById(IMAGES[i][0]), 
                    R.string.rotateClockwise,
                    RotateDirection.Clockwise));
            
            images.add(new RotateImageDescription(
                    (ImageView)layout.findViewById(IMAGES[i][1]), 
                    R.string.rotateCounterClockwise,
                    RotateDirection.CounterClockwise));
            //@formatter:on
            Quarter area = Quarter.create(left, top);

            CornerController cornerView = (CornerController)corners.findViewById(CORNERS[i]);
            cornerView.init(new CornerViewDescription(area, images, ANGLES[i]), game, eventBus);
            //cornerView.setGravity((top ? Gravity.BOTTOM : Gravity.TOP) | (left ? Gravity.RIGHT : Gravity.LEFT));
            //cornerView.setPadding(left ? 0 : 5, top ? 0 : 5, left ? 5 : 0, top ? 5 : 0);
        }
    }
}