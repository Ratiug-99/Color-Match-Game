package com.ratiug.dev.colormatchgame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ratiug.dev.colormatchgame.AdapterRating;
import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.SharedPreferencesHelper;
import com.ratiug.dev.colormatchgame.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RatingActivity extends AppCompatActivity{
    public static final String TAG = "DBG | RatingActivity";
    SharedPreferencesHelper mSharedPreferencesHelper;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<User> list = new ArrayList();;
    AdapterRating adapter;
    TextView name, record, ratingPos;
    CircleImageView profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeApp();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        name = findViewById(R.id.tvUsername);
        record = findViewById(R.id.tv_record);
        profilePic = findViewById(R.id.ivAvatar);
        ratingPos = findViewById(R.id.tv_rating_no);
        mSharedPreferencesHelper = new SharedPreferencesHelper(getApplicationContext());
        recyclerView = findViewById(R.id.rv_rating);
        recyclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));

        Log.d(TAG, "onCreate: ");
        reference = FirebaseDatabase.getInstance().getReference().child("Users:");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    User p = dataSnapshot1.getValue(User.class);
                    list.add(p);
                }
                 Collections.sort(list, new Comparator<User>() {
                     @Override
                     public int compare(User user, User t1) {
                         return Integer.parseInt(user.getRecord()) -
                                 Integer.parseInt((t1.getRecord()));
                     }
                 });
                Collections.reverse(list );
                int i = 0;
                for(User d : list){
                    i++;
                    String cUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String lUser = d.getUid();
                    Log.d(TAG, "onDataChange: Current User " + cUserId);
                    Log.d(TAG, "onDataChange: List User " + lUser);
                    if(d.getUid() != null && cUserId.equals(lUser)){
                        Log.d(TAG, "onDataChange: true");
                        setMyPosition(d, i);
                    }

                }
                adapter = new AdapterRating(RatingActivity.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    private void setMyPosition(User profiles, int position) {
        Log.d(TAG, "setMyPosition: " + profiles.getUid());
        name.setText(profiles.getName());
        record.setText(profiles.getRecord());
        ratingPos.setText(String.valueOf(position));
        Glide.with(profilePic.getContext())
                .load(Uri.parse(profiles.getUri()))
                .into(profilePic);
    }

    private void setThemeApp() {
        mSharedPreferencesHelper = new SharedPreferencesHelper(this);
        AppCompatDelegate.setDefaultNightMode(mSharedPreferencesHelper.getTheme());
    }
}