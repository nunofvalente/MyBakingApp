package com.nunovalente.android.bakingapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.databinding.FragmentRecipeDetailsBinding;

public class RecipeDetailFragment extends Fragment {

    private Context context;
    private SimpleExoPlayer exoPlayer;
    private FragmentRecipeDetailsBinding mBinding;

    public RecipeDetailFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_details, container, false);


        return mBinding.getRoot();
    }

    private void initializePlayer(Context context) {
        exoPlayer = new SimpleExoPlayer.Builder(context).build();
        mBinding.playerView.setPlayer(exoPlayer);


    }
}
