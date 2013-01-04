package ru.naumen.storybook;

import java.util.ArrayList;

import ru.naumen.storybook.model.Story;
import ru.naumen.storycontent.StoryContentActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity
{
    private class StoryClickListener implements OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(MainActivity.this, StoryContentActivity.class);
            intent.putParcelableArrayListExtra(Constants.STORIES, stories);
            intent.putExtra(Constants.STORY_NUM, position);
            startActivity(intent);
        }
    }

    private static String TAG = "core";
    private ArrayList<Story> stories;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        stories = loadStories();
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(new MainListAdapter(getApplicationContext(), stories));
        list.setOnItemClickListener(new StoryClickListener());
    }

    private ArrayList<Story> loadStories()
    {
        String[] titles = getResources().getStringArray(R.array.titles);
        String[] contents = getResources().getStringArray(R.array.contents);
        int size = Math.min(titles.length, contents.length);
        ArrayList<Story> result = new ArrayList<Story>(size);
        for (int i = 0; i < size; i++)
        {
            result.add(new Story(contents[i], titles[i]));
        }
        return result;
    }
}