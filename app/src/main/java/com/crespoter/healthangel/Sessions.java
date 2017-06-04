package com.crespoter.healthangel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Sessions {
    private SharedPreferences prefs;
    public Sessions(Context cntx)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }
    public boolean isInitialised()
    {

        return prefs.contains("userId");
    }
    public void initialise(String id,String type,String name)
    {
        prefs.edit().putString("type",type);
        prefs.edit().putString("userId",id).apply();
        prefs.edit().putString("name",name).apply();
    }
    public String getId()
    {
        return prefs.getString("userId","");
    }
    public String getName()
    {
        return prefs.getString("name","");
    }

    public String getType()
    {
        return prefs.getString("type","");
    }



}
