package com.digiscend.apps.browser.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ImageTask;
import com.digiscend.apps.browser.models.Constants;
import com.digiscend.apps.browser.models.Project;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This adapter just picks project names and shows in the list
 * Created by vikas on 04/07/16.
 * @see https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class ProjectContractorsAdapter extends ArrayAdapter<Project>
{
    public ProjectContractorsAdapter(Context context, ArrayList<Project> projects) {
        super(context, 0, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i (Constants.LOG,"Starting ProjectContractorsAdapter");

        // Get the data item for this position
        Project currentProject = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_contractor_view, parent, false);
        }
        // Lookup view for data population
        ImageView iview = (ImageView) convertView.findViewById(R.id.contractorLogo);
        Bitmap bitmap = BitmapFactory.decodeByteArray (currentProject.company.logobitmap, 0, currentProject.company.logobitmap.length);
        if(bitmap != null)
            iview.setImageBitmap (bitmap);
        return convertView;
    }
}