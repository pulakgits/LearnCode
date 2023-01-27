package com.basetechz.quizo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.basetechz.quizo.databinding.ActivitySignUpBinding;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Creating New Account ...");

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,email,pass,referCode;
                name = binding.nameBox.getText().toString();
                email = binding.emailBox.getText().toString();
                pass = binding.passwordBox.getText().toString();
                referCode = binding.referBox.getText().toString();

                // show progressdialod box
                dialog.show();


                // create a object of user class
                User user = new User(name,email,pass,referCode);

                auth.createUserWithEmailAndPassword(email,pass ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            dialog.dismiss();

                            // create a uid variable for store user unique id
                            String uid = task.getResult().getUser().getUid();

                            database.collection("Users")
                                    .document(uid)
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                dialog.dismiss();
                                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else{
                                                dialog.dismiss();
                                                Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });



                        }else{
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });



            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });






        if(auth.getCurrentUser()!= null)
        {
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }
}