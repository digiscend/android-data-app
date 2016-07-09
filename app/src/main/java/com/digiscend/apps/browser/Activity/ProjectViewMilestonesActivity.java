package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.adapters.ProjectAdapter;
import com.digiscend.apps.browser.adapters.ProjectAttrAdapter;
import com.digiscend.apps.browser.models.Constants;
import com.digiscend.apps.browser.models.Project;

import java.util.ArrayList;

public class ProjectViewMilestonesActivity extends AppCompatActivity
{

    Project currentProject;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_project_view_milestones);
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                currentProject = null;
            } else {
                currentProject = (Project)getIntent().getSerializableExtra(Constants.ACTIVE_PROJECT_OBJECT);
            }
        } else {
            currentProject = (Project)getIntent().getSerializableExtra(Constants.ACTIVE_PROJECT_OBJECT);
        }

        setProjects (currentProject.milestoneProjects);

        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
    }

    protected void setProjects(ArrayList<Project> projects)
    {
        ArrayList<String> projectNames = new ArrayList<String> ();
        ProjectAttrAdapter adapter = new ProjectAttrAdapter (this, projects);
        ListView listView = (ListView) findViewById(R.id.attr_list);
        listView.setAdapter(adapter);
    }

}
