package com.digiscend.apps.browser.models;

import android.app.Activity;
import android.content.Context;

import com.digiscend.apps.browser.R;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vikas on 16/07/16.
 */
public class ExtraHolder
        implements Serializable, Cloneable
{

    public enum baseView
    {
        COUNTRY,STAGE,MINERAL,PROJECTS,PROJECT
    };

    private ArrayList<String> filterInfoStrings;
    private String filterCacheId;

    public baseView baseType;

    public String mineral_name = "";
    public String mineral_filter = "";

    public String stage_name = "";
    public String stage_filter = "";

    public String country_name = "";
    public String country_filter = "";

    public String search_name = "";
    public String search_filter = "";

    public ExtraHolder(baseView baseType)
    {
        filterCacheId = "";
        this.filterInfoStrings = new ArrayList<String>();
        this.baseType = baseType;
    }

    public ExtraHolder(baseView baseType, String searchquery)
    {
        filterCacheId = "";
        this.filterInfoStrings = new ArrayList<String>();
        this.baseType = baseType;
        this.search_filter = searchquery;
    }

    public String getFilters(Context context)
    {
        filterCacheId = "";
        String filters = "";
        this.filterInfoStrings.clear ();
        if(this.country_filter.length ()>0)
        {
            filters += "/country/" + this.country_filter;
            filterCacheId += "-country-" + this.country_filter;
            this.filterInfoStrings.add ( context.getResources().getString(R.string.filterinfo_by_country) + ": " + this.country_name);
        }

        if(this.stage_filter.length ()>0)
        {
            filters += "/stage/" + this.stage_filter;
            filterCacheId += "-stage-" + this.stage_filter;
            this.filterInfoStrings.add ( context.getResources().getString(R.string.filterinfo_by_stages) + ": " + this.stage_name);
        }

        if(this.mineral_filter.length ()>0)
        {
            filters += "/mineral/" + this.mineral_filter;
            filterCacheId += "-mineral-" + this.mineral_filter;
            this.filterInfoStrings.add ( context.getResources().getString(R.string.filterinfo_by_mineral) + ": " + this.mineral_name);
        }

        if(this.search_filter.length ()>0)
        {
            filters += "/q/" + this.search_filter;
            filterCacheId += "-search-" + this.search_filter;
            this.filterInfoStrings.add ( context.getResources().getString(R.string.filterinfo_by_search) + ": " + this.search_filter);
        }

        return filters;
    }

    public ArrayList<String> getFilterInfoStrings()
    {
        if(this.filterInfoStrings != null)
            return filterInfoStrings;
        else
        {
            return null;
        }
    }

    public void addFilter(BrowserFilter val)
    {
        //take filter and adjust local object
        switch(val.lastbrowsetype.baseType)
        {
            case COUNTRY:
                this.country_filter = val.htmlid;
                this.country_name = val.name;
                break;
            case STAGE:
                this.stage_filter = val.htmlid;
                this.stage_name = val.name;
                break;
            case MINERAL:
                this.mineral_filter = val.htmlid;
                this.mineral_name = val.name;
                break;
        };
        this.baseType = baseView.PROJECTS;

    }

    public String getBaseAPI(Activity activity)
    {
        String api_browselisttype = "";

        switch(baseType)
        {
            case STAGE:
                api_browselisttype = Constants.API_STAGELIST;
                activity.setTitle (activity.getResources().getString(R.string.title_browse_stage));
                break;
            case COUNTRY:
                api_browselisttype = Constants.API_COUNTRYLIST;
                activity.setTitle (activity.getResources().getString(R.string.title_browse_country));
                break;
            case MINERAL:
                api_browselisttype = Constants.API_MINERALLIST;
                activity.setTitle (activity.getResources().getString(R.string.title_browse_mineral));
                break;
        }
        return api_browselisttype;
    }

    /**
     * Returns a unique astring format which can be used as filename to save cache
     * @return
     */
    public String getFilterCacheId()
    {
        String pre = "";
        switch(baseType)
        {
            case MINERAL:
                pre = "mineral";
                break;
            case STAGE:
                pre = "stage";
                break;
            case COUNTRY:
                pre = "country";
                break;
        }
        return pre + filterCacheId;
    }

    /**
     * Will switch baseView to filter
     * @param viewtype
     */
    public void setBaseType(baseView viewtype)
    {
        this.baseType = viewtype;
    }

    public ExtraHolder clone() {
        try {
            return (ExtraHolder)super.clone();
        }
        catch (CloneNotSupportedException e) {
            // This should never happen
        }
        return null;
    }



}
