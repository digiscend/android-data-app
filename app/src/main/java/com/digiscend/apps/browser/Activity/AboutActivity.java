package com.digiscend.apps.browser.Activity;

import android.graphics.Bitmap;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ImageTask;

import java.util.concurrent.ExecutionException;

public class AboutActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);


		TextView cinfo = (TextView)findViewById (R.id.textCacheInfo);
		HttpResponseCache cache = HttpResponseCache.getInstalled();
		if(cache != null)
		cinfo.setText ("Size:" + (cache.size ()/1024) + ",ctr1=" + cache.getRequestCount ()+",ctr2=" + cache.getNetworkCount ()+",ctr3="+cache.getHitCount ());
	}

}
