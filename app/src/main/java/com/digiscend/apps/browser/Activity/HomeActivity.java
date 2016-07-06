package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.PreloadingTask;
import com.digiscend.apps.browser.models.Constants;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		/*ProgressDialog dialog=new ProgressDialog(getBaseContext ());
		dialog.setMessage("message");
		dialog.setCancelable(false);
		dialog.setInverseBackgroundForced(false);
		dialog.show();*/

        //PreloadingTask pre =new PreloadingTask (this);
        //pre.execute ();

        File httpCacheDir = new File (getApplicationContext().getCacheDir()
				, "http");
		long httpCacheSize = 10 * 1024 * 1024; // 10 MiB

        HttpResponseCache cache = HttpResponseCache.getInstalled();
        if (cache == null)
        {
            try
            {
                cache = HttpResponseCache.install (httpCacheDir, httpCacheSize);
            } catch (IOException e)
            {
                Log.i (Constants.LOG_ERROR_CACHEINSTALLFAILED,
                        "OVER ICS: HTTP response cache failed:" + e);
            }
        }

	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu (menu);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.home, menu);
		return true;
	}

	public void showBrowseCountry(View view)
	{
		Intent intent = new Intent (HomeActivity.this, BrowseActivity.class);
		intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, BrowseActivity.BROWSE_COUNTRY);
		startActivity(intent);
	}

	public void showBrowseMaterial(View view)
	{
		Intent intent = new Intent (HomeActivity.this, BrowseActivity.class);
		intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, BrowseActivity.BROWSE_METAL);
		startActivity(intent);
	}

	public void showBrowseStage(View view)
	{
		Intent intent = new Intent (HomeActivity.this, BrowseActivity.class);
		intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, BrowseActivity.BROWSE_STAGE);
		startActivity(intent);
	}

	public void showTopProjects(View view)
	{
		Intent intent = new Intent (HomeActivity.this, MinesActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(getApplicationContext(),	item.getTitle() + " selected", Toast.LENGTH_SHORT).show();

		switch (item.getItemId()) {
			case R.id.aboutmenuitem:
				Intent intent = new Intent (HomeActivity.this, AboutActivity.class);
				startActivity(intent);
				break;
		}
		return true;
	}
}
