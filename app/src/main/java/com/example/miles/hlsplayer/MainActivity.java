package com.example.miles.hlsplayer;

import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
    private static final Uri VIDEO_URI = Uri.parse("https://devstreaming-cdn.apple.com/videos/streaming/examples/bipbop_16x9/bipbop_16x9_variant.m3u8");
    private static final Uri ZONE3_VIDEO_URI = Uri.parse("https://beta-academy.nova.navercorp.com/live/SG/jamdevchannel3/video/21377_s_l_ab.m3u8");
    private static final int NUM_PAGES = 2;
    private PlayerView playerView;
    private ViewPager chatPager;
    private DefaultBandwidthMeter bandwidthMeter;
    private ExoPlayer player;

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

        View rootView = playerView.getRootView();
        chatPager.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);

            int screenHeight = rootView.getHeight();
            int keyboardHeight = screenHeight - rect.bottom;

            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (keyboardHeight > 200) {
                layoutParams.bottomMargin = keyboardHeight;
            } else {
                layoutParams.bottomMargin = 0;
            }
            chatPager.setLayoutParams(layoutParams);
        });
    }

    private void createPlayer() {
        bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
    }

    private void preparePlayer() {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "HLSPlayer"), bandwidthMeter);
        MediaSource videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                                .setPlaylistParser(new HlsPlaylistParser())
                                .createMediaSource(VIDEO_URI);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
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
