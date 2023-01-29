package com.basetechz.quizo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basetechz.quizo.databinding.FragmentExploreBinding;
import com.basetechz.quizo.databinding.FragmentExploreBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;



public class ExploreFragment extends Fragment  {


    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentExploreBinding binding;
    FirebaseFirestore database;
    LinearLayoutManager layoutManager1;
    LinearLayoutManager layoutManager2;
    RecyclerCategoryAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater,container,false);
        database = FirebaseFirestore.getInstance();

        // array list for category
        ArrayList<CategoryModel> categories = new ArrayList<>();


        // Manually set image and text
        // for first index
//        CategoryModel categoryModel = new CategoryModel("","","Java");
//        categories.add(categoryModel);

        // Set GridLayout Using GridSpacingDecoration Class object set Span count , spacing , includeEdge
        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(),2);
        int spanCount = 2; // 3 columns
        int spacing = 30; // 50px
        boolean includeEdge = true;
        binding.categoryList2.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        binding.categoryList2.setLayoutManager(layoutManager2);


        // database connect (store data in fire base )
        database.collection("categories")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        categories.clear();

                        for(DocumentSnapshot snapshot : value.getDocuments()){

                            // snapshot.toObject convert object to CategoryModel
                            // because in CloudFireStore we put field name is equal to CategoryModel variable
                            CategoryModel model = snapshot.toObject(CategoryModel.class);

                            //attach document id to CategoryModel Object
                            model.setCategoryId(snapshot.getId());
                            categories.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        // pass context and categories to
        //RecyclerCategoryAdapter

        adapter = new RecyclerCategoryAdapter(getContext(),categories);
        // categories and context pass in RecyclerCategoryAdapter Constructor
        binding.categoryList2.setAdapter(adapter);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}


