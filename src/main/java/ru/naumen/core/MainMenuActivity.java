package ru.naumen.core;

import ru.naumen.core.game.BoardActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainMenuActivity extends Activity
{
    private final OnClickListener GO_TO_GAME_LISTENER = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            startActivity(new Intent(getApplicationContext(), BoardActivity.class));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.textView1).setOnClickListener(GO_TO_GAME_LISTENER);
    }

}
