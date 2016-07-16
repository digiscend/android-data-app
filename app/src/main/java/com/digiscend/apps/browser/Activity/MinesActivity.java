package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.digiscend.apps.browser.BuildConfig;
import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ReaderTask;
import com.digiscend.apps.browser.models.Constants;
import com.digiscend.apps.browser.models.ExtraHolder;
import com.digiscend.apps.browser.models.Project;
import com.digiscend.apps.browser.adapters.ProjectAdapter;

import java.util.ArrayList;
import java.util.Locale;


public class MinesActivity extends AppCompatActivity
{
    public final static String EXTRA_MESSAGE = "com.example.ListViewTest.MESSAGE";

    private ExtraHolder browsetype;
    private String typename;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_mines);

        if (savedInstanceState == null) {
            browsetype = (ExtraHolder) getIntent().getSerializableExtra (BrowseActivity.EXTRA_BROWSETYPE);
        } else {
            browsetype = (ExtraHolder) savedInstanceState.getSerializable(BrowseActivity.EXTRA_BROWSETYPE);
        }


        String filters = browsetype.getFilters (getBaseContext ());
        ArrayList<String> filterInfoStrings = browsetype.getFilterInfoStrings ();
        String filterCacheId = browsetype.getFilterCacheId ();
        Log.v(Constants.LOG_BWFILTER,filters);

        TextView txtfilterInfo = (TextView) findViewById(R.id.filterInfo);
        if(filterInfoStrings.size ()>0)
        {
            String s1 = TextUtils.join ("\n",filterInfoStrings);
            txtfilterInfo.setText (s1);
        }
        else
            txtfilterInfo.setText ("");

        setTitle (getResources().getString(R.string.title_projectlist));
        // Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);

        //setSupportActionBar (toolbar);
        Project p = null;
        ArrayList<Project> projects = p.loadlistByFilters(filters,filterCacheId,getBaseContext (), BuildConfig.VERSION_CODE);
        setProjects (projects);
    }

    protected void setProjects(ArrayList<Project> projects)
    {
        ArrayList<String> projectNames = new ArrayList<String> ();
        ProjectAdapter adapter = new ProjectAdapter (this, projects);
        ListView listView = (ListView) findViewById(R.id.project_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Project entry= (Project) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getBaseContext (), ProjectViewActivity.class);
                intent.putExtra(EXTRA_MESSAGE, entry.htmlid);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.filter_by_countries ||
                item.getItemId() == R.id.filter_by_stages ||
                item.getItemId() == R.id.filter_by_minerals)
            onFilterPresssed(item.getItemId());
        return true;
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu (menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filters, menu);

        /*
         * To hide relevent menu items depending on the currently active filters
         */
        if(browsetype != null)
        {
            if(browsetype.country_filter.length ()>0)
                menu.findItem(R.id.filter_by_countries).setVisible(false);
            if(browsetype.stage_filter.length ()>0)
                menu.findItem(R.id.filter_by_stages).setVisible(false);
            if(browsetype.mineral_filter.length ()>0)
                menu.findItem(R.id.filter_by_minerals).setVisible(false);
        }

        return true;
    }

    private void onFilterPresssed(int itemId)
    {
        Intent intent = new Intent (this, BrowseActivity.class);
        switch(itemId)
        {
            case R.id.filter_by_countries:
                browsetype.setBaseType(ExtraHolder.baseView.COUNTRY);
                break;
            case R.id.filter_by_minerals:
                browsetype.setBaseType(ExtraHolder.baseView.MINERAL);
                break;
            case R.id.filter_by_stages:
                browsetype.setBaseType(ExtraHolder.baseView.STAGE);
                break;
        }
        intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, browsetype);
        startActivity(intent);
    }

    public static String toSentenceCase(String inputString)
    {
        String result = "";
        if (inputString.length() == 0) {
            return result;
        }
        char firstChar = inputString.charAt(0);
        char firstCharToUpperCase = Character.toUpperCase(firstChar);
        result = result + firstCharToUpperCase;
        boolean terminalCharacterEncountered = false;
        char[] terminalCharacters = {'.', '?', '!'};
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            if (terminalCharacterEncountered) {
                if (currentChar == ' ') {
                    result = result + currentChar;
                } else {
                    char currentCharToUpperCase = Character.toUpperCase(currentChar);
                    result = result + currentCharToUpperCase;
                    terminalCharacterEncountered = false;
                }
            } else {
                char currentCharToLowerCase = Character.toLowerCase(currentChar);
                result = result + currentCharToLowerCase;
            }
            for (int j = 0; j < terminalCharacters.length; j++) {
                if (currentChar == terminalCharacters[j]) {
                    terminalCharacterEncountered = true;
                    break;
                }
            }
        }
        return result;
    }

}