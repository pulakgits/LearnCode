package com.basetechz.quizo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.basetechz.quizo.databinding.ActivityCategoryPartBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryPartActivity extends AppCompatActivity {

   ActivityCategoryPartBinding binding;
    FirebaseFirestore database;
    RecyclerCategoryPartAdapter adapterPart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryPartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        database = FirebaseFirestore.getInstance();

        // when click back icon backCategoryPart from Explore
        binding.categoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // array list for categoryPart
        ArrayList<CategoryPartModel> categoriesPart = new ArrayList<>();
        
        
        // pass context and categories to
        //RecyclerCategoryAdapter


        // set Linear Layout Manager
        LinearLayoutManager layoutManagerP = new LinearLayoutManager(CategoryPartActivity.this, LinearLayoutManager.VERTICAL, false);
        binding.categoryListPart.setLayoutManager(layoutManagerP);


        // get category Name from RecyclerCategoryAdapter and setText
        String categoryName = getIntent().getStringExtra("categoryName");
        binding.categoryName.setText(categoryName);
        // get category id from RecyclerCategoryAdapter
        Intent intent = getIntent();
        final String catId = intent.getStringExtra("catId");

        database.collection("categories")
                .document(catId)
                .collection("categoriesPart")
                .addSnapshotListener((value, error) -> {
                    categoriesPart.clear();

                    assert value != null;
                    for(DocumentSnapshot snapshot : value.getDocuments()){

                        // snapshot.toObject convert object to CategoryModel
                        // because in CloudFireStore we put field name is equal to CategoryModel variable
                        CategoryPartModel model = snapshot.toObject(CategoryPartModel.class);

                        //attach document id to CategoryModel Object
                        assert model != null;
                        model.setCategoryPartId(snapshot.getId());
                        categoriesPart.add(model);
                    }
                    adapterPart.notifyDataSetChanged();
                });
        adapterPart = new RecyclerCategoryPartAdapter(CategoryPartActivity.this,categoriesPart,catId);
        binding.categoryListPart.setAdapter(adapterPart);


    }

}