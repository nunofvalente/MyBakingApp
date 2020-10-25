package com.nunovalente.android.bakingapp.activity;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.util.Util;
import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.databinding.FragmentRecipeDetailsBinding;
import com.nunovalente.android.bakingapp.model.Recipe;
import com.nunovalente.android.bakingapp.model.Step;

import java.util.List;
import java.util.Objects;

public class RecipeDetailFragment extends Fragment implements View.OnClickListener {

    public final static String EXO_PLAYBACK_POSITION = "playback_position";
    public final static String EXO_CURRENT_WINDOW = "exo_current_window";
    public final static String RECIPE_STEP = "recipe_step_number";

    private SimpleExoPlayer exoPlayer;
    private FragmentRecipeDetailsBinding mBinding;
    private List<Step> steps;
    private int stepNumber = 0;
    private ImageView mFullScreenButton;
    private boolean fullScreen;


    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private boolean mTwoPane;

    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_details, container, false);

        mTwoPane = RecipeStepsActivity.mTwoPane;
        mFullScreenButton = mBinding.exoPlayerView.findViewById(R.id.exo_fullscreen_icon);



        if (getArguments() != null) {
            Recipe mRecipe = (Recipe) getArguments().get(RecipeDetailActivity.RECIPE);
            stepNumber = getArguments().getInt(RecipeDetailActivity.RECIPE_STEP);
            if (mRecipe != null) {
                steps = mRecipe.getSteps();
            }
        }

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(EXO_PLAYBACK_POSITION);
            currentWindow = savedInstanceState.getInt(EXO_CURRENT_WINDOW);
            stepNumber = savedInstanceState.getInt(RECIPE_STEP);
        }

        setListeners();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            initializeFullScreenButton();
        }

        return mBinding.getRoot();
    }


    private void initializePlayer(int currentWindow, long playbackPosition) {
        if (getContext() != null) {
            exoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
        }
        mBinding.exoPlayerView.setPlayer(exoPlayer);

        String videoUrl = steps.get(stepNumber).getVideoURL();
        if (videoUrl.isEmpty()) {
            videoUrl = steps.get(stepNumber).getThumbnailURL();
        }

        if (!videoUrl.equals("")) {
            mBinding.playerFrame.setVisibility(View.VISIBLE);
            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
            exoPlayer.setMediaItem(mediaItem);

            mBinding.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            exoPlayer.setVideoScalingMode(Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayer.seekTo(currentWindow, playbackPosition);
            exoPlayer.prepare();
        } else {
            mBinding.playerFrame.setVisibility(View.GONE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideSystemUi() {
        mBinding.exoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void loadStepsInfo() {
        mBinding.tvSteps.setText(steps.get(stepNumber).getDescription());
        if (mTwoPane) {
            mBinding.buttonPrevious.setVisibility(View.GONE);
            mBinding.buttonNext.setVisibility(View.GONE);
        } else {
            processPreviousButton();
            processNextButton();
        }
    }

    private void processNextButton() {
        if (stepNumber == 0) {
            mBinding.buttonPrevious.setVisibility(View.GONE);
        } else {
            mBinding.buttonPrevious.setVisibility(View.VISIBLE);
        }
    }

    private void processPreviousButton() {
        if (stepNumber + 1 == steps.size()) {
            mBinding.buttonNext.setVisibility(View.GONE);
        } else {
            mBinding.buttonNext.setVisibility(View.VISIBLE);
        }
    }

    private void setListeners() {
        mBinding.buttonNext.setOnClickListener(this);
        mBinding.buttonPrevious.setOnClickListener(this);
    }

    private void reloadInfo() {
        loadStepsInfo();
        releasePlayer();
        playbackPosition = 0;
        currentWindow = 0;
        initializePlayer(currentWindow, playbackPosition);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeFullScreenButton() {
        mFullScreenButton = mBinding.exoPlayerView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton.setOnClickListener(view -> {
            if(fullScreen) {
                mFullScreenButton.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_fullscreen_expand));
                if(getActivity() != null) {
                    getActivity().getActionBar().show();
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mBinding.exoPlayerView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = 300;
                mBinding.playerFrame.setLayoutParams(params);
                fullScreen = false;
            }else{
                mFullScreenButton.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_fullscreen_skrink));
                if(getActivity() != null) {
                    hideSystemUi();
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mBinding.exoPlayerView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                mBinding.exoPlayerView.setLayoutParams(params);
                fullScreen = true;
            }
        });
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
        if (Util.SDK_INT >= 24) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                hideSystemUi();
            }
            initializePlayer(currentWindow, playbackPosition);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || exoPlayer == null)) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                hideSystemUi();
            }
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
