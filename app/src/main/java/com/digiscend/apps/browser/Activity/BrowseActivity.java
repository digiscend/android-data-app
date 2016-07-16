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

import java.util.ArrayList;

/**
 * Created by vikas on 04/07/16.
 */
public class BrowseActivity extends AppCompatActivity
{
    public final static String EXTRA_BROWSETYPE = "browsetype";
    public static final String EXTRA_BROWSETYPENAME = "typename";

    public final static String BROWSE_COUNTRY = "country";

    public final static String BROWSE_STAGE = "stage";

    public final static String BROWSE_METAL = "metal";

    public final static String BROWSE_SEARCH = "q";

    public String browsetype = "";
    public String typename = "";
    public String filters = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_mines);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                browsetype = BROWSE_COUNTRY;
            } else {
                browsetype = extras.getString(EXTRA_BROWSETYPE);
                typename = extras.getString(EXTRA_BROWSETYPENAME);
            }
        } else {
            browsetype = (String) savedInstanceState.getSerializable(EXTRA_BROWSETYPE);
            typename = (String) savedInstanceState.getSerializable(EXTRA_BROWSETYPENAME);
        }

        TextView txtfilterInfo = (TextView) findViewById(R.id.filterInfo);
        txtfilterInfo.setText ("");

        String api_browselisttype = "";

        String[] browsetypelist = browsetype.split (",");
        String firstbrowsetype = browsetypelist[0];
        switch(browsetypelist[0])
        {
            case BROWSE_STAGE:
                api_browselisttype = Constants.API_STAGELIST;
                setTitle (getResources().getString(R.string.title_browse_stage));
                break;
            case BROWSE_COUNTRY:
                api_browselisttype = Constants.API_COUNTRYLIST;
                setTitle (getResources().getString(R.string.title_browse_country));
                break;
            case BROWSE_METAL:
                api_browselisttype = Constants.API_METALLIST;
                setTitle (getResources().getString(R.string.title_browse_metal));
                break;
        }

        ArrayList<String> filterInfoStrings = new ArrayList<String>();

        for(int i=1; i<browsetypelist.length; i++)
        {
            String item2 = browsetypelist[i];
            if(item2 == "null")
                break;

            String[] ss = item2.split ("=");
            if(ss.length != 2)
                break;

            filters += "/" + ss[0] + "/" + ss[1];

            filterInfoStrings.add (ss[0] + ": " + ss[1]);
        }
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
            ArrayList<BrowserFilter> filtervals = b.parseJson(str_result,firstbrowsetype);
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



    protected void setFilterContent(ArrayList<BrowserFilter> filtervals)
    {
        FilterAdapter adapter = new FilterAdapter (this, filtervals);
        ListView listView = (ListView) findViewById(R.id.project_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                BrowserFilter val= (BrowserFilter) parent.getAdapter().getItem(position);
                String[] lastbrowsetypelist = browsetype.split (",");
                if(lastbrowsetypelist.length>1)
                {
                    for (int i = 1; i < lastbrowsetypelist.length; i++)
                    {
                        val.lastbrowsetype = lastbrowsetypelist[i] + "," + val.lastbrowsetype;
                    }
                }
                Intent intent = new Intent(getBaseContext (), MinesActivity.class);
                intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, val.withLastBrowseType());
                intent.putExtra(BrowseActivity.EXTRA_BROWSETYPENAME, val.withLastName ());
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }
}
