package com.basetechz.quizo;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basetechz.quizo.databinding.FragmentLeaderboardBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardFragment extends Fragment {
    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentLeaderboardBinding binding;
    RecyclerLeaderBoardAdapter adapter;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    ArrayList<User> uArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeaderboardBinding.inflate(inflater,container,false);

         uArrayList = new ArrayList<>();


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterList(newText);
                return true;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.lBRecycler.setLayoutManager(layoutManager);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();


        // In LeaderBoard Ranking base according to number of coins
        database.collection("Users")
                .orderBy("coins",Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(DocumentSnapshot snapshot : queryDocumentSnapshots){
                            User user = snapshot.toObject(User.class);
                            user.setUserId(snapshot.getId());
                            uArrayList.add(user);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });


        // storage all total all team member
        database.collection("Users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            long userCount = task.getResult().size();
                            binding.count.setText("All Members ("+userCount+")");
                        }else {
                            Log.d("Firestore","Error getting user count:",task.getException());
                        }
                    }
                });

        adapter = new RecyclerLeaderBoardAdapter(uArrayList, getContext());
        binding.lBRecycler.setAdapter(adapter);

        return binding.getRoot();
    }

    private void filterList(String text) {
        ArrayList<User> filteredList = new ArrayList<>();
        for(User item : uArrayList){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(getContext(),"No data found",Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }
    }

}