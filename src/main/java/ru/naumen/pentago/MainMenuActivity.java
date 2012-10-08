package ru.naumen.pentago;

import java.io.Serializable;
import java.util.List;

import ru.naumen.pentago.game.BoardActivity;
import ru.naumen.pentago.game.Constants;
import ru.naumen.pentago.game.model.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainMenuActivity extends Activity
{
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
            Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
            intent.putExtra(Constants.PLAYERS_EXTRA, (Serializable)players);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.textView1).setOnClickListener(new GoToGameListener(Constants.TWO_HUMAN_PLAYERS));
        findViewById(R.id.TextView01).setOnClickListener(new GoToGameListener(Constants.HUMAN_COMPUTER_PLAYERS));
    }

}
