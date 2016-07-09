package com.digiscend.apps.browser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.models.BrowserFilter;

import java.util.ArrayList;

/**
 * This adapter just picks items like metals, countries and stages and shows in the list
 * Created by vikas on 04/07/16.
 * @see https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class FilterAdapter extends ArrayAdapter<BrowserFilter>
{
    public FilterAdapter(Context context, ArrayList<BrowserFilter> countries) {
        super(context, 0, countries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BrowserFilter filter = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview , parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.itemName);
        tvName.setText(filter.name);
        return convertView;
    }
}