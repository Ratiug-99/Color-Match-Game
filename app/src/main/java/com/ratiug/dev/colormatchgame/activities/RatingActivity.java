package com.ratiug.dev.colormatchgame.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RatingActivity extends AppCompatActivity {
    public static final String TAG = "DBG | RatingActivity";
    SharedPreferencesHelper mSharedPreferencesHelper;
    RadioGroup radioGroupCountColors;
    int countColors;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<User> list = new ArrayList();
    AdapterRating adapter;
    RadioButton radioButtonColor4;
    TextView name, record, ratingPos;
    CircleImageView profilePic;
    CardView cardViewMyPosition;
    ProgressBar pbLoading;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        pbLoading = findViewById(R.id.pb_loading);
        pbLoading.setVisibility(View.VISIBLE);

        swipeRefreshLayout = findViewById(R.id.srl_swipe);


        cardViewMyPosition = findViewById(R.id.cv_pos_in_rating);
        name = findViewById(R.id.tvUsername);
        record = findViewById(R.id.tv_record);
        profilePic = findViewById(R.id.ivAvatar);
        ratingPos = findViewById(R.id.tv_rating_no);

        mSharedPreferencesHelper = new SharedPreferencesHelper(this);

        recyclerView = findViewById(R.id.rv_rating);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        radioGroupCountColors = findViewById(R.id.rg_count_colors);
        radioButtonColor4 = findViewById(R.id.rb_clr4);

        reference = FirebaseDatabase.getInstance().getReference().child("Users:");
        radioGroupCountColors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d(TAG, "onCheckedChanged: ");
                pbLoading.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                switch (i) {
                    case R.id.rb_clr4:
                        countColors = 4;
                        break;
                    case R.id.rb_clr6:
                        countColors = 6;
                        break;
                    case R.id.rb_clr8:
                        countColors = 8;
                        break;
                    case R.id.rb_clr10:
                        countColors = 10;
                        break;
                }

                showRating();
            }
        });

        radioButtonColor4.setChecked(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showRating();
            //todo check interner update
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void showRating() {
        Log.d(TAG, "showRating: ");
        cardViewMyPosition.setVisibility(View.GONE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: ");
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User p = dataSnapshot1.getValue(User.class);
                    list.add(p);
                }
                Collections.sort(list, new Comparator<User>() {
                    @Override
                    public int compare(User user, User t1) {
                        int i;
                        switch (countColors) {
                            case 4:
                                i = Integer.parseInt(user.getRecord_4()) -
                                        Integer.parseInt((t1.getRecord_4()));
                                break;
                            case 6:
                                i = Integer.parseInt(user.getRecord_6()) -
                                        Integer.parseInt((t1.getRecord_6()));
                                break;
                            case 8:
                                i = Integer.parseInt(user.getRecord_8()) -
                                        Integer.parseInt((t1.getRecord_8()));
                                break;
                            case 10:
                                i = Integer.parseInt(user.getRecord_10()) -
                                        Integer.parseInt((t1.getRecord_10()));
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + countColors);
                        }
                        return i;
                    }
                });
                Collections.reverse(list);

                int i = 0;
                for (User d : list) {
                    i++;
                    String cUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    String lUser = d.getUid();
                    if (d.getUid() != null && cUserId.equals(lUser)) {
                        setMyPosition(d, i);
                    }
                }
                Log.d(TAG, "onDataChange: +" + countColors);
                adapter = new AdapterRating(RatingActivity.this, list, countColors);
                recyclerView.setAdapter(adapter);
//                reference.removeEventListener(this);
                recyclerView.setVisibility(View.VISIBLE);
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: " + error.getMessage());
            }
        });


    }

    private void setMyPosition(User profiles, int position) {

        name.setText(profiles.getName());
        switch (countColors) {
            case 4:
                record.setText(profiles.getRecord_4());
                break;
            case 6:
                record.setText(profiles.getRecord_6());
                break;
            case 8:
                record.setText(profiles.getRecord_8());
                break;
            case 10:
                record.setText(profiles.getRecord_10());
                break;
        }
        ratingPos.setText(String.valueOf(position));
        Glide.with(profilePic.getContext())
                .load(Uri.parse(profiles.getUri()))
                .into(profilePic);
        Log.d(TAG, "setMyPosition: " + position);

        Log.d(TAG, "setMyPosition: them" + mSharedPreferencesHelper.getTheme());
        if (mSharedPreferencesHelper.getTheme() == 2) {
            switch (position) {
                case 1:
                    profilePic.setBorderColor(getResources().getColor(R.color.color_gold));
                    name.setTextColor(getResources().getColor(R.color.color_gold));
                    record.setTextColor(getResources().getColor(R.color.color_gold));
                    ratingPos.setTextColor(getResources().getColor(R.color.color_gold));
                    break;
                case 2:
                    profilePic.setBorderColor(getResources().getColor(R.color.color_silver));
                    name.setTextColor(getResources().getColor(R.color.color_silver));
                    record.setTextColor(getResources().getColor(R.color.color_silver));
                    ratingPos.setTextColor(getResources().getColor(R.color.color_silver));
                    break;
                case 3:
                    profilePic.setBorderColor(getResources().getColor(R.color.color_bronze));
                    name.setTextColor(getResources().getColor(R.color.color_bronze));
                    record.setTextColor(getResources().getColor(R.color.color_bronze));
                    ratingPos.setTextColor(getResources().getColor(R.color.color_bronze));
                    break;
                default:
                    profilePic.setBorderColor(getResources().getColor(R.color.black));
                    name.setTextColor(getResources().getColor(R.color.textColor));
                    record.setTextColor(getResources().getColor(R.color.textColor));
                    ratingPos.setTextColor(getResources().getColor(R.color.textColor));
                    break;
            }
        } else {
            switch (position) {
                case 1:
                   cardViewMyPosition.setCardBackgroundColor(getResources().getColor(R.color.color_gold));
                    break;
                case 2:
                    cardViewMyPosition.setCardBackgroundColor(getResources().getColor(R.color.color_silver));
                    break;
                case 3:
                    cardViewMyPosition.setCardBackgroundColor(getResources().getColor(R.color.color_bronze));
                    break;
                default:
                    cardViewMyPosition.setCardBackgroundColor(getResources().getColor(R.color.colorBackgroundCardViewMyPos));
                    break;
            }
        }
        cardViewMyPosition.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recreate();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }
}
