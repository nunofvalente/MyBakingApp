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

    public final static String EXO_PLAYBACK_POSITION = "playback_position";
    public final static String EXO_CURRENT_WINDOW = "exo_current_window";
    public final static String RECIPE_STEP = "recipe_step_number";

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



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_details, container, false);

        RecipeDetailActivity activity = (RecipeDetailActivity) getActivity();
        if (activity != null) {
            mRecipe = activity.getRecipe();
            context = getActivity().getApplicationContext();
            steps = mRecipe.getSteps();
            ingredients = mRecipe.getIngredients();
        }

        if(savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(EXO_PLAYBACK_POSITION);
            currentWindow = savedInstanceState.getInt(EXO_CURRENT_WINDOW);
            loadIngredientsInfo();
            stepNumber = savedInstanceState.getInt(RECIPE_STEP);
        }

        setListeners();

        return mBinding.getRoot();
    }

    private void initializePlayer(int currentWindow, long playbackPosition) {
        exoPlayer = new SimpleExoPlayer.Builder(context).build();
        mBinding.playerView.setPlayer(exoPlayer);

        String videoUrl = steps.get(stepNumber).getVideoURL();
        if(videoUrl.isEmpty()) {
            videoUrl = steps.get(stepNumber).getThumbnailURL();
        }

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
        exoPlayer.setMediaItem(mediaItem);

        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playbackPosition);
        exoPlayer.prepare();
    }

    private void loadIngredientsInfo() {
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
    }

    private void loadStepsInfo() {
        mBinding.tvSteps.setText(steps.get(stepNumber).getDescription());
    }

    private void setListeners() {
        mBinding.buttonNext.setOnClickListener(this);
        mBinding.buttonPrevious.setOnClickListener(this);
    }

    private void reloadInfo() {
        loadIngredientsInfo();
        loadStepsInfo();
        releasePlayer();
        playbackPosition = 0;
        currentWindow = 0;
        initializePlayer(currentWindow, playbackPosition);
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
        loadStepsInfo();
        loadIngredientsInfo();
        if (Util.SDK_INT >= 24) {
            initializePlayer(currentWindow, playbackPosition);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || exoPlayer == null)) {
            initializePlayer(currentWindow, playbackPosition);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(EXO_PLAYBACK_POSITION, playbackPosition);
        outState.putInt(EXO_CURRENT_WINDOW, currentWindow);
        outState.putInt(RECIPE_STEP, stepNumber);
    }
}
