package com.basetechz.quizo.Home.popularCourse.Video;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.basetechz.quizo.R;
import com.basetechz.quizo.WatchActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPCLessonAdapter extends RecyclerView.Adapter<RecyclerPCLessonAdapter.ViewHolder> {

    boolean isClick = false;
    FirebaseFirestore database;
    FirebaseAuth auth;
    List<String> users;
    String formattedViewCount;

    ArrayList<LessonModel> lessonModelArrayList = new ArrayList<>();
    Context context;
    String courseId;
    public RecyclerPCLessonAdapter(Context context,ArrayList<LessonModel> lessonModelArrayList,String courseId){
        this.context = context;
        this.lessonModelArrayList = lessonModelArrayList;
        this.courseId = courseId;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lesson_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LessonModel model = lessonModelArrayList.get(position);

        holder.lessonTxt.setText(model.getVideoLessonTxt());
        holder.txtCount.setText(String.format("%d",position+1));
        Glide.with(holder.itemView.getContext()).load(model.getVideoImage()).into(holder.videoImage);


        if (model.getViewCount()< 1000) {
            formattedViewCount = String.valueOf(model.getViewCount());
        } else if (model.getViewCount() < 10000) {
            formattedViewCount = String.valueOf(model.getViewCount() / 1000) + "k";
        } else if (model.getViewCount()<= 100000) {
            formattedViewCount = String.valueOf(model.getViewCount() / 1000) + "k";
        } else if (model.getViewCount() < 1000000) {
            formattedViewCount = String.valueOf(model.getViewCount()/ 1000) + "k";
        } else if(model.getViewCount()<1000000000) {
            formattedViewCount = String.valueOf(model.getViewCount()/ 1000000) + "M";
        }else if(model.getViewCount()>=1000000000) {
            formattedViewCount = String.valueOf(model.getViewCount()/ 1000000000) + "B";
        }
        holder.videoViews.setText(String.valueOf(formattedViewCount));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WatchActivity.class);
                intent.putExtra("courseId",courseId);
                intent.putExtra("videoId",model.getVideoLessonId());
                intent.putExtra("videoLessonTxt",model.getVideoLessonTxt());

                // real-time view count in Android Studio using Java
                // and Firebase Firestore, where each user can only view the video once
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference docRef = db.collection("popularCourse").document(courseId).collection("video").document(model.getVideoLessonId());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                long viewCount = document.getLong("viewCount");
                                intent.putExtra("views",viewCount);

                                // Update the formatted view count on the UI
                            } else {
                                Log.d(TAG, "Current data: null");
                            }

                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

                // To update the view count in real-time
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    users = (List<String>) document.get("users");
                                    if (users == null) {
                                        users = new ArrayList<>();
                                    }
                                    if (!users.contains(userId)) {
                                        // User has not viewed the video
                                        users.add(userId);
                                        docRef.update("users", users);
                                        docRef.update("viewCount", FieldValue.increment(1));
                                    }

                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
                }

                // real-time changes to the view count
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "Listen failed.", error);
                            return;
                        }

                        if (value != null && value.exists()) {
                            long viewCount = value.getLong("viewCount");
                            // Update the view count on the UI
                        } else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });

                intent.putExtra("viewCount",formattedViewCount);

//                intent.putExtra("likes",model.getLike());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return lessonModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTxt;
        TextView txtCount;
        ImageView videoImage;
        TextView videoViews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonTxt = itemView.findViewById(R.id.videoLessonTxt);
            txtCount = itemView.findViewById(R.id.txtCount);
            videoImage = itemView.findViewById(R.id.videoImage);
            videoViews = itemView.findViewById(R.id.videoViews);
        }
    }
}
