package com.nunovalente.android.bakingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Recipe;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    public final static String RECIPE_STEP = "recipe_step";
    public final static String RECIPE_SELECTED = "recipe_selected";
    public final static String STEP_SELECTED = "ste_selected";

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        if(getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mRecipe = (Recipe) bundle.get(RecipeListActivity.RECIPE_OBJECT);
        }

        Bundle bundleSteps = new Bundle();
        if(mRecipe != null) {
            bundleSteps.putSerializable(RECIPE_STEP, mRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setArguments(bundleSteps);
            fragmentManager.beginTransaction().replace(R.id.fragment_recipe_steps, recipeStepsFragment).commit();
        }
    }

    @Override
    public void onStepSelected(int id) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_SELECTED, mRecipe);
        intent.putExtra(STEP_SELECTED, id);
        startActivity(intent);
    }
}