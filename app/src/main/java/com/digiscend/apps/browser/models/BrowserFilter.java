package com.digiscend.apps.browser.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.digiscend.apps.browser.Activity.BrowseActivity;
import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ReaderTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vikas on 04/07/16.
 */
public class BrowserFilter
        implements Serializable
{
    public String name;
    public String htmlid;
    public ExtraHolder lastbrowsetype;
    /**
     * @see http://www.codeproject.com/Articles/267023/Send-and-receive-json-between-android-and-php
     * @param json
     * @return
     */
    public static ArrayList<BrowserFilter> parseJson(String json,ExtraHolder browsetype)
    {
        String key = "";
        switch(browsetype.baseType)
        {
            case COUNTRY:
                key = "countries";
                break;
            case STAGE:
                key = "status";
                break;
            case MINERAL:
                key = "minerals";
                break;
        }

        ArrayList<BrowserFilter> filtervals =
                new ArrayList<BrowserFilter>();
        try
        {
            JSONObject obj = new JSONObject (json);
            if(!obj.has (key))
                return null;

            JSONArray jArray = obj.getJSONArray (key);

            for (int i = 0; i < jArray.length (); i++)
            {
                HashMap<String, String> map = new HashMap<String, String> ();
                JSONObject jsonFilterValue = jArray.getJSONObject (i);

                BrowserFilter filterval = parseJsonObject (jsonFilterValue);
                filterval.lastbrowsetype = browsetype;
                filtervals.add (filterval);
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return filtervals;

    }

    /**
     * Parses single object data from json object
     * @see http://www.codeproject.com/Articles/267023/Send-and-receive-json-between-android-and-php
     * @param json
     * @return
     */
    public static BrowserFilter parseJsonObject(JSONObject jFilterValue)
    {
        BrowserFilter obj = new BrowserFilter ();
        try
        {
            if(!jFilterValue.has ("name") ||
                    !jFilterValue.has ("htmlid"))
                return null;

            obj.name = jFilterValue.getString ("name");
            obj.htmlid = jFilterValue.getString ("htmlid");
        }
        catch(Exception e)
        {
            return null;
        }
        return obj;

    }

    /**
     * Will add this value and lastbrowsetype together
     * @return
     */
    public String withLastBrowseType()
    {
        return this.lastbrowsetype + "=" + this.htmlid;
    }

    public static ArrayList<BrowserFilter> loadlistByFilters(ExtraHolder browsetype, Activity activity, int versionCode)
    {
        boolean filefound = true;
        String filters = browsetype.getFilters (activity);
        Log.v(Constants.LOG_BWFILTER,filters);
        String filterCacheId = browsetype.getFilterCacheId ();

        ArrayList<BrowserFilter> filtervals = null;
        String api_browselisttype = browsetype.getBaseAPI (activity);
        String CACHEFILENAME = "filters." + versionCode + "." + filterCacheId;

        //check if serialized copy of project in local cache
        if (filefound)//found in local cache)
        {
            ObjectInputStream ois = null;
            FileInputStream fis = null;
            //load from local cache
            try
            {
                fis = activity.openFileInput (CACHEFILENAME);
                ois = new ObjectInputStream (fis);
                filtervals = (ArrayList<BrowserFilter>) ois.readObject ();
                if(filtervals == null)
                {
                    filefound = false;
                    activity.openFileInput (CACHEFILENAME);
                }
                else
                    Log.v (Constants.LOG_BWCACHEFOUND, CACHEFILENAME + "  FOUND!");

                ois.close ();
                fis.close ();
            } catch (FileNotFoundException e)
            {
                filefound = false;
            } catch (Exception e)
            {
                e.printStackTrace ();
                filefound=false;
            }

        }

        if (!filefound)
        {
            Log.v (Constants.LOG_BWCACHENOTFOUND, CACHEFILENAME + " not found");
            try
            {
                String url = activity.getResources ().getString (R.string.api_server)
                        + api_browselisttype
                        + filters
                        + "?lang=" + activity.getResources ().getString (R.string.api_q_lang)
                        + "&v=" + versionCode;
                Log.v (Constants.LOG_BWURL, url);
                String str_result = new ReaderTask ().execute (url).get ();
                BrowserFilter b = null;
                filtervals = b.parseJson (str_result, browsetype);

                ObjectOutputStream out = null;
                FileOutputStream fos = null;
                fos = activity.openFileOutput (CACHEFILENAME, Context.MODE_PRIVATE);
                out = new ObjectOutputStream (fos);
                out.writeObject (filtervals);
                Log.v (Constants.LOG_BWCACHESAVED, CACHEFILENAME + " SAVED");
                out.close ();
                fos.close ();

            } catch (Exception e)
            {

            }

        }//!filefound
        return filtervals;
    }
}
