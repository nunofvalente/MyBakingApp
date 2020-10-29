package com.nunovalente.android.bakingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Recipe;

public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {

    public static boolean mTwoPane = false;

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
            mRecipe = (Recipe) bundle.get(getResources().getString(R.string.RECIPE));
        }

        mTwoPane = findViewById(R.id.frame_recipe_details) != null;

        Bundle bundleSteps = new Bundle();
        if(mRecipe != null) {
            bundleSteps.putSerializable(getResources().getString(R.string.RECIPE_STEP), mRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
            recipeStepsFragment.setArguments(bundleSteps);
            fragmentManager.beginTransaction().replace(R.id.fragment_recipe_steps, recipeStepsFragment).commit();
        }
    }

    @Override
    public void onStepSelected(int id) {
        if(mTwoPane) {
            Bundle bundleDetails = new Bundle();
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            bundleDetails.putSerializable(getResources().getString(R.string.RECIPE), mRecipe);
            bundleDetails.putInt(getResources().getString(R.string.RECIPE_STEP), id);
            recipeDetailFragment.setArguments(bundleDetails);

            FragmentManager fragmentManagerDetails = getSupportFragmentManager();
            fragmentManagerDetails.beginTransaction().replace(R.id.frame_recipe_details, recipeDetailFragment).commit();

        } else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(getResources().getString(R.string.RECIPE), mRecipe);
            intent.putExtra(getResources().getString(R.string.STEP_SELECTED), id);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}