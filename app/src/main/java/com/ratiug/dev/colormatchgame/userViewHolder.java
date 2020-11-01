package com.ratiug.dev.colormatchgame;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class userViewHolder extends RecyclerView.ViewHolder {
    TextView tvUsername, tvRecord;
    CircleImageView ivAvatar;

    public userViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        tvRecord = itemView.findViewById(R.id.tv_record);
        ivAvatar = itemView.findViewById(R.id.ivAvatar);
    }
}

