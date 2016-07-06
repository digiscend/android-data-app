package com.digiscend.apps.browser;

import android.app.Application;
import android.content.Context;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     RestClient client = BrowserApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class BrowserApplication extends Application
{
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		BrowserApplication.context = this;
	}
}