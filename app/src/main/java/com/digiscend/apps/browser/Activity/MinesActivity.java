package com.digiscend.apps.browser.Activity;

import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.digiscend.apps.browser.models.Project;
import com.digiscend.apps.browser.adapters.ProjectAdapter;

import java.util.ArrayList;
import java.util.Locale;


public class MinesActivity extends AppCompatActivity
{
    public final static String EXTRA_MESSAGE = "com.example.ListViewTest.MESSAGE";

    private String browsetype;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_mines);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                browsetype = null;
            } else {
                browsetype = extras.getString(BrowseActivity.EXTRA_BROWSETYPE);
            }
        } else {
            browsetype = (String) savedInstanceState.getSerializable(BrowseActivity.EXTRA_BROWSETYPE);
        }



        ArrayList<String> filterInfoStrings = new ArrayList<String>();
        String filters = "";
        String filters2 = "";
        if(browsetype != null)
        {

            String[] browsetypelist = browsetype.split (",");
            for (int i = 0; i < browsetypelist.length; i++)
            {
                String item2 = browsetypelist[i];
                if (item2 == "null")
                    break;

                String[] ss = item2.split ("=");
                if (ss.length != 2)
                    break;

                filters += "/" + ss[0] + "/" + ss[1];
                filters2 += "-" + ss[0] + "-" + ss[1];

                String  title = toSentenceCase(ss[0]);

                filterInfoStrings.add (title + ": " + ss[1]);
            }
        }
        Log.v(Constants.LOG_BWFILTER,filters);


        Log.v(Constants.LOG_PLFILTER,filters);
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
        ArrayList<Project> projects = p.loadlistByFilters(filters,filters2,getBaseContext (), BuildConfig.VERSION_CODE);
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
                item.getItemId() == R.id.filter_by_metals)
            onFilterPresssed(item.getItemId());
        return true;
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu (menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filters, menu);

        if(browsetype != null)
        {
            String[] browsetypelist = browsetype.split (",");
            for (int i = 0; i < browsetypelist.length; i++)
            {
                String item2 = browsetypelist[i];
                if (item2 == "null")
                    break;

                String[] ss = item2.split ("=");
                switch(ss[0])
                {
                    case BrowseActivity.BROWSE_COUNTRY:
                        menu.findItem(R.id.filter_by_countries).setVisible(false);
                        break;
                    case BrowseActivity.BROWSE_STAGE:
                        menu.findItem(R.id.filter_by_stages).setVisible(false);
                        break;
                    case BrowseActivity.BROWSE_METAL:
                        menu.findItem(R.id.filter_by_metals).setVisible(false);
                        break;
                }
            }
        }

        return true;
    }

    private void onFilterPresssed(int itemId)
    {
        Intent intent = new Intent (this, BrowseActivity.class);
        if(browsetype != null)
        {
            browsetype = "," + browsetype;
        }
        else
            browsetype = "";


        switch(itemId)
        {
            case R.id.filter_by_countries:
                intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, BrowseActivity.BROWSE_COUNTRY + browsetype);
                break;
            case R.id.filter_by_metals:
                intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, BrowseActivity.BROWSE_METAL + browsetype);
                break;
            case R.id.filter_by_stages:
                intent.putExtra(BrowseActivity.EXTRA_BROWSETYPE, BrowseActivity.BROWSE_STAGE + browsetype);
                break;
        }
        startActivity(intent);
    }

    public static String toSentenceCase(String inputString) {
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