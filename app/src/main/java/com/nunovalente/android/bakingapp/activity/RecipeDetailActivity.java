package com.nunovalente.android.bakingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private final static String TAG = RecipeDetailActivity.class.getSimpleName();

    private Recipe recipe;
    private int stepId;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            recipe = (Recipe) bundle.getSerializable(getResources().getString(R.string.RECIPE));
            stepId = getIntent().getIntExtra(getResources().getString(R.string.STEP_SELECTED), 0);
            currentWindow = bundle.getInt(RecipeDetailFragment.EXO_CURRENT_WINDOW, 0);
            playbackPosition = bundle.getLong(RecipeDetailFragment.EXO_PLAYBACK_POSITION, 0);
        }

        if(savedInstanceState == null) {
            loadFragment();
        }
    }

    private void loadFragment() {
        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundleFragment = new Bundle();
        bundleFragment.putSerializable(getResources().getString(R.string.RECIPE), recipe);
        bundleFragment.putInt(getResources().getString(R.string.RECIPE_STEP), stepId);
        bundleFragment.putInt(RecipeDetailFragment.EXO_CURRENT_WINDOW, currentWindow);
        bundleFragment.putLong(RecipeDetailFragment.EXO_PLAYBACK_POSITION, playbackPosition);
        detailFragment.setArguments(bundleFragment);
        fragmentManager.beginTransaction().add(R.id.frame_recipe_details, detailFragment).commit();
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getStepId() {
        return stepId;
    }

}