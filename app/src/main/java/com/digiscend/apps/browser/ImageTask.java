package com.digiscend.apps.browser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vikas on 04/07/16.
 */
class ImageTask extends AsyncTask<String, Void, Bitmap>
{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... args) {
        Bitmap bitmap = null;
        try {

            URL url = new URL (args[0]);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setUseCaches(true);
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            Log.i(Constants.LOG_ERROR_IMAGETASKFAILED,
                    "OVER ICS: HTTP response cache failed:" + e);
        }
        return bitmap;
    }


}