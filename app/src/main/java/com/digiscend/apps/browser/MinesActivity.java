package com.digiscend.apps.browser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.digiscend.apps.browser.models.Project;
import com.digiscend.apps.browser.models.ProjectAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MinesActivity extends AppCompatActivity
{
    public final static String EXTRA_MESSAGE = "com.example.ListViewTest.MESSAGE";

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
            String url = getResources().getString(R.string.api_server) + getResources().getString(R.string.api_projectlist) + "?lang=" + getResources().getString(R.string.api_q_lang);
            String str_result = new ReaderTask ().execute (url).get ();
            Project p = null;
            ArrayList<Project> projects = p.parseJson(str_result);
            setProjects(projects);
        }
        catch(Exception e)
        {

        }

    }
    /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        String item = (String) getListAdapter().getItem(position);
        return;
    }
    */



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

        ProjectAdapter adapter = new ProjectAdapter (this, projects);
        ListView listView = (ListView) findViewById(R.id.project_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Project entry= (Project) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getBaseContext (), ProjectViewActivity.class);
                intent.putExtra(EXTRA_MESSAGE, entry.htmlid);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }
}
