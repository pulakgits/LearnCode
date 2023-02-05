package com.basetechz.quizo.Home.popularCourse.Video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.basetechz.quizo.R;
import com.basetechz.quizo.WatchActivity;

import java.util.ArrayList;

public class RecyclerPCLessonAdapter extends RecyclerView.Adapter<RecyclerPCLessonAdapter.ViewHolder> {

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WatchActivity.class);
                intent.putExtra("courseId",courseId);
                intent.putExtra("videoId",model.getVideoLessonId());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lessonTxt = itemView.findViewById(R.id.videoLessonTxt);
            txtCount = itemView.findViewById(R.id.txtCount);
        }
    }
}
