package com.basetechz.quizo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerLeaderBoardAdapter extends RecyclerView.Adapter<RecyclerLeaderBoardAdapter.ViewHolder> {

    ArrayList<User> uArrayList;
    Context context;
    RecyclerLeaderBoardAdapter(ArrayList<User> uArrayList,Context context){
        this.uArrayList = uArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leader_board_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = uArrayList.get(position);


        holder.coins.setText(String.valueOf(user.getCoins()));
        Picasso.get().load(user.getImage()).into(holder.img);
        holder.name.setText(user.getName());
        holder.rank.setText(String.format("%d",position+1));


    }

    @Override
    public int getItemCount() {
        return uArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rank;
        ImageView img;
        TextView name;
        TextView coins;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.personRank);
            img = itemView.findViewById(R.id.personImg);
            name = itemView.findViewById(R.id.personName);
            coins = itemView.findViewById(R.id.personCoins);

        }
    }


}
