package com.irfandev.project.simplemarket.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * created by Irfan Assidiq
 * email : assidiq.irfan@gmail.com
 **/
public class PrefsHelper {
    private SharedPreferences prefsHelp;
    private static String APPSNAME = "SIMPLEMARKET";

    private Context ctx;
    private static PrefsHelper instance;
    public static PrefsHelper sharedInstance(Context ctx){
        if (instance == null){
            instance = new PrefsHelper(ctx);
        }
        return instance;
    }

    public PrefsHelper(Context ctx){
        this.ctx = ctx;
        this.prefsHelp = ctx.getSharedPreferences(APPSNAME, Context.MODE_PRIVATE);
    }

    // untuk kebutuhan eksekusi data nantinya
    private String KEYLOGIN = "LOGINCHECK";
    private String KEYUSERNAME = "USERNAME";

    public boolean getStatusLogin(){
        return prefsHelp.getBoolean(KEYLOGIN, false);
    }

    public void setStatusLogin(boolean statusLogin){
        SharedPreferences.Editor editor = prefsHelp.edit();
        editor.putBoolean(KEYLOGIN, statusLogin);
        editor.apply();
    }

    public String getUsername(){
        return prefsHelp.getString(KEYUSERNAME, "");
    }
    public void setUsername(String username){
        SharedPreferences.Editor editor = prefsHelp.edit();
        editor.putString(KEYUSERNAME, username);
        editor.apply();
    }
}
