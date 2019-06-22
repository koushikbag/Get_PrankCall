package com.kbinfo.prankcall.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static void setName(Context context, String name){
        SharedPreferences sharedpreferences = context.getSharedPreferences("PRANK_CALL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public static String getName(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("PRANK_CALL", Context.MODE_PRIVATE);
        return sharedpreferences.getString("name", "NA");
    }

    public static void setNumber(Context context, String name){
        SharedPreferences sharedpreferences = context.getSharedPreferences("PRANK_CALL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("number", name);
        editor.apply();
    }

    public static String getNumber(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("PRANK_CALL", Context.MODE_PRIVATE);
        return sharedpreferences.getString("number", "NA");
    }
}
