package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiscend.apps.browser.BuildConfig;
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
    int stubLayoutResourceId = 0;
    Project currentProject;
    boolean stubview = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_project_view);

        ViewStub stub = (ViewStub) findViewById(R.id.stub);

        if(stubLayoutResourceId==0)
            stubLayoutResourceId = R.layout.stub_project_description;
        stub.setLayoutResource(stubLayoutResourceId);
        stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        if(toolbar!=null)
            setSupportActionBar (toolbar);

        if(stubview)
        {
            Log.i (Constants.LOG,"Returning to caller activity from ProjectViewActivity");
            return;
        }
        //common section ends here


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

        Project p = null;
        currentProject = p.loadById (projectid,getBaseContext (), BuildConfig.VERSION_CODE);

        setProjectHeaders(currentProject);

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.project_view_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener (toggle);
        toggle.syncState ();

        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this);
    }

    public void setProjectHeaders(Project p)
    {
        TextView tvCountry = (TextView)findViewById (R.id.countryName);
        TextView tvCompany = (TextView)findViewById (R.id.companyName);
        TextView tvIntro = (TextView)findViewById (R.id.projectIntro);


        setTitle(p.name);
        tvCountry.setText (p.country);
        if(tvIntro != null)
            tvIntro.setText (Html.fromHtml (p.intro));
        if(currentProject.company != null)
            tvCompany.setText (p.company.name);

        String url = null;

        if(p.company.logobitmap != null)
        {
            ImageView iview = (ImageView) findViewById (R.id.companyLogo);
            Bitmap bitmap = BitmapFactory.decodeByteArray (p.company.logobitmap, 0, p.company.logobitmap.length);
            iview.setImageBitmap (bitmap);
        }
        else
        {
            try
            {
                url = getResources().getString(R.string.api_server) + p.company.logosrc;
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

        }

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
            Intent intent = new Intent(this, ProjectViewAttrsActivity.class);
            intent.putExtra(Constants.ACTIVE_PROJECT_OBJECT, currentProject);
            startActivity(intent);
        }
        else if (id == R.id.nav_contractors)
        {
            Intent intent = new Intent(this, ProjectViewContractorsActivity.class);
            intent.putExtra(Constants.ACTIVE_PROJECT_OBJECT, currentProject);
            Log.i (Constants.LOG,"Going to starting contractor activity");
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

    public void getProjectFromExtras(Bundle savedInstanceState)
    {
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
    }

}
