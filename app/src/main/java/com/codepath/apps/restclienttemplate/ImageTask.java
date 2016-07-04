package com.codepath.apps.restclienttemplate;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
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
            bitmap = BitmapFactory.decodeStream((InputStream)new URL (args[0]).getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}