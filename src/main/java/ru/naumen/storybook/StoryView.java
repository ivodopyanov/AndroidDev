/**
 * 
 */
package ru.naumen.storybook;

import ru.naumen.storybook.model.Story;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author ivodopyanov
 * @since 29.10.2012
 * 
 */
public class StoryView extends LinearLayout
{
    public StoryView(Context context)
    {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.story, this);
        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        setLayoutParams(lp);
    }

    public void init(Story story)
    {
        TextView title = (TextView)findViewById(R.id.textView1);
        title.setText(story.getTitle());
    }
}