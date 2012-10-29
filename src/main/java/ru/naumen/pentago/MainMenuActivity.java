package ru.naumen.pentago;

import java.io.Serializable;
import java.util.List;

import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.GameActivity;
import ru.naumen.pentago.game.model.Player;
import ru.naumen.pentago.help.HowToActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class MainMenuActivity extends Activity
{
    private final class ExitListener implements OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
        }
    }

    private final class GoToGameListener implements OnClickListener
    {
        private final List<Player> players;

        public GoToGameListener(List<Player> players)
        {
            this.players = players;
        }

        @Override
        public void onClick(View v)
        {
            Log.d("menu", "game started");
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra(Constants.PLAYERS_EXTRA, (Serializable)players);
            startActivity(intent);
        }
    }

    private final class GoToHowToListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            startActivity(new Intent(getApplicationContext(), HowToActivity.class));
        }
    }

    private static final int ANIMATION_DURATION = 1000;
    private static final int[] ANIMATION_FROM_BOTTOM = new int[] { R.id.textVSComp, R.id.textVSHuman, R.id.textHowTo,
            R.id.textExit };

    private static boolean firstTime = true;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        findViewById(R.id.textVSHuman).setOnClickListener(new GoToGameListener(Constants.TWO_HUMAN_PLAYERS));
        findViewById(R.id.textVSComp).setOnClickListener(new GoToGameListener(Constants.HUMAN_COMPUTER_PLAYERS));
        findViewById(R.id.textHowTo).setOnClickListener(new GoToHowToListener());
        findViewById(R.id.textExit).setOnClickListener(new ExitListener());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && firstTime)
        {
            firstTime = false;
            int bottom = findViewById(R.id.main).getHeight();
            int offset = 500;
            for (int viewId : ANIMATION_FROM_BOTTOM)
            {
                View view = findViewById(viewId);
                view.startAnimation(generateInitialAnimation(view, bottom, offset));
                offset += ANIMATION_DURATION;
            }
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