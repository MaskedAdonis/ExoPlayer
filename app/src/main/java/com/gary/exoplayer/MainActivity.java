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
     * 全屏 功能
     */
    private void setFullScreen() {
        if (isFullScreen) {
            // 取消全屏
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
            //设置全屏
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
                //播放器没有可播放的媒体。

                break;
            case Player.STATE_BUFFERING:
                //播放器无法立即从当前位置开始播放。这种状态通常需要加载更多数据时发生。

                break;
            case Player.STATE_READY:
                //播放器可以立即从当前位置开始播放。如果{@link#getPlayWhenReady（）}为true，否则暂停。
                //当点击暂停或者播放时都会调用此方法
                //当跳转进度时，进度加载完成后调用此方法
                break;
            case Player.STATE_ENDED:
                //播放器完成了播放
                Toast.makeText(this, "播放完成", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPlayerError(EventTime eventTime, ExoPlaybackException error) {
        switch (error.type) {

            case ExoPlaybackException.TYPE_SOURCE:
                // 播放器加载数据源异常
                break;
            case ExoPlaybackException.TYPE_RENDERER:
                // 渲染器异常
                break;
            case ExoPlaybackException.TYPE_UNEXPECTED:
                // 不被期望的异常
                break;
            case ExoPlaybackException.TYPE_REMOTE:
                // 远程组件异常
                break;
            default:
                break;

        }
    }

    @Override
    public void onVisibilityChange(int visibility) {

        //当你点击视频时，视频的控制器(即播放按钮，快进按钮等)被显示/隐藏时，调用此方法
    }
}