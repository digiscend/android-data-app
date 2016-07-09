package com.digiscend.apps.browser.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.models.Constants;
import com.digiscend.apps.browser.models.Project;

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

        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
    }

}
