package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.digiscend.apps.browser.BuildConfig;
import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ImageTask;
import com.digiscend.apps.browser.Task.PreloadingTask;
import com.digiscend.apps.browser.Task.ReaderTask;
import com.digiscend.apps.browser.models.Constants;

import java.util.concurrent.ExecutionException;

public class SplashActivity extends AppCompatActivity
{
    AsyncTask task1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash);


    }

    @Override
    protected void onResume()
    {
        super.onResume ();
        try
        {
            String[] url = {getResources ().getString (R.string.api_server)
                        + Constants.API_PROJECTLIST
                        + "?lang=" + getResources ().getString (R.string.api_q_lang)
                        + "&v=" + BuildConfig.VERSION_CODE,

            getResources().getString(R.string.api_server)
                    + Constants.API_MINERALLIST
                    + "?lang=" + getResources().getString(R.string.api_q_lang)
                    + "&v=" + BuildConfig.VERSION_CODE,

            getResources().getString(R.string.api_server)
                    + Constants.API_COUNTRYLIST
            + "?lang=" + getResources().getString(R.string.api_q_lang)
                    + "&v=" + BuildConfig.VERSION_CODE,

            getResources().getString(R.string.api_server)
                    + Constants.API_STAGELIST
                    + "?lang=" + getResources().getString(R.string.api_q_lang)
                    + "&v=" + BuildConfig.VERSION_CODE};
            task1 = new ReaderTask (this);
            task1.execute (url);

        }  catch (Exception e)
        {
            e.printStackTrace ();
        }

        ImageView myImageView= (ImageView)findViewById(R.id.imageView);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.splash);
        myImageView.startAnimation(myFadeInAnimation);
    }
}
