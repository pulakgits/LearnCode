package com.basetechz.quizo.Home.popularCourse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basetechz.quizo.LessonActivity;
import com.basetechz.quizo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerPopularCourseAdapter extends RecyclerView.Adapter<RecyclerPopularCourseAdapter.ViewHolder> {

    Context context;
   ArrayList<PopularCourseModel> pcmArrayList;
   public RecyclerPopularCourseAdapter(Context context, ArrayList<PopularCourseModel> pcmArrayList){
       this.context = context;
       this.pcmArrayList = pcmArrayList;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.popular_course_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       PopularCourseModel pcModel = pcmArrayList.get(position);

        Picasso.get().load(pcModel.getCourseImg()).into(holder.courseImg);
        holder.courseName.setText(pcModel.getCourseName());
        holder.courseLesson.setText(pcModel.getCourseLesson());
        holder.courseStar.setText(pcModel.getCourseStar());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LessonActivity.class);
                intent.putExtra("courseName",pcModel.getCourseName());
                intent.putExtra("courseId",pcModel.getCourseId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pcmArrayList.size();
    }

    public static  class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImg;
        TextView courseName;
        TextView courseLesson;
        TextView courseStar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseImg = itemView.findViewById(R.id.courseImg);
            courseName = itemView.findViewById(R.id.courseName);
            courseLesson = itemView.findViewById(R.id.courseLesson);
            courseStar = itemView.findViewById(R.id.courseStar);

        }
    }
}
