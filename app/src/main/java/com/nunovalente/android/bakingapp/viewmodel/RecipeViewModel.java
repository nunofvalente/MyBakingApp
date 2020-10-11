package com.nunovalente.android.bakingapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nunovalente.android.bakingapp.data.RecipeRepository;
import com.nunovalente.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> mutableLiveData;
    private RecipeRepository repository;

    public void init() {
        if(mutableLiveData != null) {
            return;
        }
        repository = RecipeRepository.getInstance();
        mutableLiveData = repository.getRecipeList();
    }

    public LiveData<List<Recipe>> getRecipeRepository() {
        return mutableLiveData;
    }
}
