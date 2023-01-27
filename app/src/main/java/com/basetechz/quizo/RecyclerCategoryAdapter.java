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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder> {


    // constructer of REcyclerCategoryAdapter
    Context context;
    ArrayList<CategoryModel> categories;
    RecyclerCategoryAdapter(Context context,ArrayList<CategoryModel> categories){
        this.context = context;
        this.categories=categories;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //holder.imgCategory.setImageResource(categories.get(position).categoryImage);
        //Glide.with(context).load(model.categoryImage).into(holder.imgCategory);

        CategoryModel categoryModel = categories.get(position);


        // for drawable image
//       holder.imgCategory.setImageResource(categories.get(position).getCategoryImage());

        String categoryImage;
       categoryImage = categoryModel.getCategoryImage();
        Picasso.get().load(categoryImage).into(holder.imgCategory);
        holder.txtCategory.setText(categoryModel.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,QuizActivity.class);
                intent.putExtra("catId",categoryModel.getCategoryId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtCategory;
        ImageView imgCategory;
        public ViewHolder(View itemView){
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            imgCategory = itemView.findViewById(R.id.imgCategory);



        }
    }

}
