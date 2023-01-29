package com.basetechz.quizo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerCategoryPartAdapter extends RecyclerView.Adapter<RecyclerCategoryPartAdapter.ViewHolder> {

    // Constructor of RecyclerCategoryPart
    Context context;
    String catId;
    ArrayList<CategoryPartModel> categoriesPart;
    RecyclerCategoryPartAdapter(Context context,ArrayList<CategoryPartModel> categoriesPart,String catId){
        this.context = context;
        this.categoriesPart=categoriesPart;
        this.catId=catId;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_part_row,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryPartModel categoryPartModel = categoriesPart.get(position);

        holder.catPartName.setText(categoryPartModel.getCatPartName());
        holder.catPartNum.setText(categoryPartModel.getCatPartNum());

        String categoryPartImage = categoryPartModel.getCategoryPartImage();
        Picasso.get().load(categoryPartImage).into(holder.catPartImage);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,QuizActivity.class);
            intent.putExtra("categoryPartId",categoryPartModel.getCategoryPartId());
            intent.putExtra("catId",catId);
            context.startActivity(intent);

        });
    }
    @Override
    public int getItemCount() {
        return categoriesPart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView catPartName;
        TextView catPartNum;
        ImageView catPartImage;

        public ViewHolder(View itemView){
            super(itemView);

            catPartName = itemView.findViewById(R.id.catPartName);
            catPartNum = itemView.findViewById(R.id.catPartNum);
            catPartImage = itemView.findViewById(R.id.categoryPartImage);
        }
    }
}
