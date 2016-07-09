package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ImageTask;
import com.digiscend.apps.browser.Task.ReaderTask;
import com.digiscend.apps.browser.models.Constants;
import com.digiscend.apps.browser.models.Project;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProjectAttrsActivity extends ProjectViewActivity
{

    Project currentProject;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);


    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.project_view_layout);
        if (drawer.isDrawerOpen (GravityCompat.START))
        {
            drawer.closeDrawer (GravityCompat.START);
        } else
        {
            super.onBackPressed ();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId ();

        if (id == R.id.nav_milestones)
        {
            Intent intent = new Intent(this, ProjectViewMilestonesActivity.class);
            intent.putExtra(Constants.ACTIVE_PROJECT_OBJECT, currentProject);
            startActivity(intent);
        }
        /*if (id == R.id.nav_share)
        {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.project_view_layout);
        drawer.closeDrawer (GravityCompat.START);
        return true;
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu (menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filters, menu);
        return true;
    }

}
