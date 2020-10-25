package com.nunovalente.android.bakingapp.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    private final static String TAG = RecipeDetailActivity.class.getSimpleName();

    public static final String RECIPE = "recipe";
    public static final String RECIPE_STEP = "recipe_step";


    private Recipe recipe;
    private int stepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            recipe = (Recipe) bundle.getSerializable(RecipeStepsActivity.RECIPE_SELECTED);
            stepId = getIntent().getIntExtra(RecipeStepsActivity.STEP_SELECTED, 0);
        }

        if(savedInstanceState == null) {
            loadFragment();
        }
    }

    private void loadFragment() {
        RecipeDetailFragment detailFragment = new RecipeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundleFragment = new Bundle();
        bundleFragment.putSerializable(RECIPE, recipe);
        bundleFragment.putInt(RECIPE_STEP, stepId);
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