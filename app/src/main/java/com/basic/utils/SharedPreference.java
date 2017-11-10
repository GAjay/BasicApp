package com.basic.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Shared prefences to hold the prefences of the application.
 */
public class SharedPreference {

    private static final int MODE_PRIVATE=0;
    private final SharedPreferences preferences;

    public SharedPreference(Context context){
        String userPref = "Dwell";
        preferences=context.getSharedPreferences(userPref,MODE_PRIVATE);
    }

    public void storeDeviceToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ReferenceToken", token);
        editor.commit();
    }
    public String getDeviceToken(){
        return preferences.getString("ReferenceToken","");
    }


    public void storeEntryId(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("entryId", token);
        editor.commit();
    }
    public String getEntryId(){
        return preferences.getString("entryId","");
    }

    public void storeFirstName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("firstName", name);
        editor.commit();
    }

    public String getFirstName() {
        return preferences.getString("firstName", "");
    }

    public void storeLastName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lastName", name);
        editor.commit();
    }

    public String getLastName() {
        return preferences.getString("lastName", "");
    }

    public void storeRoomLocationId(String title) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("roomLocationId", title);
        editor.commit();
    }

    public String getRoomLocationId() {
        return preferences.getString("roomLocationId", "");
    }

    public void storeNameTitle(String title) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nameTitle", title);
        editor.commit();
    }

    public String getNameTitle() {
        return preferences.getString("nameTitle", "");
    }


    public void storeRoomSpaceId(String title) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("roomSpaceId", title);
        editor.commit();
    }

    public String getRoomSpaceId() {
        return preferences.getString("roomSpaceId", "");
    }


    public boolean getLogout(Context context) {
        SharedPreferences.Editor editor = preferences.edit();
        String deviceToken = new com.basic.utils.SharedPreference(context).getDeviceToken();
        editor.clear().commit();
        new com.basic.utils.SharedPreference(context).storeDeviceToken(deviceToken);
        return true;
    }

   /* public void storeDeviceToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ReferenceToken", token);
        editor.commit();
    }
    public String getDeviceToken(){
        return preferences.getString("ReferenceToken","");
    }

    public void storeDeviceToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ReferenceToken", token);
        editor.commit();
    }
    public String getDeviceToken(){
        return preferences.getString("ReferenceToken","");
    }*/

}
