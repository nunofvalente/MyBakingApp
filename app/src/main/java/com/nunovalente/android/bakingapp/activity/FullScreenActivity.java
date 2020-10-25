package com.nunovalente.android.bakingapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.util.Util;
import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.databinding.ActivityFullscreenBinding;
import com.nunovalente.android.bakingapp.model.Recipe;

public class FullScreenActivity extends AppCompatActivity {

    private String videoUrl;
    private SimpleExoPlayer exoPlayer;
    private ActivityFullscreenBinding mBinding;
    private ImageView mFullScreenButton;
    private Recipe mRecipe;
    private int stepNumber;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_fullscreen);

        mFullScreenButton = mBinding.exoPlayerFullscreen.findViewById(R.id.exo_fullscreen_icon);
        initializeFullScreenButton();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            playbackPosition = bundle.getLong(RecipeDetailFragment.EXO_PLAYBACK_POSITION);
            currentWindow = bundle.getInt(RecipeDetailFragment.EXO_CURRENT_WINDOW);
            videoUrl = bundle.getString(RecipeDetailFragment.VIDEO_URL);
            mRecipe = (Recipe) bundle.getSerializable(getResources().getString(R.string.RECIPE));
            stepNumber = bundle.getInt(getResources().getString(R.string.STEP_SELECTED));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializePlayer(int currentWindow, long playbackPosition) {
        if (getApplicationContext() != null) {
            exoPlayer = new SimpleExoPlayer.Builder(getApplicationContext()).build();
        }
        mBinding.exoPlayerFullscreen.setPlayer(exoPlayer);

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
        exoPlayer.setMediaItem(mediaItem);

        mBinding.exoPlayerFullscreen.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        exoPlayer.setVideoScalingMode(Renderer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playbackPosition);
        hideSystemUi();
        exoPlayer.prepare();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializeFullScreenButton() {
        mFullScreenButton = mBinding.exoPlayerFullscreen.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_fullscreen_skrink));
        mFullScreenButton.setOnClickListener(view -> {
            startDetailActivity();
        });
    }

    private void startDetailActivity() {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        playbackPosition = exoPlayer.getCurrentPosition();
        currentWindow = exoPlayer.getCurrentWindowIndex();
        intent.putExtra(RecipeDetailFragment.EXO_PLAYBACK_POSITION, playbackPosition);
        intent.putExtra(RecipeDetailFragment.EXO_CURRENT_WINDOW, currentWindow);
        intent.putExtra(getResources().getString(R.string.RECIPE), mRecipe);
        intent.putExtra(getResources().getString(R.string.STEP_SELECTED), stepNumber);
        startActivity(intent);
    }

    private void startStepActivity() {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(getResources().getString(R.string.RECIPE), mRecipe);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideSystemUi() {
        mBinding.exoPlayerFullscreen.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer(currentWindow, playbackPosition);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
    public void onBackPressed() {
        super.onBackPressed();
        startDetailActivity();
    }
}
