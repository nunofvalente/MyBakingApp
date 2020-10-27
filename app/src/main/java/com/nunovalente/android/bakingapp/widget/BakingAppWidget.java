package com.nunovalente.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.activity.RecipeListActivity;
import com.nunovalente.android.bakingapp.activity.RecipeStepsActivity;
import com.nunovalente.android.bakingapp.model.Ingredient;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
        String recipeName = pref.getString(context.getString(R.string.recipe_pref_name), "");

        Intent intent = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 , intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        views.setTextViewText(R.id.tv_recipe_widget, recipeName);

        Intent serviceIntent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.list_ingredients_widget, serviceIntent);

        views.setOnClickPendingIntent(R.id.widget_relative_layout, pendingIntent);

       // views.setEmptyView(R.id.widget_relative_layout, R.id.tv_widget_default);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        IngredientService.startActionUpdateIngredients(context);
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

