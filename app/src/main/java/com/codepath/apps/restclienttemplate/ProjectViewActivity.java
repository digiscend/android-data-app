package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProjectViewActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_project_view);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString(MinesActivity.EXTRA_MESSAGE);
            }
        } else {
            newString= (String) savedInstanceState.getSerializable(MinesActivity.EXTRA_MESSAGE);
        }
        TextView tvName = (TextView)findViewById (R.id.projectName);
        tvName.setText (newString);

    }
}
