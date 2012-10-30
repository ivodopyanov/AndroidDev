package ru.naumen.storybook;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ru.naumen.storybook.model.Story;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity
{

    private static String TAG = "core";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        StoryLoader storyLoader = new StoryLoader(getApplicationContext());
        List<Story> stories = new LinkedList<Story>();
        try
        {
            stories = storyLoader.load("stories.xml");
        }
        catch (IOException e)
        {
            Log.d(TAG, e.getLocalizedMessage());
            e.printStackTrace();
        }
        catch (ParserConfigurationException e)
        {
            Log.d(TAG, e.getLocalizedMessage());
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            Log.d(TAG, e.getLocalizedMessage());
            e.printStackTrace();
        }
        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(new MainListAdapter(getApplicationContext(), stories));
    }
}