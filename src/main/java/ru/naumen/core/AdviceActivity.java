package ru.naumen.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AdviceActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advice);
        int index = getIntent().getIntExtra("index", 0);
        setTitle(getResources().getStringArray(R.array.adviceCaptions)[index]);
        String[] advices = getResources().getStringArray(R.array.adviceDescs);
        setText(advices, index);
        setGoToPrevAdvice(advices, index);
        setGoToNextAdvice(advices, index);
        setGoToContents();
    }

    private void setGoToContents()
    {
        findViewById(R.id.imageButton2).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AdviceActivity.this, ContentsActivity.class));
            }
        });
    }

    private void setGoToNextAdvice(String[] advices, int index)
    {
        int nextIndex = index + 1;
        if (index == advices.length - 1)
        {
            nextIndex = 0;
        }
        findViewById(R.id.imageButton3).setOnClickListener(new GoToAdviceClickListener(nextIndex, this));
    }

    private void setGoToPrevAdvice(String[] advices, int index)
    {
        int prevIndex = index - 1;
        if (index == 0)
        {
            prevIndex = advices.length - 1;
        }
        findViewById(R.id.imageButton1).setOnClickListener(new GoToAdviceClickListener(prevIndex, this));
    }

    private void setText(String[] advices, int index)
    {
        TextView textView = (TextView)findViewById(R.id.textView1);
        textView.setText(advices[index]);
    }
}
