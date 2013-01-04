/**
 * 
 */
package ru.naumen.storycontent;

import java.util.ArrayList;

import ru.naumen.storybook.Constants;
import ru.naumen.storybook.R;
import ru.naumen.storybook.model.Story;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 04.01.2013
 * 
 */
public class StoryContentActivity extends Activity
{
    private ArrayList<Story> stories;
    private int storyNum;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storycontent);
        stories = getIntent().getExtras().getParcelableArrayList(Constants.STORIES);
        storyNum = getIntent().getExtras().getInt(Constants.STORY_NUM);
        setTitle(stories.get(storyNum).getTitle());
        TextView content = (TextView)findViewById(R.id.storyContent);
        content.setText(stories.get(storyNum).getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.prev:
        {
            Intent intent = new Intent(this, StoryContentActivity.class);
            intent.putParcelableArrayListExtra(Constants.STORIES, stories);
            intent.putExtra(Constants.STORY_NUM, storyNum == 0 ? stories.size() - 1 : storyNum - 1);
            startActivity(intent);
            return true;
        }
        case R.id.next:
        {
            Intent intent = new Intent(this, StoryContentActivity.class);
            intent.putParcelableArrayListExtra(Constants.STORIES, stories);
            intent.putExtra(Constants.STORY_NUM, storyNum == stories.size() - 1 ? 0 : storyNum + 1);
            startActivity(intent);
            return true;
        }
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}