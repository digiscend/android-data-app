package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ProjectViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    Project currentProject;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_project_view);

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        if(toolbar!=null)
            setSupportActionBar (toolbar);

        ViewStub stub = (ViewStub) findViewById(R.id.stub);
        stub.setLayoutResource(R.layout.stub_project_properties);
        stub.inflate();

        //ViewGroup.LayoutParams lp2 = getLayoutParams();
        //ViewGroup.LayoutParams lp = stub.getLayoutParams();
        //lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        //int h = lp.height;
        //int h2= lp2.height;

        //stub.setLayoutParams(lp);

        //stub.setMinimumHeight (h - h2);


        String newString;
        String projectid = "";

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                projectid = extras.getString(MinesActivity.EXTRA_MESSAGE);
            }
        } else {
            projectid= (String) savedInstanceState.getSerializable(MinesActivity.EXTRA_MESSAGE);
        }

        ArrayList<Project> projects = null;
        String str_result = null;
        try
        {
            String url=getResources().getString(R.string.api_server) + getResources().getString(R.string.api_project) + "/" + projectid + "?lang=" + getResources().getString(R.string.api_q_lang);
            str_result = new ReaderTask ().execute (url).get ();
            Project p = null;
            projects = p.parseJson(str_result);
        } catch (InterruptedException e)
        {
            e.printStackTrace ();
        } catch (ExecutionException e)
        {
            e.printStackTrace ();
        }


        TextView tvName = (TextView)findViewById (R.id.textYear);
        TextView tvCountry = (TextView)findViewById (R.id.countryName);
        TextView tvCompany = (TextView)findViewById (R.id.companyName);
        TextView tvIntro = (TextView)findViewById (R.id.projectIntro);

        currentProject = projects.get (0);

        setTitle(currentProject.name);
        tvCountry.setText (currentProject.country);
        if(tvIntro != null)
            tvIntro.setText (Html.fromHtml (currentProject.intro));
        if(currentProject.company != null)
            tvCompany.setText (projects.get (0).company.name);

        String url = null;

        try
        {
            url = getResources().getString(R.string.api_server) + currentProject.company.logosrc;
            Bitmap bitmap = new ImageTask ().execute (url).get ();
            ImageView iview = (ImageView)findViewById (R.id.companyLogo);
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

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.project_view_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener (toggle);
        toggle.syncState ();

        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this);
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
        else if (id == R.id.nav_properties)
        {
            Intent intent = new Intent(this, ProjectAttrsActivity.class);
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
