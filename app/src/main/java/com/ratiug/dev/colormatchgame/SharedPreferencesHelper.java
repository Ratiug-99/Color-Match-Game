package com.ratiug.dev.colormatchgame;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    public static final String APP_PREFERENCES_NAME = "APP_PREFERENCES_NAME";
    public static final String APP_PREFERENCES_RECORD = "APP_PREFERENCES_RECORD";
    public static final String APP_PREFERENCE_COUNT_COLORS = "APP_PREFERENCE_COUNT_COLORS";
    public static final String APP_PREFERENCE_VIBRATION_STATUS = "APP_PREFERENCE_VIBRATION_STATUS";
    public static final String APP_PREFERENCE_LANGUAGE = "APP_PREFERENCE_LANGUAGE";
    public static final String APP_PREFERENCE_THEME = "APP_PREFERENCE_THEME";
    public static final String APP_PREFERENCE_TOKEN_USER_ID = "APP_PREFERENCE_TOKEN_USER_ID";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.apply();
    }

    public int getRecord() {
        return mSharedPreferences.getInt(APP_PREFERENCES_RECORD, 0);
    }
    public void setRecord(int record) {
        mEditor.putInt(APP_PREFERENCES_RECORD, record);
        mEditor.apply();
    }

    public int getCountColors() {
        return mSharedPreferences.getInt(APP_PREFERENCE_COUNT_COLORS, 0);
    }
    public void setCountColors(int count) {
        mEditor.putInt(APP_PREFERENCE_COUNT_COLORS, count);
        mEditor.apply();
    }

    public boolean getVibrationStatus() {
        return mSharedPreferences.getBoolean(APP_PREFERENCE_VIBRATION_STATUS, true);
    }
    public void setVibrationStatus(boolean status) {
        mEditor.putBoolean(APP_PREFERENCE_VIBRATION_STATUS, status);
        mEditor.apply();
    }

    public String getLanguage() {
        return mSharedPreferences.getString(APP_PREFERENCE_LANGUAGE, "");
    }
    public void setLanguage(String lang) {
        mEditor.putString(APP_PREFERENCE_LANGUAGE, lang);
        mEditor.apply();
    }

    public int getTheme() {
        return mSharedPreferences.getInt(APP_PREFERENCE_THEME, 1);
    }
    public void setTheme(int theme) {
        mEditor.putInt(APP_PREFERENCE_THEME, theme);
        mEditor.apply();
    }

    public String getTokenId() {
        return mSharedPreferences.getString(APP_PREFERENCE_TOKEN_USER_ID, null);
    }
    public void setTokenId(String tokenIdUser) {
        mEditor.putString(APP_PREFERENCE_TOKEN_USER_ID, tokenIdUser);
        mEditor.apply();
    }
}
