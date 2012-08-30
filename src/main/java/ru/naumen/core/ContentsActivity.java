package ru.naumen.core;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class ContentsActivity extends ListActivity
{
    private static String TAG = "core";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        //setContentView(R.layout.main);

        String[] titles = getResources().getStringArray(R.array.adviceCaptions);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.main, titles));
        getListView().setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(ContentsActivity.this, AdviceActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });
    }
}