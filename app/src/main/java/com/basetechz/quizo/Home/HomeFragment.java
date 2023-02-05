package com.basetechz.quizo.Home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basetechz.quizo.GridSpacingItemDecoration;
import com.basetechz.quizo.Home.popularCourse.PopularCourseModel;
import com.basetechz.quizo.Home.popularCourse.RecyclerPopularCourseAdapter;
import com.basetechz.quizo.User;
import com.basetechz.quizo.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentHomeBinding binding;
    FirebaseFirestore database;
    LinearLayoutManager layoutManagerPCourse;


    RecyclerPopularCourseAdapter adapterPopularCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        database = FirebaseFirestore.getInstance();


        
        // set Popular course card and adapter and array list

        // create array list for popular course
        ArrayList<PopularCourseModel> pcmArrayList = new ArrayList<>();

        // set Layout manager and handel
      LinearLayoutManager layoutManagerPC = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
      binding.popularCourse.setLayoutManager(layoutManagerPC);



      database.collection("popularCourse").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
          @Override
          public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
              for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                  PopularCourseModel pcm = snapshot.toObject(PopularCourseModel.class);
                  pcm.setCourseId(snapshot.getId());
                  pcmArrayList.add(pcm);
              }
              adapterPopularCourse.notifyDataSetChanged();

          }
      });
            adapterPopularCourse = new RecyclerPopularCourseAdapter(getContext(),pcmArrayList);
            binding.popularCourse.setAdapter(adapterPopularCourse);
















        return binding.getRoot();
    }
}