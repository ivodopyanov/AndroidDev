package ru.naumen.core;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class GoToAdviceClickListener implements OnClickListener
{
    private final int pos;
    private final Activity currentActivity;

    public GoToAdviceClickListener(int pos, Activity currentActivity)
    {
        this.pos = pos;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(currentActivity, AdviceActivity.class);
        intent.putExtra("index", pos);
        currentActivity.startActivity(intent);
    }
}
