package com.nunovalente.android.bakingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.nunovalente.android.bakingapp.R;



public class RecipeListActivity extends AppCompatActivity {

    private final static String TAG = RecipeListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecipeListFragment fragment = new RecipeListFragment(getApplicationContext());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_recipe_list, fragment)
                .commit();


    }


}