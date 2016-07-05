package com.digiscend.apps.browser;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiscend.apps.browser.models.Project;

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

        setContentView (R.layout.activity_project_view);
        TextView tvName = (TextView)findViewById (R.id.itemName);
        TextView tvCountry = (TextView)findViewById (R.id.countryName);
        TextView tvCompany = (TextView)findViewById (R.id.companyName);
        TextView tvIntro = (TextView)findViewById (R.id.projectIntro);

        setTitle(projects.get (0).name);
        tvCountry.setText (projects.get (0).country);
        tvIntro.setText (Html.fromHtml (projects.get (0).intro));
        if(projects.get (0).company != null)
            tvCompany.setText (projects.get (0).company.name);

        String url = null;

        try
        {
            url = getResources().getString(R.string.api_server) + projects.get (0).company.logosrc;
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

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu (menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filters, menu);
        return true;
    }

}
