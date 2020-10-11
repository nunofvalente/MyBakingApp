package com.nunovalente.android.bakingapp.activity;

import android.content.Context;
import android.net.Uri;
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
import com.google.android.exoplayer2.util.Util;
import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.databinding.FragmentRecipeDetailsBinding;
import com.nunovalente.android.bakingapp.model.Ingredient;
import com.nunovalente.android.bakingapp.model.Recipe;
import com.nunovalente.android.bakingapp.model.Step;

import java.util.List;

public class RecipeDetailFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private SimpleExoPlayer exoPlayer;
    private FragmentRecipeDetailsBinding mBinding;
    private Recipe mRecipe;
    private List<Step> steps;
    private List<Ingredient> ingredients;
    private int stepNumber = 0;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    public RecipeDetailFragment() {
    }

    public RecipeDetailFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_details, container, false);

        RecipeDetailActivity activity = (RecipeDetailActivity) getActivity();
        if (activity != null) {
            mRecipe = activity.getRecipe();
        }

        steps = mRecipe.getSteps();
        ingredients = mRecipe.getIngredients();

        setListeners();

        return mBinding.getRoot();
    }

    private void initializePlayer(Context context) {
        exoPlayer = new SimpleExoPlayer.Builder(context).build();
        mBinding.playerView.setPlayer(exoPlayer);

        String videoUrl = steps.get(stepNumber).getVideoURL();
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
        exoPlayer.setMediaItem(mediaItem);

        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playbackPosition);
        exoPlayer.prepare();
    }

    private void loadRecipeInfo() {
        int counter = 1;
        if (stepNumber == 0) {
            mBinding.tvIngredients.setText("");
            for (Ingredient ingredient : ingredients) {
                if(counter == ingredients.size()) {
                    mBinding.tvIngredients.append(ingredient.toString());
                } else {
                    mBinding.tvIngredients.append(ingredient.toString() + "\n");
                    counter++;
                }
            }
        }

        mBinding.tvSteps.setText(steps.get(stepNumber).getDescription());
    }

    private void setListeners() {
        mBinding.buttonNext.setOnClickListener(this);
        mBinding.buttonPrevious.setOnClickListener(this);
    }

    private void reloadInfo() {
        loadRecipeInfo();
        releasePlayer();
        playbackPosition = 0;
        currentWindow = 0;
        initializePlayer(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_next:
                stepNumber++;
                reloadInfo();
                break;
            case R.id.button_previous:
                stepNumber--;
                reloadInfo();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadRecipeInfo();
        if (Util.SDK_INT >= 24) {
            initializePlayer(context);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || exoPlayer == null)) {
            initializePlayer(context);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
}
