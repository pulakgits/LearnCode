package com.basetechz.quizo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.basetechz.quizo.databinding.ActivityEditBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

public class editActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    ArrayList<String> stringArrayList = new ArrayList<>();

    FirebaseFirestore database;
    FirebaseAuth mAuth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");

        stringArrayList.add("Andhra Pradesh");
        stringArrayList.add("Arunachal Pradesh");
        stringArrayList.add("Assam");
        stringArrayList.add("Bihar");
        stringArrayList.add("Chhattisgarh");
        stringArrayList.add("Goa");
        stringArrayList.add("Gujarat");
        stringArrayList.add("Haryana");
        stringArrayList.add("Himachal Pradesh");
        stringArrayList.add("Jharkhand");
        stringArrayList.add("Karnataka");
        stringArrayList.add("Kerala");
        stringArrayList.add("Madhya Pradesh");
        stringArrayList.add("Maharashtra");
        stringArrayList.add("Manipur");
        stringArrayList.add("Meghalaya");
        stringArrayList.add("Mizoram");
        stringArrayList.add("Nagaland");
        stringArrayList.add("Odisha");
        stringArrayList.add("Punjab");
        stringArrayList.add("Rajasthan");
        stringArrayList.add("Sikkim");
        stringArrayList.add("Tamil Nadu");
        stringArrayList.add("Telangana");
        stringArrayList.add("Tripura");
        stringArrayList.add("Uttarakhand");
        stringArrayList.add("Uttar Pradesh");
        stringArrayList.add("West Bengal");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,stringArrayList);
        binding.autoTxtView.setAdapter(adapter);
        binding.autoTxtView.setThreshold(1);

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                database.collection("Users").document(mAuth.getCurrentUser().getUid())
                        .update("name",binding.nameBox.getText().toString(),
                                "email",binding.emailBox.getText().toString(),
                                "phoneNumber",binding.mobileBox.getText().toString(),
                                "pass",binding.passwordBox.getText().toString(),
                                "state",binding.autoTxtView.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                                startActivity(new Intent(editActivity.this,ProfileActivity.class));
                                finish();
                            }
                        });
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editActivity.super.onBackPressed();
            }
        });




    }
}