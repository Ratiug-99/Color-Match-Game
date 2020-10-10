package com.ratiug.dev.colormatchgame;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    public static final String APP_PREFERENCES_NAME = "APP_PREFERENCES_NAME";
    public static final String APP_PREFERENCES_RECORD = "APP_PREFERENCES_RECORD";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPreferencesHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
    }

    public int getRecord(){
      return  mSharedPreferences.getInt(APP_PREFERENCES_RECORD,0);
    }

    public void setRecord(int record){
        mEditor.putInt(APP_PREFERENCES_RECORD,record);
        mEditor.apply();
    }
}
