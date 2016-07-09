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
 * This adapter just picks project names and shows in the list
 * Created by vikas on 04/07/16.
 * @see https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class ProjectAttrAdapter extends ArrayAdapter<Project>
{
    public ProjectAttrAdapter(Context context, ArrayList<Project> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project project = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_milestone_view, parent, false);
        }

        if(project == null)
            return convertView;

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.itemName);
        TextView tvYear = (TextView) convertView.findViewById(R.id.textYear);
        // Populate the data into the template view using the data object
        tvName.setText(project.name);
        tvYear.setText(project.year);
        return convertView;
    }
}