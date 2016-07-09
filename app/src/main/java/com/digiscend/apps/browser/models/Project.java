package com.digiscend.apps.browser.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vikas on 03/07/16.
 */
public class Project implements Serializable
{
    public String htmlid;
    public String name;
    //public int company_id;
    public String country;
    public Company company = null;
    public String intro;
    public String year;

    public ArrayList<Project> milestoneProjects =
            new ArrayList<Project>();

    public ArrayList<AttrValue> attrvalues =
            new ArrayList<AttrValue>();

    public Project() {

    }

    /**
     * @see http://www.codeproject.com/Articles/267023/Send-and-receive-json-between-android-and-php
     * @param json
     * @return
     */
    public static ArrayList<Project> parseJson(String json)
    {
        ArrayList<Project> projects =
                new ArrayList<Project>();
        try
        {
            JSONObject obj = new JSONObject (json);
            if(!obj.has ("projects"))
                return null;

            JSONArray jProjects = obj.getJSONArray ("projects");

            for (int i = 0; i < jProjects.length (); i++)
            {
                HashMap<String, String> map = new HashMap<String, String> ();
                JSONObject jsonProject = jProjects.getJSONObject (i);

                Project p = parseJsonObject (jsonProject);
                projects.add (p);
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return projects;

    }

    /**
     * Parses single object data from json object
     * @see http://www.codeproject.com/Articles/267023/Send-and-receive-json-between-android-and-php
     * @param json
     * @return
     */
    public static Project parseJsonObject(JSONObject jProject)
    {
        Project obj = new Project ();
        try
        {
            if(!jProject.has ("name") ||
                    !jProject.has ("htmlid") ||
                    !jProject.has ("intro") ||
                    !jProject.has ("countryName"))
                return null;

            obj.name = jProject.getString ("name");
            obj.htmlid = jProject.getString ("htmlid");
            obj.country = jProject.getString ("countryName");
            obj.intro = jProject.getString ("intro");

            if(jProject.has("year"))
                obj.year = jProject.getString ("year");

            if(jProject.has ("selectedCompany"))
            {
                Company c = null;
                obj.company = c.parseJsonObject(jProject.getJSONObject ("selectedCompany"));
            }

            AttrValue av = new AttrValue ();
            if(jProject.has("attributevalues"))
            {
                obj.attrvalues = av.parseJsonObject(jProject.getJSONObject ("attributevalues"));
            }

            if(jProject.has ("milestoneProjects"))
            {
                JSONArray jProjects2 = jProject.getJSONArray ("milestoneProjects");

                for (int i = 0; i < jProjects2.length (); i++)
                {
                    HashMap<String, String> map = new HashMap<String, String> ();
                    JSONObject jsonProject = jProjects2.getJSONObject (i);

                    Project p = parseJsonObject (jsonProject);
                    obj.milestoneProjects.add (p);
                }
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return obj;

    }

}
