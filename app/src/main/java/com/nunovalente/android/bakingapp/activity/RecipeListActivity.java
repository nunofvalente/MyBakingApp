package com.nunovalente.android.bakingapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.adapter.RecipeAdapter;
import com.nunovalente.android.bakingapp.adapter.RecyclerClickListener;
import com.nunovalente.android.bakingapp.databinding.ActivityMainBinding;
import com.nunovalente.android.bakingapp.model.Recipe;
import com.nunovalente.android.bakingapp.util.NetworkUtils;
import com.nunovalente.android.bakingapp.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;


public class RecipeListActivity extends AppCompatActivity implements RecyclerClickListener {

    private final static String TAG = RecipeListActivity.class.getSimpleName();
    public final static String RECIPE_OBJECT = "recipe_object";

    private List<Recipe> mRecipeList = new ArrayList<>();
    private RecipeAdapter mAdapter;

    private ActivityMainBinding mBinding;


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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        mBinding.recyclerRecipes.setLayoutManager(layoutManager);
        mBinding.recyclerRecipes.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(context, mRecipeList, this);
        mBinding.recyclerRecipes.setAdapter(mAdapter);
    }

    private void getRecipeList() {
        mBinding.progressBarMain.setVisibility(View.VISIBLE);
        RecipeViewModel mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.init();
        mRecipeViewModel.getRecipeRepository().observe(this, recipes -> {
            mRecipeList.clear();
            mRecipeList.addAll(recipes);
            mAdapter.setValue(mRecipeList);
        });
        mBinding.progressBarMain.setVisibility(View.INVISIBLE);
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
        Intent intent = new Intent(this, RecipeStepsActivity.class);
        intent.putExtra(RECIPE_OBJECT, recipe);
        startActivity(intent);
    }
}