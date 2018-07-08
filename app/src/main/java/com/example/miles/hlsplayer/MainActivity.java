package com.example.miles.hlsplayer;

import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.miles.hlsplayer.keyboard.KeyboardHeightObserver;
import com.example.miles.hlsplayer.keyboard.KeyboardHeightProvider;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;

public class MainActivity extends AppCompatActivity implements KeyboardHeightObserver {
    private static final Uri VIDEO_URI = Uri.parse("http://content.jwplatform.com/manifests/vM7nH0Kl.m3u8");
    private static final Uri ZONE3_VIDEO_URI = Uri.parse("https://beta-academy.nova.navercorp.com/live/SG/jamdevchannel3/video/21377_s_l_ab.m3u8");
    private static final int NUM_PAGES = 2;
    private PlayerView playerView;
    private ViewPager chatPager;
    private DefaultBandwidthMeter bandwidthMeter;
    private SimpleExoPlayer player;
    private KeyboardHeightProvider keyboardHeightProvider;
    private MediaSource videoSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.player_view);
        playerView.hideController();
        createPlayer();
        playerView.setPlayer(player);
        preparePlayer();

        chatPager = findViewById(R.id.chat_pager);
        chatPager.setAdapter(new ChatPagerAdapter(getSupportFragmentManager()));
        chatPager.setCurrentItem(1);

        keyboardHeightProvider = new KeyboardHeightProvider(this);
        // View가 붙을때 까지 기다렸다가 start
        /*findViewById(android.R.id.content).post(() -> {
            keyboardHeightProvider.start();
        });*/
        findViewById(android.R.id.content).addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                keyboardHeightProvider.start();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    private void createPlayer() {
        bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
    }

    private void preparePlayer() {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "HLSPlayer"), bandwidthMeter);
        videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .setPlaylistParser(new HlsPlaylistParser())
                .createMediaSource(VIDEO_URI);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addVideoListener(new VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

            }

            @Override
            public void onRenderedFirstFrame() {
                //resizeVideoProperly();
            }
        });
    }

    private void resizeVideoProperly() {
        Format videoFormat = player.getVideoFormat();
        Point displaySizePoint = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySizePoint);

        float videoRatio = videoFormat.width / (float) videoFormat.height;
        float screenRatio = displaySizePoint.x / (float) displaySizePoint.y;

        if (screenRatio <= videoRatio) {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
        } else {
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        keyboardHeightProvider.close();
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {

        // color the keyboard height view, this will stay when you close the keyboard
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) chatPager.getLayoutParams();
        layoutParams.bottomMargin = height;
        chatPager.setLayoutParams(layoutParams);
    }

    private class ChatPagerAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments = new Fragment[] {new EmptyFragment(), new ChatFragment()};

        public ChatPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments[i];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
