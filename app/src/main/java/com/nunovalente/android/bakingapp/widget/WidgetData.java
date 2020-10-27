package com.nunovalente.android.bakingapp.widget;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Recipe;

public class WidgetData {

    public static SharedPreferences preferences;

    public static Recipe getRecipeList(Context context) {
        preferences = context.getSharedPreferences(context.getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(context.getString(R.string.recipe_pref_key), "");
        return gson.fromJson(json, Recipe.class);
    }
}
