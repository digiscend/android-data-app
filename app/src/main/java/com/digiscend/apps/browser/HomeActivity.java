package com.digiscend.apps.browser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.codepath.oauth.OAuthLoginActionBarActivity;

public class HomeActivity extends OAuthLoginActionBarActivity<RestClient> {

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

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		// Intent i = new Intent(this, PhotosActivity.class);
		// startActivity(i);
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

	public void loginSwitchView(View view)
	{

	}

	public void showBrowseCountry(View view)
	{
	}

	public void showBrowseOwner(View view)
	{
	}

	public void showBrowseMaterial(View view)
	{
	}

	public void showBrowseStage(View view)
	{
	}

	public void showTopProjects(View view)
	{
		Intent intent = new Intent (HomeActivity.this, MinesActivity.class);
		startActivity(intent);
	}
}
