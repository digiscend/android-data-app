package com.digiscend.apps.browser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;

public class HomeActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void showBrowseCountry(View view)
	{
		Intent intent = new Intent (HomeActivity.this, BrowseActivity.class);
		//intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, BrowseActivity.BROWSE_COUNTRY);
		startActivity(intent);
	}

	public void showBrowseOwner(View view)
	{
		Intent intent = new Intent (HomeActivity.this, BrowseActivity.class);
		intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, BrowseActivity.BROWSE_OWNER);
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
}
