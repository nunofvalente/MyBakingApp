package com.nunovalente.android.bakingapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private final static String TAG = RecipeDetailActivity.class.getSimpleName();

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getIntentData();

        if(savedInstanceState != null) {
            return;
        } else {
            loadFragment();
        }
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            recipe = (Recipe) bundle.getSerializable(RecipeListFragment.RECIPE_OBJECT);
        } else {
            Log.d(TAG, "Recipe was not passed");
        }
    }

    private void loadFragment() {
        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_recipe_details, detailFragment).commit();
    }

    public Recipe getRecipe() {
        return recipe;
    }
}