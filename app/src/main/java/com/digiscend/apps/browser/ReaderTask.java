package com.digiscend.apps.browser;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vikas on 03/07/16.
 */
class ReaderTask extends AsyncTask<String, Void, String>
{

    private Exception exception;

    private String readStream(InputStream is) {
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
        try {
            URL url= new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection ();
            InputStream in = new BufferedInputStream (urlConnection.getInputStream ());



            String rt = readStream (in);
            urlConnection.disconnect();
            return rt;

        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }


}

