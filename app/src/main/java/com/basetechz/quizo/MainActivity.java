package com.basetechz.quizo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basetechz.quizo.Home.HomeFragment;
import com.basetechz.quizo.Home.popularCourse.RecyclerPopularCourseAdapter;
import com.basetechz.quizo.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseDatabase db;
    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int color;
    int txtColor;
    TextView switch_theme1;
    DrawerLayout drawerLayout;
    androidx.appcompat.widget.Toolbar toolBar;
    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
    TextView name;
    TextView email;
    ImageView phoUrl;
    User user;
    FirebaseAuth mAuth;


    RecyclerPopularCourseAdapter adapter;

    // number of selected tab we have 4 tabs so value must lie between 1-4 ,
    // default value is 1 because first tab selected by default.
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        switch_theme1 = findViewById(R.id.switch_theme1);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolBar = findViewById(R.id.toolBar);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoUrl = findViewById(R.id.photoUrl);
        setSupportActionBar(toolBar);
        mAuth = FirebaseAuth.getInstance();


        // create ActionBarDrawerToggle object
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolBar,R.string.OpenDrawer,R.string.CloseDrawer);




        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id){
                    case R.id.optWallet:
                        startActivity(new Intent(MainActivity.this,WalletActivity.class));
                        break;
                    case R.id.optLeaderboard:
                        binding.toolBarText.setText("LeaderBoard");
                        binding.leadTxt.setTextColor(color);
                        binding.leadImage.setImageResource(R.drawable.leadboard_icon_select);
                        binding.homeTxt.setTextColor(txtColor);
                        binding.homeImage.setImageResource(R.drawable.home_icons);
                       loadFragment(new LeaderboardFragment());
                       break;

                    case R.id.optInvite:

                    case R.id.optsetting:
                    case R.id.optLearnCodePro:

                }

                onBackPressed();

                return true;
            }
        });










        // night mode and light mode function
//        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
//        nightMODE = sharedPreferences.getBoolean("night", false);// light mode is the default mode
//        switch_theme1 = findViewById(R.id.switch_theme1);
//        if(nightMODE) {
//            switch_theme1.setClickable(true);
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        }else{
//            switch_theme1.setClickable(false);
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//        switch_theme1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(nightMODE){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    editor = sharedPreferences.edit();
//                    editor.putBoolean("night", false);
//                }else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    editor = sharedPreferences.edit();
//                    editor.putBoolean("night", true);
//                }
//                editor.apply();
//            }
//        });

        color = Color.parseColor("#E91E63");
        txtColor = Color.parseColor("#9C9C9C");


        // set home fragment by default
        // Its a Transaction fragment object
        loadFragment(new HomeFragment());
        binding.homeTxt.setTextColor(color);
        binding.homeImage.setImageResource(R.drawable.home_icons_select);
        binding.homeLayout.setBackgroundResource(R.drawable.round_back_home_100);


        // homeLayout setOnClickListener
        binding.homeLayout.setOnClickListener(v -> {
            binding.toolBarText.setText("Home");

            // set Home Fragment
            loadFragment(new HomeFragment());

            // check if home Tab is selected or not


            // unselect other tab except home tab

            binding.leadImage.setImageResource(R.drawable.leadboard_icon);
            binding.leadTxt.setTextColor(txtColor);
            binding.exploreImage.setImageResource(R.drawable.explore_icon);
            binding.exploreTxt.setTextColor(txtColor);
            binding.profileImage.setImageResource(R.drawable.profile_iconn);
            binding.profileTxt.setTextColor(txtColor);

            for (LinearLayout linearLayout : Arrays.asList(binding.leadLayout, binding.exploreLayout, binding.profileLayout))
                linearLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            // select home Tab

            binding.homeTxt.setTextColor(color);
            binding.homeImage.setImageResource(R.drawable.home_icons_select);
            binding.homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

            // create animation

        });

        binding.leadLayout.setOnClickListener(v -> {
            binding.toolBarText.setText("LeaderBoard");

            // set Explore Fragment
            loadFragment(new LeaderboardFragment());



            // check if home Tab is selected or not
            // unselect other tab except home tab
            binding.homeImage.setImageResource(R.drawable.home_icons);
            binding.homeTxt.setTextColor(txtColor);
            binding.exploreImage.setImageResource(R.drawable.explore_icon);
            binding.exploreTxt.setTextColor(txtColor);
            binding.profileImage.setImageResource(R.drawable.profile_iconn);
            binding.profileTxt.setTextColor(txtColor);

            binding.homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            binding.exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            binding.profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            binding.leadTxt.setTextColor(color);
            binding.leadImage.setImageResource(R.drawable.leadboard_icon_select);
            binding.leadLayout.setBackgroundResource(R.drawable.round_back_rank_100);

            // create animation

        });

        binding.exploreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.toolBarText.setText("Explore");

                // set Explore Fragment
                loadFragment(new ExploreFragment());

                // check if home Tab is selected or not

                // unselect other tab except home tab


                binding.homeImage.setImageResource(R.drawable.home_icons);
                binding.homeTxt.setTextColor(txtColor);
                binding.leadImage.setImageResource(R.drawable.leadboard_icon);
                binding.leadTxt.setTextColor(txtColor);
                binding.profileImage.setImageResource(R.drawable.profile_iconn);
                binding.profileTxt.setTextColor(txtColor);

                binding.homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.leadLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                binding.exploreTxt.setTextColor(color);
                binding.exploreImage.setImageResource(R.drawable.explore_icon_select);
                binding.exploreLayout.setBackgroundResource(R.drawable.round_back_wallet_100);
                // create animation
            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.toolBarText.setText("Profile");

                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);

                // unselect other tab except home tab
                binding.homeImage.setImageResource(R.drawable.home_icons);
                binding.homeTxt.setTextColor(txtColor);
                binding.leadImage.setImageResource(R.drawable.leadboard_icon);
                binding.leadTxt.setTextColor(txtColor);
                binding.exploreImage.setImageResource(R.drawable.explore_icon);
                binding.exploreTxt.setTextColor(txtColor);

                binding.homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.leadLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                binding.exploreLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                binding.profileTxt.setTextColor(color);
                binding.profileImage.setImageResource(R.drawable.profile_iconn_select);
                binding.profileLayout.setBackgroundResource(R.drawable.round_back_profile_100);

            }
        });

//        fetch data from database
        FirebaseUser currentUser = mAuth.getCurrentUser();
        db=FirebaseDatabase.getInstance();
        database = FirebaseFirestore.getInstance();

        database.collection("Users").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                String personName = user.getName();
                String personEmail = user.getEmail();

                name.setText(personName);
                email.setText(personEmail);
                Glide.with(MainActivity.this).load(user.getImage()).into(phoUrl);
                binding.coins.setText(String.valueOf(user.getCoins()));
            }

        });


        // sign out
        binding.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               mAuth.signOut();
               Intent intent = new Intent(MainActivity.this,LoginActivity.class);
               startActivity(intent);
               finish();

            }
        });

    }


    private void setDisplayHomeAsUpEnabled(boolean b) {
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content,fragment);
        ft.setReorderingAllowed(true);
        ft.commit();
    }
}


