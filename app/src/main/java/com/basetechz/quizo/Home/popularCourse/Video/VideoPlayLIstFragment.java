package com.basetechz.quizo.Home.popularCourse.Video;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basetechz.quizo.Home.popularCourse.Video.LessonModel;
import com.basetechz.quizo.Home.popularCourse.Video.RecyclerPCLessonAdapter;
import com.basetechz.quizo.databinding.FragmentVideoPlayLIstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class VideoPlayLIstFragment extends Fragment {


    public VideoPlayLIstFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentVideoPlayLIstBinding binding;
    FirebaseFirestore database;
    RecyclerPCLessonAdapter adapter;
    LessonModel lessonModel;
    long views = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVideoPlayLIstBinding.inflate(inflater,container,false);
        database = FirebaseFirestore.getInstance();

        String courseId = getArguments().getString("courseId");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.videoRecycler.setLayoutManager(layoutManager);

        ArrayList<LessonModel> lModelArrayList = new ArrayList<>();

        // retrieve data from firebase firestore
        database.collection("popularCourse").document(courseId).collection("video").orderBy("videoLessonId", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        lModelArrayList.clear();
                        for(DocumentSnapshot snapshot : value.getDocuments()){
                            lessonModel = snapshot.toObject(LessonModel.class);
                            lessonModel.setVideoLessonId(snapshot.getId());
                            lModelArrayList.add(lessonModel);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        adapter = new RecyclerPCLessonAdapter(getContext(),lModelArrayList,courseId);
        binding.videoRecycler.setAdapter(adapter);
       return binding.getRoot();
    }
}