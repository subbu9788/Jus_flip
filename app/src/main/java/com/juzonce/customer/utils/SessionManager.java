package com.juzonce.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.fragment.app.FragmentActivity;

import com.juzonce.customer.MainActivity;
import com.juzonce.customer.model.login.UserDetails;
import com.google.gson.Gson;


public class SessionManager {

    public static String KEY_ID = "key_id";
    public static String KEY_MOBILE = "9999999999";
    public static String KEY_NO = "PURPLE";
    public static String KET_PROFILE_PHOTO = "";
    public static String KEY_TOKEN = "token";
    public static String LANGUAGES = "English";
    public static String JSON_OBJECT = "json";
    public static String IS_SUBSCRIBE = "is_subscribe";
    private static String KEY_USERNAME = "key_username";
    private static String KEY_EMAIL = "key_email";
    private static String IS_LOGIN = "IsLogedIn";
    private SharedPreferences pref;
    private Editor editor;
    private FragmentActivity context;
    private int PRIVATE_MODE = 0;

    public SessionManager(FragmentActivity context) {
        this.context = context;
        this.pref = context.getSharedPreferences(KEY_NO, PRIVATE_MODE);
        this.editor = pref.edit();
    }

    public static boolean isSubscribe(FragmentActivity fragmentActivity) {
        SharedPreferences pref = fragmentActivity.getSharedPreferences(KEY_NO, 0);
        return pref.getBoolean(IS_SUBSCRIBE, false);
    }

    public static void updateSubscriptionDetails(FragmentActivity context, boolean isSubscribe) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        Editor editor = pref.edit();
        editor.putBoolean(IS_SUBSCRIBE, isSubscribe);
        editor.commit();
    }

    public static void updateLoginSession(FragmentActivity context, UserDetails details) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(details);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(JSON_OBJECT, json);
        editor.commit();

    }
    public static void isClear(FragmentActivity activity) {
        SharedPreferences pref = activity.getSharedPreferences(KEY_NO, 0);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
    public static String getToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        return pref.getString(KEY_TOKEN, KEY_TOKEN);
    }

    public static String getMobile(Context context) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        return pref.getString(KEY_MOBILE, KEY_MOBILE);
    }

    public static String getId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        return pref.getString(KEY_ID, KEY_ID);
    }

    public static String getEmailId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        return pref.getString(KEY_EMAIL, KEY_EMAIL);
    }

    public static String getName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        return pref.getString(KEY_USERNAME, KEY_USERNAME);
    }

    public static String getProfileImg(Context context) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        return pref.getString(KET_PROFILE_PHOTO, KET_PROFILE_PHOTO);
    }

    public static UserDetails getObject(Context context) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        Gson gson = new Gson();
        String json = pref.getString(JSON_OBJECT, "");
        return gson.fromJson(json, UserDetails.class);
    }

    public static void logoutUser(FragmentActivity fragmentActivity) {
        SharedPreferences pref = fragmentActivity.getSharedPreferences(KEY_NO, 0);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        MessageUtils.showToastMessage(fragmentActivity, "Logout Success", false);
        Intent i = new Intent(fragmentActivity, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fragmentActivity.startActivity(i);
        fragmentActivity.finish();
    }

    public static void upDateProfileImage(String photo, Context context) {
        SharedPreferences pref = context.getSharedPreferences(KEY_NO, 0);
        Editor editor = pref.edit();
        editor.putString(KET_PROFILE_PHOTO, photo);
        editor.commit();
    }

    public void createLoginSession(String details) {

        editor.putBoolean(IS_LOGIN, true);
        editor.putString(JSON_OBJECT, "");
        editor.commit();

    }

    public void createLoginSession(UserDetails details) {

        Gson gson = new Gson();
        String json = gson.toJson(details);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(JSON_OBJECT, json);
        editor.commit();

    }

    public void setlanguages(String lang) {
        editor.putString(LANGUAGES, lang);
        editor.commit();
    }

    public boolean checkLogin() {
        return this.isLoggedIn();

    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


}
