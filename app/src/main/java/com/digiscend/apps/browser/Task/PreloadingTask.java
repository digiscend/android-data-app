package com.digiscend.apps.browser.Task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.digiscend.apps.browser.Activity.BrowseActivity;
import com.digiscend.apps.browser.Activity.HomeActivity;
import com.digiscend.apps.browser.Activity.SplashActivity;
import com.digiscend.apps.browser.R;

/**
 * Created by vikas on 06/07/16.
 */
public class PreloadingTask extends AsyncTask<Void, Integer, Void>
{
    private ProgressDialog progressDialog;
    private Activity hA;

    public PreloadingTask(Activity hA)
    {
        this.hA = hA;
    }

    @Override
    protected void onPreExecute()
    {
        Intent intent = new Intent (hA, SplashActivity.class);
        hA.startActivity(intent);
    }

    //The code to be executed in a background thread.
    @Override
    protected Void doInBackground(Void... params)
    {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */

        return null;
    }

    //Update the progress
    @Override
    protected void onProgressUpdate(Integer... values)
    {
        //set the current progress of the progress dialog
        //progressDialog.setProgress(values[0]);
    }

    //after executing the code in the thread
    @Override
    protected void onPostExecute(Void result)
    {
        //close the progress dialog
        //progressDialog.dismiss();
        //initialize the View
        Intent mainIntent = new Intent(hA,HomeActivity.class);
        hA.startActivity(mainIntent);
        hA.finish();

    }
}
