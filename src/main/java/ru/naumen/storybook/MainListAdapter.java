/**
 * 
 */
package ru.naumen.storybook;

import java.util.List;

import ru.naumen.storybook.model.Story;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author ivodopyanov
 * @since 29.10.2012
 * 
 */
public class MainListAdapter extends BaseAdapter
{
    private final Context context;
    private final List<Story> objects;

    public MainListAdapter(Context context, List<Story> objects)
    {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount()
    {
        return objects.size();
    }

    @Override
    public Object getItem(int position)
    {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return null;
    }
}