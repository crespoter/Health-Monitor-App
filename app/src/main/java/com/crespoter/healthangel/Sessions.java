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
    public void initialise(String id,String type)
    {
        prefs.edit().putString("type",type);
        prefs.edit().putString("userId",id).apply();
    }
    public String getId()
    {
        return prefs.getString("userId","");
    }

}
