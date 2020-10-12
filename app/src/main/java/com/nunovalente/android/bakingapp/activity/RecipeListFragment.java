package com.nunovalente.android.bakingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.adapter.RecipeAdapter;
import com.nunovalente.android.bakingapp.adapter.RecyclerClickListener;
import com.nunovalente.android.bakingapp.model.Recipe;
import com.nunovalente.android.bakingapp.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeListFragment extends Fragment implements RecyclerClickListener {

    public final static String RECIPE_OBJECT = "recipe_object";

    private List<Recipe> mRecipeList = new ArrayList<>();
    private RecipeAdapter mAdapter;
    private RecyclerView mRecyclerRecipeList;
    private Context context;

    public RecipeListFragment() {
    }

    public RecipeListFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mRecyclerRecipeList = view.findViewById(R.id.recycler_recipes);

        getRecipeList();

        return view;
    }

    private void configureRecyclerView(Context context) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            mRecyclerRecipeList.setLayoutManager(layoutManager);
            mRecyclerRecipeList.setHasFixedSize(true);
    }

    private void getRecipeList() {
        RecipeViewModel mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.init();
        mRecipeViewModel.getRecipeRepository().observe(this, recipes -> {
            mRecipeList.addAll(recipes);
            mAdapter = new RecipeAdapter(context, mRecipeList, this);
            mRecyclerRecipeList.setAdapter(mAdapter);
            configureRecyclerView(context);
        });
    }

   @Override
    public void onClick(int position) {
        Recipe recipe = mRecipeList.get(position);
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_OBJECT, recipe);
        startActivity(intent);
    }
}
