package com.digiscend.apps.browser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.models.Project;

import java.util.ArrayList;

/**
 * Created by vikas on 04/07/16.
 * @see https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class ProjectAdapter extends ArrayAdapter<Project>
{
    public ProjectAdapter(Context context, ArrayList<Project> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project project = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview , parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.itemName);
        TextView tvCountry = (TextView) convertView.findViewById(R.id.countryName);
        // Populate the data into the template view using the data object
        tvName.setText(project.name);
        tvCountry.setText(project.country);
        //tvId.setText(project.id);
        // Return the completed view to render on screen

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext (), ProjectViewActivity.class);
                String message = "abc";
                getContext ().startActivity (intent);
            }
        });*/

        return convertView;
    }
}