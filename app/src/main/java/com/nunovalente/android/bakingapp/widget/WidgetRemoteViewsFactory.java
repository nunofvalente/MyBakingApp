package com.nunovalente.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Ingredient;
import com.nunovalente.android.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private Recipe mRecipe;
    private List<Ingredient> mIngredientsList = new ArrayList<>();

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.mContext = context;
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipe = WidgetData.getRecipeList(mContext);
        mIngredientsList = mRecipe.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredientsList != null) {
            return mIngredientsList.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_remote_view);
        remoteViews.setTextViewText(R.id.remoteView_ingredient_name, mRecipe.getIngredients().get(position).getIngredient());
        remoteViews.setTextViewText(R.id.remoteView_ingredient_quantity, String.valueOf(mRecipe.getIngredients().get(position).getQuantity()));
        remoteViews.setTextViewText(R.id.remoteView_ingredient_measure, mRecipe.getIngredients().get(position).getMeasure());
        return remoteViews;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
