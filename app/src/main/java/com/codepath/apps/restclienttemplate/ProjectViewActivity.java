package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Project;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProjectViewActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);

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
            str_result = new ReaderTask ().execute ("http://www.gateway.local/api/project/" + projectid).get ();
            Project p = null;
            projects = p.parseJson(str_result);
        } catch (InterruptedException e)
        {
            e.printStackTrace ();
        } catch (ExecutionException e)
        {
            e.printStackTrace ();
        }





        setContentView (R.layout.activity_project_view);
        TextView tvName = (TextView)findViewById (R.id.projectName);
        TextView tvCountry = (TextView)findViewById (R.id.countryName);
        TextView tvCompany = (TextView)findViewById (R.id.companyName);

        tvName.setText (projects.get (0).name);
        tvCountry.setText (projects.get (0).country);
        if(projects.get (0).company != null)
            tvCompany.setText (projects.get (0).company.name);
    }
}
