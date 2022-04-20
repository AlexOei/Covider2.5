package com.example.covider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.databinding.ActivityListBinding;
import com.example.covider.model.Building;
import com.example.covider.model.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListActivitySchedule extends AppCompatActivity {

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

        //firebase.getList(binding, "freq_visited", buildingArrayList, ListActivity.this);
        firebase.getListSched(binding, "should_visit", buildingArrayList, ListActivitySchedule.this);


    }

}
