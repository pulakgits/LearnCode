package com.basetechz.quizo.Home.popularCourse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.basetechz.quizo.Home.popularCourse.Video.RecyclerPCLessonAdapter;
import com.basetechz.quizo.Home.popularCourse.Video.ViewPagerLessonAdapter;
import com.basetechz.quizo.R;
import com.basetechz.quizo.databinding.ActivityLessonBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class LessonActivity extends AppCompatActivity {
    ActivityLessonBinding binding;
    FirebaseFirestore database;
    RecyclerPCLessonAdapter adapter;
    TabLayout tab;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLessonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseFirestore.getInstance();

        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        // set Linear Layout on recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(LessonActivity.this, RecyclerView.VERTICAL,false);
        binding.lessonRecycler.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        String courseName = getIntent().getStringExtra("courseName");
        String courseId = intent.getStringExtra("courseId");
        binding.courseTxt.setText(courseName);

        ViewPagerLessonAdapter adapterViewPager = new ViewPagerLessonAdapter(getSupportFragmentManager(),courseId);
        viewPager.setAdapter(adapterViewPager);
        tab.setupWithViewPager(viewPager);

        binding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the share Intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share our app");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out our app: [https://chat.openai.com/chat/63a59290-fbe7-4642-89ee-9d5fa41e1227]");

                // Show the share dialog
                startActivity(Intent.createChooser(shareIntent, "Share our app using"));

            }
        });



    }
}