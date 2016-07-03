package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Project;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
       // Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);

        //setSupportActionBar (toolbar);

        try
        {
            //new ReaderTask().execute("http://gateway.local/site/helloservice");
            //String str_result = new ReaderTask ().execute ("http://www.gateway.local/site/helloservice").get ();
            String str_result = new ReaderTask ().execute ("http://www.gateway.local/site/helloservice03").get ();
            ArrayList<Project> projects = parseResult(str_result);
            setProjects(projects);
        }
        catch(Exception e)
        {

        }

    }

    /**
     * @see http://www.codeproject.com/Articles/267023/Send-and-receive-json-between-android-and-php
     * @param json
     * @return
     */
    protected ArrayList<Project> parseResult(String json)
    {
        ArrayList<Project> projects =
                new ArrayList<Project>();
        try
        {
            JSONObject obj = new JSONObject (json);
            JSONArray jProjects = obj.getJSONArray ("projects");

            for (int i = 0; i < jProjects.length (); i++)
            {
                HashMap<String, String> map = new HashMap<String, String> ();
                JSONObject jsonProject = jProjects.getJSONObject (i);
                Project p = new Project();
                p.name = jsonProject.getString ("name");
                p.id = jsonProject.getInt ("id");
                projects.add (p);
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return projects;

    }

    protected void setProjects(ArrayList<Project> projects)
    {
        ArrayList<String> projectNames = new ArrayList<String> ();
        //ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, R.layout.activity_mines);
        String[] vals = new String[projects.size ()];
        for (int i = 0; i < projects.size (); i++)
        {
            //projectNames.add (projects.get (i).name);
            //adapter.add (projects.get (i).name);
            vals[i] = projects.get (i).name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, R.layout.activity_listview,vals);
        ListView listView = (ListView) findViewById(R.id.project_list);
        listView.setAdapter(adapter);
    }
}
