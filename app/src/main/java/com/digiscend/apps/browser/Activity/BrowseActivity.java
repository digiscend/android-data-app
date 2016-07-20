package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.digiscend.apps.browser.BuildConfig;
import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ReaderTask;
import com.digiscend.apps.browser.models.BrowserFilter;
import com.digiscend.apps.browser.adapters.FilterAdapter;
import com.digiscend.apps.browser.models.Constants;
import com.digiscend.apps.browser.models.ExtraHolder;
import com.digiscend.apps.browser.models.Project;

import java.util.ArrayList;

/**
 * Created by vikas on 04/07/16.
 */
public class BrowseActivity extends AppCompatActivity
{
    public final static String EXTRA_BROWSETYPE = "browsetype";
    public final static String BROWSE_SEARCH = "q";
    public static final String EXTRA_PLBROWSETYPE = "plbrowsetype";

    public ExtraHolder browsetype;
    public String typename = "";
    public String filters = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_mines);

        if (savedInstanceState == null)
        {
            browsetype = (ExtraHolder) getIntent ().getSerializableExtra (BrowseActivity.EXTRA_BROWSETYPE);
        } else
        {
            browsetype = (ExtraHolder) savedInstanceState.getSerializable (EXTRA_BROWSETYPE);
        }

        ArrayList<String> filterInfoStrings = browsetype.getFilterInfoStrings ();
        TextView txtfilterInfo = (TextView) findViewById(R.id.filterInfo);
        if(filterInfoStrings.size ()>0)
        {
            String s1 = TextUtils.join ("\n",filterInfoStrings);
            txtfilterInfo.setText (s1);
        }
        else
            txtfilterInfo.setText ("");


        //ArrayList<String> filterInfoStrings = browsetype.getFilterInfoStrings ();
        BrowserFilter bf = null;
        ArrayList<BrowserFilter> filtervals = bf.loadlistByFilters (browsetype, this, BuildConfig.VERSION_CODE);
        setFilterContent (filtervals);
    }

    /*
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        String item = (String) getListAdapter().getItem(position);
        return;
    }
    */

    @Override
    protected void onStart() {
        super.onStart();

        ExtraHolder browsetype2 = (ExtraHolder) getIntent().getSerializableExtra (BrowseActivity.EXTRA_BROWSETYPE);
        browsetype2.search_name += "1";
        // The activity is about to become visible.
    }



    protected void setFilterContent(ArrayList<BrowserFilter> filtervals)
    {
        FilterAdapter adapter = new FilterAdapter (this, filtervals);
        ListView listView = (ListView) findViewById(R.id.project_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                BrowserFilter val= (BrowserFilter) parent.getAdapter().getItem(position);
                ExtraHolder browsetype_for_mines = browsetype.clone ();
                browsetype_for_mines.addFilter(val);
                /*String[] lastbrowsetypelist = browsetype.split (",");
                if(lastbrowsetypelist.length>1)
                {
                    for (int i = 1; i < lastbrowsetypelist.length; i++)
                    {
                        val.lastbrowsetype = lastbrowsetypelist[i] + "," + val.lastbrowsetype;
                    }
                }*/
                Intent intent = new Intent(getBaseContext (), MinesActivity.class);
                intent.putExtra(BrowseActivity.EXTRA_PLBROWSETYPE, browsetype_for_mines);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }
}
