package com.basetechz.quizo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.basetechz.quizo.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    int points = 0;




    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswers = getIntent().getIntExtra("correct",0);
        int totalQuestions = getIntent().getIntExtra("total question",0);

        int points = correctAnswers*10;

        binding.score.setText(String.format("%d/%d",correctAnswers,totalQuestions));
        binding.earnedCoined.setText(String.valueOf(points));

    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}