package com.basetechz.quizo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.basetechz.quizo.Home.popularCourse.Video.VideoModel;
import com.basetechz.quizo.databinding.ActivityWatchBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class WatchActivity extends AppCompatActivity {
    ActivityWatchBinding binding;
    FirebaseFirestore database;

    PlayerView playerView;
    ProgressBar progressBar;
    ImageView bt_fullScreen;
    ImageView bt_lockScreen;
    private SimpleExoPlayer players;
    ExoPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        playerView = findViewById(R.id.player);
        bt_fullScreen = findViewById(R.id.bt_fullScreen);
        bt_lockScreen = findViewById(R.id.exo_lock);

        // get access putExtra courseId from RecyclerPCLessonAdapter
        String courseId = getIntent().getStringExtra("courseId");
        // get access putExtra videoId from RecyclerPCLessonAdapter
        String videoId = getIntent().getStringExtra("videoId");


        // retrieve retrieve video url from Firebase FireStore
        database = FirebaseFirestore.getInstance();
        database.collection("popularCourse").document(courseId).collection("video")
                .document(videoId).collection("videos")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){


                    String videoUrl = documentSnapshot.getString("video_url");

                    player = new ExoPlayer.Builder(WatchActivity.this).build();

                    DataSource.Factory dataSourceFactory = new
                            DefaultDataSourceFactory(WatchActivity.this, Util.getUserAgent(WatchActivity.this,"Quizo"));

                    player.setPlayWhenReady(true);
                    player.prepare(new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(videoUrl)));
                    playerView.setPlayer(player);
                }
            }
        });


    }
}