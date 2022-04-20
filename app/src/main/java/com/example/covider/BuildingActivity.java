package com.example.covider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.databinding.ActivityBuildingBinding;

public class BuildingActivity extends AppCompatActivity {
    ActivityBuildingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuildingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){

            String name = intent.getStringExtra("name");
            String risk = intent.getStringExtra("risk");
            String code = intent.getStringExtra("code");
            String freqOrSched = intent.getStringExtra("freqOrSched");

            binding.nameProfile.setText(name);
            binding.phoneProfile.setText(risk);
            binding.countryProfile.setText(code);
            binding.freqSched.setText(freqOrSched);


        }

    }


}
