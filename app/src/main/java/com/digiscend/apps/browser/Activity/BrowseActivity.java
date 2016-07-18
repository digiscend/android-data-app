package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ReaderTask;
import com.digiscend.apps.browser.models.BrowserFilter;
import com.digiscend.apps.browser.adapters.FilterAdapter;
import com.digiscend.apps.browser.models.Constants;
import com.digiscend.apps.browser.models.ExtraHolder;

import java.util.ArrayList;

/**
 * Created by vikas on 04/07/16.
 */
public class BrowseActivity extends AppCompatActivity
{
    public final static String EXTRA_BROWSETYPE = "browsetype";
    public final static String BROWSE_SEARCH = "q";

    public ExtraHolder browsetype;
    public String typename = "";
    public String filters = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_mines);

        if (savedInstanceState == null) {
            browsetype = (ExtraHolder) getIntent().getSerializableExtra (BrowseActivity.EXTRA_BROWSETYPE);
        } else {
            browsetype = (ExtraHolder) savedInstanceState.getSerializable(EXTRA_BROWSETYPE);
        }

        TextView txtfilterInfo = (TextView) findViewById(R.id.filterInfo);
        txtfilterInfo.setText ("");

        String api_browselisttype = "";

        switch(browsetype.baseType)
        {
            case STAGE:
                api_browselisttype = Constants.API_STAGELIST;
                setTitle (getResources().getString(R.string.title_browse_stage));
                break;
            case COUNTRY:
                api_browselisttype = Constants.API_COUNTRYLIST;
                setTitle (getResources().getString(R.string.title_browse_country));
                break;
            case MINERAL:
                api_browselisttype = Constants.API_MINERALLIST;
                setTitle (getResources().getString(R.string.title_browse_mineral));
                break;
        }

        filters = browsetype.getFilters(this.getBaseContext ());
        ArrayList<String> filterInfoStrings = browsetype.getFilterInfoStrings ();
        Log.v(Constants.LOG_BWFILTER,filters);

        try
        {
            String url = getResources().getString(R.string.api_server)
                    + api_browselisttype
                    + filters
                    + "?lang=" + getResources().getString(R.string.api_q_lang);
            Log.v(Constants.LOG_BWURL,url);
            String str_result = new ReaderTask ().execute (url).get ();
            BrowserFilter b = null;
            ArrayList<BrowserFilter> filtervals = b.parseJson(str_result,browsetype);
            setFilterContent(filtervals);
        }
        catch(Exception e)
        {

        }

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
                browsetype.addFilter(val);
                /*String[] lastbrowsetypelist = browsetype.split (",");
                if(lastbrowsetypelist.length>1)
                {
                    for (int i = 1; i < lastbrowsetypelist.length; i++)
                    {
                        val.lastbrowsetype = lastbrowsetypelist[i] + "," + val.lastbrowsetype;
                    }
                }*/
                Intent intent = new Intent(getBaseContext (), MinesActivity.class);
                intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, browsetype);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }
}
