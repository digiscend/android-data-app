package com.digiscend.apps.browser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

		String url = "";
		try
		{
			url = "https://digiscend.com/images/digiscend-logo.png";
			Bitmap bitmap = new ImageTask ().execute (url).get ();
			ImageView iview = (ImageView)findViewById (R.id.imageView);
			iview.setImageBitmap (bitmap);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace ();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace ();
		}
	}

}
