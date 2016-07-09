package com.digiscend.apps.browser.Activity;

import android.os.Bundle;
import android.widget.ListView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.adapters.ProjectMilestoneAdapter;
import com.digiscend.apps.browser.models.Project;

import java.util.ArrayList;

public class ProjectViewMilestonesActivity extends ProjectViewActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        stubview = true;
        stubLayoutResourceId = R.layout.stub_project_milestones;
        super.onCreate (savedInstanceState);
        //common work done by the parent
        super.getProjectFromExtras(savedInstanceState);
        setProjectHeaders(currentProject);
        setProjects (currentProject.milestoneProjects);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected void setProjects(ArrayList<Project> projects)
    {
        ArrayList<String> projectNames = new ArrayList<String> ();
        ProjectMilestoneAdapter adapter = new ProjectMilestoneAdapter (this, projects);
        ListView listView = (ListView) findViewById(R.id.attr_list);
        listView.setAdapter(adapter);
    }

}
