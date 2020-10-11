package com.nunovalente.android.bakingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.databinding.ActivityMainBinding;


public class RecipeListActivity extends AppCompatActivity {

    private final static String TAG = RecipeListActivity.class.getSimpleName();

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        RecipeListFragment fragment = new RecipeListFragment(getApplicationContext());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frame_recipe_list, fragment)
                .commit();

    }


}