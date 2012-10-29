/**
 * 
 */
package ru.naumen.pentago.help;

import ru.naumen.pentago.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author ivodopyanov
 * @since 26.10.2012
 * 
 */
public class HowToActivity extends Activity
{
    private final class GoToMainMenuListener implements OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howto);
        findViewById(R.id.main).setOnClickListener(new GoToMainMenuListener());
    }
}