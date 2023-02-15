package com.basetechz.quizo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.basetechz.quizo.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.InputStream;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser user1;
    FirebaseFirestore database;
    FirebaseDatabase db;
    ProgressDialog dialog;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    User firebaseUser;
    EditText name;
    EditText email;
    EditText pass;

    Uri filepath;
    ImageView img;
    ImageView browser;
    Bitmap bitmap;
    private final int GALLERY_REQ_CODE=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        db = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Creating New Account ...");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        db = FirebaseDatabase.getInstance();

        binding.skipBtn.setAnimation(AnimationUtils.loadAnimation(this,R.anim.buttom_animation));
        binding.textSample.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        binding.ani.setAnimation(AnimationUtils.loadAnimation(this,R.anim.buttom_animation));
        binding.nameBox.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        binding.emailBox.setAnimation(AnimationUtils.loadAnimation(this,R.anim.buttom_animation));
        binding.passwordBox.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));
        binding.loginLayout.setAnimation(AnimationUtils.loadAnimation(this,R.anim.buttom_animation));
        binding.SignUpBtn.setAnimation(AnimationUtils.loadAnimation(this,R.anim.top_animation));

        img = findViewById(R.id.addPhotoProfile);

        // mobile external storage pic access
        img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,GALLERY_REQ_CODE);
                    }
                });


        // When Click SignUpBtn
        binding.SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

        binding.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user1!= null){
            finish();
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK){
            assert data != null;
            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Upload Image into Firebase
    private void uploadToFirebase() {
        dialog.setTitle("File Uploader");
        dialog.show();
        name = findViewById(R.id.nameBox);
        email = findViewById(R.id.emailBox);
        pass = findViewById(R.id.passwordBox);

        String personEmail,personPass;
        personEmail = email.getText().toString();
        personPass  = pass.getText().toString();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("Image"+new Random().nextInt(50));

        mAuth.createUserWithEmailAndPassword(personEmail,personPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dialog.dismiss();
                    uploader.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            long coins = 100;
                                            user1 = mAuth.getCurrentUser();

                                            User firebaseUser = new User(name.getText().toString(),personEmail,personPass,uri.toString(),coins);
                                            String uid = task.getResult().getUser().getUid();

                                            database.collection("Users")
                                                   .document(uid).set(firebaseUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {

                                                           startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                                                           finish();
                                                       }
                                                   });
                                        }
                                    });
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    dialog.dismiss();
                                    float percent =(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                                    dialog.setMessage("Uploaded:"+(int)percent+"%");
                                }
                            });
                }else{
                    dialog.dismiss();
                    Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}