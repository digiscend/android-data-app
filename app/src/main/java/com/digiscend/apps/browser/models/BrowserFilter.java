package com.digiscend.apps.browser.models;

import com.digiscend.apps.browser.Activity.BrowseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

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
    public String lastbrowsetype;
    public String lasttypename;
    /**
     * @see http://www.codeproject.com/Articles/267023/Send-and-receive-json-between-android-and-php
     * @param json
     * @return
     */
    public static ArrayList<BrowserFilter> parseJson(String json,String browsetype,String typename)
    {
        String key = "";
        switch(browsetype)
        {
            case BrowseActivity.BROWSE_COUNTRY:
                key = "countries";
                break;
            case BrowseActivity.BROWSE_STAGE:
                key = "status";
                break;
            case BrowseActivity.BROWSE_METAL:
                key = "metals";
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
                filterval.lasttypename = typename;
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

    /**
     * Will save name and htmlid
     * @return
     */
    public String withLastName()
    {
        return this.lasttypename + "=" + this.name;
    }

}
