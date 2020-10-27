package com.nunovalente.android.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.nunovalente.android.bakingapp.R;

public class IngredientService extends IntentService {

    public static final String ACTION_SHOW_INGREDIENTS = "com.nunovalente.android.bakingapp.action.show_ingredients";

    public IngredientService() {
        super("IngredientService");
    }


    private void handleActionUpdateIngredients() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_ingredients_widget);

        BakingAppWidget.updateBakingWidgets(this, appWidgetManager, appWidgetIds);
    }

    public static void startActionUpdateIngredients(Context context) {
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_SHOW_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_SHOW_INGREDIENTS.equals(action)) {
                handleActionUpdateIngredients();
            }
        }
    }
}
