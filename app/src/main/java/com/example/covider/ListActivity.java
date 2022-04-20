package com.example.covider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.databinding.ActivityListBinding;
import com.example.covider.model.Building;
import com.example.covider.model.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ActivityListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Building> buildingArrayList = new ArrayList();
        super.onCreate(savedInstanceState);
        Firebase firebase = new Firebase();
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), MapsActivity.class);
            startActivity(i);
        });

        firebase.getList(binding, "freq_visited", buildingArrayList, ListActivity.this);
        //firebase.getListSched(binding, "should_visit", buildingArrayList, ListActivity.this);


        }

    }
