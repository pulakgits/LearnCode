package com.basetechz.quizo;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.basetechz.quizo.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    // number of selected tab we have 4 tabs so value must lie between 1-4 ,
    // default value is 1 because first tab selected by default.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        



        // set home fragment by default
        // Its a Transaction fragment object
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.content,HomeFragment.class,null)
                .commit();



        // homeLayout setOnClickListener
        binding.homeLayout.setOnClickListener(v -> {

            // set Home Fragment
            getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.content,HomeFragment.class,null)
                .commit();

            // check if home Tab is selected or not


            // unselect other tab except home tab
                binding.rankTxt.setVisibility(View.GONE);
                binding.walletTxt.setVisibility(View.GONE);
                binding.profileTxt.setVisibility(View.GONE);

                binding.rankImage.setImageResource(R.drawable.rank_icon);
                binding.walletImage.setImageResource(R.drawable.wallet_icon);
                binding.profileImage.setImageResource(R.drawable.profile_icons);

            for (LinearLayout linearLayout : Arrays.asList(binding.rankLayout, binding.walletLayout, binding.profileLayout))
                linearLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // select home Tab
                binding.homeTxt.setVisibility(View.VISIBLE);
                binding.homeImage.setImageResource(R.drawable.home_selected_icon);
                binding.homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

                // create animation
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f,Animation.RELATIVE_TO_SELF,0.0f, Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                binding.homeLayout.startAnimation(scaleAnimation);
        });
        binding.rankLayout.setOnClickListener(v -> {



            // check if home Tab is selected or not

            // unselect other tab except home tab
                binding.homeTxt.setVisibility(View.GONE);
                binding.walletTxt.setVisibility(View.GONE);
                binding.profileTxt.setVisibility(View.GONE);

                binding.homeImage.setImageResource(R.drawable.home_icon);
                binding.walletImage.setImageResource(R.drawable.wallet_icon);
                binding.profileImage.setImageResource(R.drawable.profile_icons);

                binding.homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.walletLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                binding.rankTxt.setVisibility(View.VISIBLE);
                binding.rankImage.setImageResource(R.drawable.rank_selected_icon);
                binding.rankLayout.setBackgroundResource(R.drawable.round_back_rank_100);

                // create animation
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f,Animation.RELATIVE_TO_SELF,0.0f, Animation.RELATIVE_TO_SELF,0.0f);
            scaleAnimation.setDuration(200);
            scaleAnimation.setFillAfter(true);
            binding.rankLayout.startAnimation(scaleAnimation);

        });
        binding.walletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // set Wallet Fragment
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.content,WalletFragment.class,null)
                        .commit();

                // check if home Tab is selected or not

                // unselect other tab except home tab
                    binding.homeTxt.setVisibility(View.GONE);
                    binding.rankTxt.setVisibility(View.GONE);
                    binding.profileTxt.setVisibility(View.GONE);

                    binding.homeImage.setImageResource(R.drawable.home_icon);
                    binding.rankImage.setImageResource(R.drawable.rank_icon);
                    binding.profileImage.setImageResource(R.drawable.profile_icons);

                    binding.homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    binding.rankLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    binding.profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    binding.walletTxt.setVisibility(View.VISIBLE);
                    binding.walletImage.setImageResource(R.drawable.wallet_selected_icon);
                    binding.walletLayout.setBackgroundResource(R.drawable.round_back_wallet_100);

                // create animation
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f,Animation.RELATIVE_TO_SELF,0.0f, Animation.RELATIVE_TO_SELF,0.0f);
                scaleAnimation.setDuration(200);
                scaleAnimation.setFillAfter(true);
                binding.walletLayout.startAnimation(scaleAnimation);
            }
        });
        binding.profileLayout.setOnClickListener(this::onClick);


    }

    private void onClick(View v) {
        //set Profile Fragment
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.content, ProfileFragment.class, null)
                .commit();

        // check if home Tab is selected or not

        // unselect other tab except home tab
        binding.homeTxt.setVisibility(View.GONE);
        binding.rankTxt.setVisibility(View.GONE);
        binding.walletTxt.setVisibility(View.GONE);

        binding.homeImage.setImageResource(R.drawable.home_icon);
        binding.rankImage.setImageResource(R.drawable.rank_icon);
        binding.walletImage.setImageResource(R.drawable.wallet_icon);

        binding.homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        binding.rankLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        binding.walletLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        binding.profileTxt.setVisibility(View.VISIBLE);
        binding.profileImage.setImageResource(R.drawable.profile_selected_icon);
        binding.profileLayout.setBackgroundResource(R.drawable.round_back_profile_100);

        // create animation
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        scaleAnimation.setDuration(200);
        scaleAnimation.setFillAfter(true);
        binding.profileLayout.startAnimation(scaleAnimation);


    }
}