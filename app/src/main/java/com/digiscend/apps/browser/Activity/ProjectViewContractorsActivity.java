package com.digiscend.apps.browser.Activity;

import android.os.Bundle;
import android.widget.ListView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.adapters.ProjectAttrAdapter;
import com.digiscend.apps.browser.adapters.ProjectContractorsAdapter;
import com.digiscend.apps.browser.adapters.ProjectMilestoneAdapter;
import com.digiscend.apps.browser.models.AttrValue;
import com.digiscend.apps.browser.models.Project;

import java.util.ArrayList;

public class ProjectViewContractorsActivity extends ProjectViewActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        stubview = true;
        stubLayoutResourceId = R.layout.stub_project_contractors;
        super.onCreate (savedInstanceState);
        //common work done by the parent
        super.getProjectFromExtras(savedInstanceState);
        setProjectHeaders(currentProject);
        setProjects (currentProject.majorProjects);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    protected void setProjects(ArrayList<Project> projects)
    {
        ArrayList<String> projectNames = new ArrayList<String> ();
        ProjectContractorsAdapter adapter = new ProjectContractorsAdapter (this, projects);
        ListView listView = (ListView) findViewById(R.id.contractor_list);
        listView.setAdapter(adapter);

    }

}
