package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.PreloadingTask;
import com.digiscend.apps.browser.models.Constants;
import com.digiscend.apps.browser.models.ExtraHolder;

import java.io.File;
import java.io.IOException;

public class HomeActivity extends AppCompatActivity
		implements SearchView.OnQueryTextListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		SearchView search=(SearchView) findViewById (R.id.searchView1);
		search.setQueryHint(getResources ().getString (R.string.search));
		search.setOnQueryTextListener(this);

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
		intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, new ExtraHolder (ExtraHolder.baseView.COUNTRY));
		startActivity(intent);
	}

	public void showBrowseMaterial(View view)
	{
		Intent intent = new Intent (HomeActivity.this, BrowseActivity.class);
		intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, new ExtraHolder (ExtraHolder.baseView.MINERAL));
		startActivity(intent);
	}

	public void showBrowseStage(View view)
	{
		Intent intent = new Intent (HomeActivity.this, BrowseActivity.class);
		intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, new ExtraHolder (ExtraHolder.baseView.STAGE));
		startActivity(intent);
	}

	public void showTopProjects(View view)
	{
		Intent intent = new Intent (HomeActivity.this, MinesActivity.class);
		ExtraHolder browsetype = new ExtraHolder (ExtraHolder.baseView.PROJECTS);
		intent.putExtra(BrowseActivity.EXTRA_PLBROWSETYPE, browsetype);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.aboutmenuitem:
				Intent intent = new Intent (HomeActivity.this, AboutActivity.class);
				startActivity(intent);
				break;
		}
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// User pressed the search button
		Intent intent = new Intent(getBaseContext (), MinesActivity.class);
		intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, new ExtraHolder (ExtraHolder.baseView.PROJECTS,query));
		startActivity(intent);
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// User changed the text
		return false;
	}
}
