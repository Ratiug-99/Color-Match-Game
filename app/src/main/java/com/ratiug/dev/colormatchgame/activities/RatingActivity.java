package com.ratiug.dev.colormatchgame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ratiug.dev.colormatchgame.AdapterRating;
import com.ratiug.dev.colormatchgame.R;
import com.ratiug.dev.colormatchgame.User;

import java.util.ArrayList;
import java.util.List;

public class RatingActivity extends AppCompatActivity{
    public static final String TAG = "DBG | RatingActivity";
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<User> list = new ArrayList();;
    AdapterRating adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        recyclerView = findViewById(R.id.rv_rating);
        recyclerView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));

        Log.d(TAG, "onCreate: ");
        reference = FirebaseDatabase.getInstance().getReference().child("Users:");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    User p = dataSnapshot1.getValue(User.class);
                    list.add(p);
                    Log.d(TAG, "onDataChange: " + list.size());
                }
                adapter = new AdapterRating(getApplicationContext(),list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}