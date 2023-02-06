package com.basetechz.quizo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.basetechz.quizo.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Login Process.....");



        binding.skipBtn.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        binding.txt.setAnimation(AnimationUtils.loadAnimation(this,R.anim.buttom_animation));
        binding.ani.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        binding.emailBox.setAnimation(AnimationUtils.loadAnimation(this,R.anim.buttom_animation));
        binding.passwordBox.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        binding.forgotBtn.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        binding.sb.setAnimation(AnimationUtils.loadAnimation(this,R.anim.buttom_animation));
        binding.loginBtn.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass;
                email = binding.emailBox.getText().toString();
                pass = binding.passwordBox.getText().toString();
                dialog.show();

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            dialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
       binding.smBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
               finish();
           }
       });

       binding.skipBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
           }
       });


        if(auth.getCurrentUser()!= null)
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}