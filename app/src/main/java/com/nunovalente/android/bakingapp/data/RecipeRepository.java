package com.nunovalente.android.bakingapp.data;

import androidx.lifecycle.MutableLiveData;

import com.nunovalente.android.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeRepository {

    private static WebService service;
    private static MutableLiveData<List<Recipe>> recipeList = new MutableLiveData<>();

    private static RecipeRepository repository;

    public static RecipeRepository getInstance() {
        if (repository == null) {
            repository = new RecipeRepository();
        }
        return repository;
    }

    public RecipeRepository() {
        service = RetrofitService.getInterface();
    }

    public MutableLiveData<List<Recipe>> getRecipeList() {
        Call<List<Recipe>> listOfRecipes = service.getRecipes();
        listOfRecipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    recipeList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                recipeList.setValue(null);
                t.printStackTrace();
            }
        });
        return recipeList;
    }
}

