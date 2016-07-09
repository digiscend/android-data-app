package com.digiscend.apps.browser.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vikas on 03/07/16.
 */
public class Company implements Serializable
{
    public String htmlid;
    public String name;
    public String logosrc;
    //public int company_id;
    //public String country;

    public Company() {

    }

    /**
     * @see http://www.codeproject.com/Articles/267023/Send-and-receive-json-between-android-and-php
     * @param json
     * @return
     */
    public static ArrayList<Company> parseJson(String json)
    {
        ArrayList<Company> companies =
                new ArrayList<Company>();
        try
        {
            JSONObject obj = new JSONObject (json);
            if(!obj.has ("projects"))
                return null;

            JSONArray jProjects = obj.getJSONArray ("projects");

            for (int i = 0; i < jProjects.length (); i++)
            {
                HashMap<String, String> map = new HashMap<String, String> ();
                JSONObject jCompany = jProjects.getJSONObject (i);
                Company obji = parseJsonObject(jCompany);
                companies.add (obji);
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return companies;

    }

    /**
     * Parses single object data from json object
     * @see http://www.codeproject.com/Articles/267023/Send-and-receive-json-between-android-and-php
     * @param json
     * @return
     */
    public static Company parseJsonObject(JSONObject jCompany)
    {
        Company obj = new Company ();
        try
        {
                Company p = new Company ();
                if(!jCompany.has ("name") ||
                        !jCompany.has ("logosrc") ||
                        !jCompany.has ("htmlid"))
                    return null;

            obj.name = jCompany.getString ("name");
            obj.htmlid = jCompany.getString ("htmlid");
            obj.logosrc = jCompany.getString ("logosrc");
                //p.country = jsonProject.getString ("countryName");

        }
        catch(Exception e)
        {
            return null;
        }
        return obj;

    }
}
