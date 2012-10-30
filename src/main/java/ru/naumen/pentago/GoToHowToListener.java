/**
 * 
 */
package ru.naumen.pentago;

import ru.naumen.pentago.help.HowToActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author ivodopyanov
 * @since 30.10.2012
 * 
 */
public class GoToHowToListener implements OnClickListener
{
    private final Context context;

    public GoToHowToListener(Context context)
    {
        this.context = context;
    }

    @Override
    public void onClick(View v)
    {
        context.startActivity(new Intent(context, HowToActivity.class));
    }
}