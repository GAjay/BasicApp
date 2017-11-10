package com.basic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.basic.R;
import com.basic.utils.SharedPreference;

/**
 * Created by ajay on 14/9/16.
 */
public class SplashScreenActivity extends AppCompatActivity {




    private Handler handler = new Handler();
    /**
     * A screen for splash screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        int deviceWidth = getResources().getDisplayMetrics().widthPixels;

        ImageView splashlogo = (ImageView) findViewById(R.id.splashlogo);


        splashlogo.getLayoutParams().width = (int) (0.80 * deviceWidth);
        splashlogo.getLayoutParams().height = (int) (0.48 * deviceWidth);

        ImageView splashtextimg = (ImageView) findViewById(R.id.splash_text_img);

        splashtextimg.getLayoutParams().width = (int) (0.80 * deviceWidth);
        splashtextimg.getLayoutParams().height = (int) (0.48 * deviceWidth);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(new SharedPreference(getApplicationContext()).getEntryId())) {
                    Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                    i.putExtra("notification", -1);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);


        /*Time out time of splash screen*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Un-registering the handler
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        // removing the allocated memory of handler
            handler = null;
        }
    }
}
