package com.nunovalente.android.bakingapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.adapter.RecipeAdapter;
import com.nunovalente.android.bakingapp.adapter.RecyclerClickListener;
import com.nunovalente.android.bakingapp.databinding.ActivityMainBinding;
import com.nunovalente.android.bakingapp.model.Recipe;
import com.nunovalente.android.bakingapp.util.NetworkUtils;
import com.nunovalente.android.bakingapp.util.SimpleIdlingResource;
import com.nunovalente.android.bakingapp.viewmodel.RecipeViewModel;
import com.nunovalente.android.bakingapp.widget.IngredientService;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity implements RecyclerClickListener {

    private final List<Recipe> mRecipeList = new ArrayList<>();
    private RecipeAdapter mAdapter;

    private ActivityMainBinding mBinding;

    @Nullable
    private static SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public static IdlingResource getIdlingResource() {
        if(mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        boolean isConnected = NetworkUtils.checkConnectivity(getApplication());

        if (isConnected) {
            showRecyclerView();
            configureRecyclerView(this);
            getRecipeList();
        } else {
            showConnectivityError();
        }

        getIdlingResource();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void configureRecyclerView(Context context) {
        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if(tabletSize) {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
                mBinding.recyclerRecipes.setLayoutManager(gridLayoutManager);
            }else {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
                mBinding.recyclerRecipes.setLayoutManager(gridLayoutManager);
            }
        } else {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            mBinding.recyclerRecipes.setLayoutManager(layoutManager);
        }

        mBinding.recyclerRecipes.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(context, mRecipeList, this);
        mBinding.recyclerRecipes.setAdapter(mAdapter);
    }

    private void getRecipeList() {
        mBinding.progressBar.setVisibility(View.VISIBLE);
        RecipeViewModel mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.init();
        mRecipeViewModel.getRecipeRepository().observe(this, recipes -> {
            mRecipeList.clear();
            mRecipeList.addAll(recipes);
            mAdapter.setValue(mRecipeList);
            mBinding.progressBar.setVisibility(View.INVISIBLE);
        });
    }

    public void showConnectivityError() {
        mBinding.tvNoInternet.setVisibility(View.VISIBLE);
        mBinding.recyclerRecipes.setVisibility(View.INVISIBLE);
    }

    public void showRecyclerView() {
        mBinding.tvNoInternet.setVisibility(View.INVISIBLE);
        mBinding.recyclerRecipes.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int position) {
        Recipe recipe = mRecipeList.get(position);

        SharedPreferences prefs = getApplicationContext().getSharedPreferences(getApplicationContext().getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();

        String json = gson.toJson(recipe);

        editor.putString(getApplicationContext().getString(R.string.recipe_pref_key), json);
        editor.putString(getApplicationContext().getString(R.string.recipe_pref_name), recipe.getName());
        editor.putInt(getApplicationContext().getString(R.string.recipe_pref_id), recipe.getId());
        editor.apply();

        IngredientService.startActionUpdateIngredients(getApplicationContext());

        Intent intent = new Intent(this, RecipeStepsActivity.class);
        intent.putExtra(getResources().getString(R.string.RECIPE), recipe);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}