package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;


public class MinesActivity extends AppCompatActivity
{

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_mines);
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);

        setSupportActionBar (toolbar);

        TextView txtbox = (TextView)findViewById(R.id.txtbox);

        try
        {
            //new ReaderTask().execute("http://gateway.local/site/helloservice");
            //String str_result = new ReaderTask ().execute ("http://www.gateway.local/site/helloservice").get ();
            String str_result = new ReaderTask ().execute ("http://www.gateway.local/site/helloservice02").get ();
            String result2 = parseResult(str_result);
            txtbox.setText (result2);
        }
        catch(Exception e)
        {
            txtbox.setText(e.getMessage ());
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ();
            }
        });
    }

    protected String parseResult(String json)
    {
        ArrayList<String> projectNames = new ArrayList<String> ();
        try
        {
            JSONObject obj = new JSONObject (json);
            JSONArray jProjects = obj.getJSONArray ("projects");

            for (int i = 0; i < jProjects.length (); i++)
            {
                HashMap<String, String> map = new HashMap<String, String> ();
                String e = jProjects.getString (i);
                projectNames.add (e);
            }
        }
        catch(Exception e)
        {
            return e.getMessage ();
        }
        return projectNames.toString ();

    }
}
