package com.gary.exoplayer;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AnalyticsListener, PlayerControlView.VisibilityListener {

    private SimpleExoPlayer simpleExoPlayer;
    private Boolean isFullScreen = false;
    private PlayerView playerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.player_view);
        playerView.setControllerVisibilityListener(this);
        ImageButton igFullScreen = playerView.findViewById(R.id.exo_fullscreen);
        igFullScreen.setOnClickListener(this);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        simpleExoPlayer.addAnalyticsListener(this);
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
     * ?????? ??????
     */
    private void setFullScreen() {
        if (isFullScreen) {
            // ????????????
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
            //????????????
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
    public void onPlaybackStateChanged(EventTime eventTime, int state) {

        switch (state) {
            case Player.STATE_IDLE:
                //????????????????????????????????????

                break;
            case Player.STATE_BUFFERING:
                //?????????????????????????????????????????????????????????????????????????????????????????????????????????

                break;
            case Player.STATE_READY:
                //?????????????????????????????????????????????????????????{@link#getPlayWhenReady??????}???true??????????????????
                //???????????????????????????????????????????????????
                //?????????????????????????????????????????????????????????
                break;
            case Player.STATE_ENDED:
                //????????????????????????
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPlayerError(EventTime eventTime, ExoPlaybackException error) {
        switch (error.type) {

            case ExoPlaybackException.TYPE_SOURCE:
                // ??????????????????????????????
                break;
            case ExoPlaybackException.TYPE_RENDERER:
                // ???????????????
                break;
            case ExoPlaybackException.TYPE_UNEXPECTED:
                // ?????????????????????
                break;
            case ExoPlaybackException.TYPE_REMOTE:
                // ??????????????????
                break;
            default:
                break;

        }
    }

    @Override
    public void onVisibilityChange(int visibility) {

        //??????????????????????????????????????????(?????????????????????????????????)?????????/???????????????????????????
    }
}