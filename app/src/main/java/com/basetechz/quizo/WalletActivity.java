package com.basetechz.quizo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.basetechz.quizo.databinding.ActivityWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WalletActivity extends AppCompatActivity {
    ActivityWalletBinding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseFirestore db;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityWalletBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

       mAuth = FirebaseAuth.getInstance();
       database= FirebaseDatabase.getInstance();
       db = FirebaseFirestore.getInstance();
       currentUser = mAuth.getCurrentUser();

       db.collection("Users").document(currentUser.getUid()).get()
                       .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                           @Override
                           public void onSuccess(DocumentSnapshot documentSnapshot) {
                               User user = documentSnapshot.toObject(User.class);
                               binding.currentCoins.setText(String.valueOf(user.getCoins()));
                           }
                       });
       binding.back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              onBackPressed();
           }
       });
    }
}