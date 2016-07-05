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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_mines);

        String browsetype = "";

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                browsetype = null;
            } else {
                browsetype = extras.getString(BrowseActivity.EXTRA_BROWSETYPE);
            }
        } else {
            browsetype = (String) savedInstanceState.getSerializable(BrowseActivity.EXTRA_BROWSETYPE);
        }

        String filters = "";
        if(browsetype != null)
        {
            String[] ss = browsetype.split ("=");
            filters = "/" + ss[0] + "/" + ss[1];
        }


        // Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);

        //setSupportActionBar (toolbar);

        try
        {
            //new ReaderTask().execute("http://gateway.local/site/helloservice");
            //String str_result = new ReaderTask ().execute ("http://www.gateway.local/site/helloservice").get ();
            String url = getResources ().getString (R.string.api_server) + getResources ().getString (R.string.api_projectlist) + filters + "?lang=" + getResources ().getString (R.string.api_q_lang);
            String str_result = new ReaderTask ().execute (url).get ();
            Project p = null;
            ArrayList<Project> projects = p.parseJson (str_result);
            setProjects (projects);
        } catch (Exception e)
        {

        }
    }

    protected void setProjects(ArrayList<Project> projects)
    {
        ArrayList<String> projectNames = new ArrayList<String> ();
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
