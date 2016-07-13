package com.digiscend.apps.browser.models;

import android.content.Context;
import android.util.Log;

import com.digiscend.apps.browser.R;
import com.digiscend.apps.browser.Task.ReaderTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

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

    public ArrayList<Project> majorProjects =
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

            processProjects("milestoneProjects",obj.milestoneProjects,jProject);
            processProjects("majorProjects", obj.majorProjects,jProject);

        }
        catch(Exception e)
        {
            return null;
        }
        return obj;

    }

    public static void processProjects(String key,ArrayList arrayList,JSONObject jProject)
    {
        if(jProject.has (key))
        {
            try
            {

                JSONArray jProjects2 = jProject.getJSONArray (key);

                for (int i = 0; i < jProjects2.length (); i++)
                {
                    HashMap<String, String> map = new HashMap<String, String> ();
                    JSONObject jsonProject = jProjects2.getJSONObject (i);

                    Project p = parseJsonObject (jsonProject);
                    arrayList.add (p);
                }

            }
            catch (Exception e)
            {

            }
        }
    }

    public static Project loadById(String htmlid,Context context)
    {
        Project currentProject = null;
        String CACHEFILENAME = "project." + htmlid;
        boolean filefound=true;
        File file = new File(CACHEFILENAME);

        //check if serialized copy of project in local cache
        if(filefound)//found in local cache)
        {
            ObjectInputStream ois = null;
            FileInputStream fis = null;
            //load from local cache
            try
            {
                fis = context.openFileInput (CACHEFILENAME);
                ois = new ObjectInputStream (fis);
                currentProject = (Project) ois.readObject();
                ois.close();
                fis.close ();
            }
            catch(FileNotFoundException e)
            {
                filefound=false;
            }
            catch(Exception e)
            {
                e.printStackTrace ();
            }

        }
        if(!filefound)
        {
            ArrayList<Project> projects = null;
            String str_result = null;
            try
            {
                String url = context.getResources ().getString (R.string.api_server)
                            + context.getResources ().getString (R.string.api_project)
                            + "/" + htmlid
                            + "?lang=" + context.getResources ().getString (R.string.api_q_lang);
                Log.v (Constants.LOG_PLURL, url);
                str_result = new ReaderTask ().execute (url).get ();
                projects = parseJson (str_result);
            } catch (InterruptedException e)
            {
                e.printStackTrace ();
            } catch (ExecutionException e)
            {
                e.printStackTrace ();
            }

            currentProject = projects.get (0);
            //save in local cache

            ObjectOutputStream out = null;
            FileOutputStream fos = null;
            try
            {
                fos = context.openFileOutput(CACHEFILENAME, Context.MODE_PRIVATE);
                out = new ObjectOutputStream(fos);
                out.writeObject (currentProject);
                out.close ();
                fos.close();
            } catch (Exception e)
            {
                e.printStackTrace ();
            }
        }
        return currentProject;
    }
}
