package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vikas on 03/07/16.
 */
public class Project
{
    public String htmlid;
    public String name;
    //public int company_id;
    public String country;

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
                Project p = new Project();
                if(!jsonProject.has ("name") ||
                        !jsonProject.has ("htmlid") ||
                        !jsonProject.has ("countryName"))
                    return null;

                p.name = jsonProject.getString ("name");
                p.htmlid = jsonProject.getString ("htmlid");
                p.country = jsonProject.getString ("countryName");
                projects.add (p);
            }
        }
        catch(Exception e)
        {
            return null;
        }
        return projects;

    }
}
