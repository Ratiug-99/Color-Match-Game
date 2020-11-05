package com.ratiug.dev.colormatchgame;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class AdapterRating extends RecyclerView.Adapter<AdapterRating.MyViewHolder> {
    public static final String TAG = "DBG | AdapterRating";
    Context context;
    ArrayList<User> profiles;

    public AdapterRating(Context c, ArrayList<User> p) {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_rating, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(profiles.get(position).getName());
        holder.record.setText(profiles.get(position).getRecord());
        holder.profilePic.setImageURI(Uri.parse(profiles.get(position).getUri()));
        holder.ratingPos.setText(String.valueOf(position + 1));
        Glide.with(holder.profilePic.getContext())
                .load(Uri.parse(profiles.get(position).getUri()))
                .into(holder.profilePic);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, record, ratingPos;
        CircleImageView profilePic;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvUsername);
            record = itemView.findViewById(R.id.tv_record);
            profilePic = itemView.findViewById(R.id.ivAvatar);
            ratingPos = itemView.findViewById(R.id.tv_rating_no);
        }
    }
}