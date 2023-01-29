package com.basetechz.quizo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.basetechz.quizo.databinding.ActivitySplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding binding;
    FirebaseAuth auth;
    Animation topAni,btmAni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        topAni = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        btmAni = AnimationUtils.loadAnimation(this,R.anim.buttom_animation);

        binding.image.setAnimation(topAni);
        binding.text.setAnimation(btmAni);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}