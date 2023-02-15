package com.basetechz.quizo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;

public class RecyclerLeaderBoardAdapter extends RecyclerView.Adapter<RecyclerLeaderBoardAdapter.ViewHolder> {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    private int currentUserPo;


    ArrayList<User> uArrayList;
    Context context;
    RecyclerLeaderBoardAdapter(ArrayList<User> uArrayList, Context context){
        this.uArrayList = uArrayList;
        this.context = context;

    }

    public  void setFilteredList(ArrayList<User> filteredList){
        this.uArrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.leader_board_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // array list per give position of each element
        User user = uArrayList.get(position);

        Log.d("LeaderBoardAdapter", "User name: " + user.getName());
        Log.d("LeaderBoardAdapter", "User image URL: " + user.getImage());
        Log.d("LeaderBoardAdapter", "User coins: " + user.getCoins());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation);
        holder.itemView.startAnimation(animation);

        holder.coins.setText(String.valueOf(user.getCoins()));
        Picasso.get().load(user.getImage()).into(holder.img);
        holder.name.setText(user.getName());
        holder.rank.setText(String.format("%d",position+1));

        db.collection("Users").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user1 = documentSnapshot.toObject(User.class);
                assert user1 != null;
                if(user.getUserId().equals(documentSnapshot.getId())){
                    holder.name.setTextColor(ContextCompat.getColor(context,R.color.mc1));
                    holder.rank.setTextColor(ContextCompat.getColor(context,R.color.mc1));
                    holder.coins.setTextColor(ContextCompat.getColor(context,R.color.mc1));
                }
            }
        });
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

    // important code
    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.itemView.clearAnimation();
    }
}
