package com.devianggraini.utsapilkasicatering.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.devianggraini.utsapilkasicatering.admin.MenuHome;
import com.devianggraini.utsapilkasicatering.users.home_user;

public class PrefSetting {
    public static String _id;
    public static String userName;
    public static String no_telp;
    public static String alamat;
    public static String role;
    Activity activity;

    public PrefSetting(Activity activity){
        this.activity = activity;
    }

    public SharedPreferences getSharePreferances(){
        SharedPreferences preferences = activity.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return preferences;
    }

    public void isLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        if (session.isLoggedIn()){
            pref = getSharePreferances();
            _id = pref.getString("_id","");
            userName = pref.getString("userName","");
            no_telp = pref.getString("no_telp","");
            alamat = pref.getString("alamat","");
            role = pref.getString("role","");
        } else {
            session.setLogin(false);
            session.setSessid(0);
            Intent i = new Intent(activity, activity.getClass());
            activity.startActivity(i);
            activity.finish();
        }
    }

    public void checkLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        _id = pref.getString("_id","");
        userName = pref.getString("userName","");
        no_telp = pref.getString("no_telp","");
        alamat = pref.getString("alamat","");
        role = pref.getString("role","");
        if (session.isLoggedIn()){
            if (role.equals("1")){
                Intent i = new Intent(activity, MenuHome.class);
                activity.startActivity(i);
                activity.finish();
            } else {
                Intent i = new Intent(activity, home_user.class);
                activity.startActivity(i);
                activity.finish();
            }
        }
    }

    public void storeRegIdSharedPreferences(Context context, String _id, String userName,
                                            String no_telp, String alamat, String role, SharedPreferences prefs){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("_id", _id);
        editor.putString("userName", userName);
        editor.putString("no_telp", no_telp);
        editor.putString("alamat", alamat);
        editor.putString("role", role);
        editor.commit();

    }
}
