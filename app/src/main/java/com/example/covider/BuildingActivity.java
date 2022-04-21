package com.example.covider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.databinding.ActivityBuildingBinding;
import com.example.covider.model.Firebase;

public class BuildingActivity extends AppCompatActivity {
    ActivityBuildingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuildingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button checkIn = findViewById(R.id.checkBuilding);
        Intent intent = this.getIntent();
        TextView phoneProfile = findViewById(R.id.phone_profile);
        Firebase firebase = new Firebase();
        if (intent != null){

            String name = intent.getStringExtra("name");
            String risk = intent.getStringExtra("risk");
            String code = intent.getStringExtra("code");
            String freqOrSched = intent.getStringExtra("freqOrSched");

            binding.nameProfile.setText(name);
            firebase.getCovidRisk(code, phoneProfile);
            binding.countryProfile.setText(code);
            binding.freqSched.setText(freqOrSched);

            checkIn.setOnClickListener(view -> {
                Intent i = new Intent(view.getContext(), BuildingCheck.class);
                i.putExtra("Building", name);
                i.putExtra("Code", code);
                startActivity(i);
            });

        }





    }


}
