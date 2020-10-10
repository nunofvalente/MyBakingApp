package com.nunovalente.android.bakingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.adapter.RecipeAdapter;
import com.nunovalente.android.bakingapp.adapter.RecyclerClickListener;
import com.nunovalente.android.bakingapp.data.RetrofitService;
import com.nunovalente.android.bakingapp.data.WebService;
import com.nunovalente.android.bakingapp.databinding.ActivityMainBinding;
import com.nunovalente.android.bakingapp.model.Recipe;
import com.nunovalente.android.bakingapp.utilities.JsonUtil;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements RecyclerClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;
    private RecipeAdapter mAdapter;
    private List<Recipe> mRecipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        configureRecyclerView();
        getRecipes();

    }

    private void configureRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerRecipes.setLayoutManager(layoutManager);
        mBinding.recyclerRecipes.setHasFixedSize(true);
       mAdapter = new RecipeAdapter(MainActivity.this, mRecipeList, MainActivity.this);
       mBinding.recyclerRecipes.setAdapter(mAdapter);

    }


   private void getRecipes() {
        Retrofit retrofit = RetrofitService.getRetrofit();
        WebService service = retrofit.create(WebService.class);
        Call<List<Recipe>> call = service.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipeJSON = response.body();

               // ArrayList<Recipe> mParsedRecipeList = JsonUtil.parseJSON(recipeJSON);
                mRecipeList.addAll(recipeJSON);
                mAdapter = new RecipeAdapter(MainActivity.this, mRecipeList, MainActivity.this);
                mBinding.recyclerRecipes.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onClick() {

    }
}