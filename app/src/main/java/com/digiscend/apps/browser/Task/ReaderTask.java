package com.digiscend.apps.browser.Task;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.digiscend.apps.browser.Activity.HomeActivity;
import com.digiscend.apps.browser.Activity.SplashActivity;
import com.digiscend.apps.browser.models.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vikas on 03/07/16.
 */
public class ReaderTask extends AsyncTask<String, Void, String>
{

    private Exception exception;
    private SplashActivity caller=null;

    public ReaderTask(SplashActivity splashActivity)
    {
        caller=splashActivity;
    }

    public ReaderTask()
    {
    }

    private String readStream(InputStream is,int cl) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

    protected String doInBackground(String... urls) {
        String rt="";

        if(caller != null)
            caller.updateProgress (0);

        try {
            for(int i=0; i<urls.length; i++)
            {
                URL url = new URL (urls[i]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();

                int maxStale = 60 * 60 * 24 * 1; // tolerate 4-weeks stale
                urlConnection.addRequestProperty ("Cache-Control", "max-stale=" + maxStale);
                urlConnection.setUseCaches (true);

                //
                //InputStream in = new BufferedInputStream (urlConnection.getInputStream ());
                //rt = readStream (in,urlConnection.getContentLength ());
                //
                BufferedReader in = new BufferedReader (new InputStreamReader (
                        urlConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //
                rt = response.toString ();
                urlConnection.disconnect();
                if(caller != null)
                    caller.updateProgress (i+1);
            }

        } catch (Exception e) {

            Log.i(Constants.LOG_ERROR_URLTASKFAILED,
                    "OVER ICS: HTTP response cache failed:" + e);
            this.exception = e;
            return null;
        }
        return rt;
    }

    @Override
    public void onPostExecute (String result)
    {
        if(caller != null)
        {
            Intent mainIntent = new Intent(caller,HomeActivity.class);
            caller.startActivity(mainIntent);
            caller.finish();
        }
    }


}

