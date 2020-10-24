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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState == null) {
            loadFragment();
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            recipe = (Recipe) bundle.getSerializable(RecipeStepsActivity.RECIPE_SELECTED);
            stepId = getIntent().getIntExtra(RecipeStepsActivity.STEP_SELECTED, 0);
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

    public int getStepId() {
        return stepId;
    }
}