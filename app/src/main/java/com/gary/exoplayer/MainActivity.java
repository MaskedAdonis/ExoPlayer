package com.gary.exoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SimpleExoPlayer simpleExoPlayer;
    private Boolean isFullScreen = false;
    private PlayerView playerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.player_view);
        ImageButton igFullScreen = playerView.findViewById(R.id.exo_fullscreen);
        igFullScreen.setOnClickListener(this);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        // Build the media item.
       /* MediaItem mediaItem = MediaItem.fromUri("https://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4");
        simpleExoPlayer.setMediaItem(mediaItem);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();*/

        // Create a data source factory.
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory();
        // Create a progressive media source pointing to a stream uri.
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri("https://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"));
        // Set the media source to be played.
        simpleExoPlayer.setMediaSource(mediaSource);
        // Prepare the player.
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exo_fullscreen:

                setFullScreen();
                break;
            default:
                break;

        }
    }


    /***
     *
     * 全屏 功能
     */
    private void setFullScreen() {
        if (isFullScreen) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
            playerView.setLayoutParams(params);
            isFullScreen = false;
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            playerView.setLayoutParams(params);
            isFullScreen = true;
        }


    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}