package com.example.covider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.model.Section;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequentlyVisited extends AppCompatActivity {
    Button switchViews, submitClasses;
    EditText firstCode, secondCode, thirdCode, fourthCode, fifthCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequently_visited);

        firstCode = findViewById(R.id.code1);
        secondCode = findViewById(R.id.code2);
        thirdCode = findViewById(R.id.code3);
        fourthCode = findViewById(R.id.code4);
        fifthCode = findViewById(R.id.code5);
        submitClasses = findViewById(R.id.submitClasses);
        switchViews = findViewById(R.id.schedule);

        switchViews.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), ClassSectionActivity.class);
            startActivity(i);
        });

        submitClasses.setOnClickListener(view -> {
            String codeOne = firstCode.getText().toString().trim();
            String codeTwo = secondCode.getText().toString().trim();
            String codeThree = thirdCode.getText().toString().trim();
            String codeFour = fourthCode.getText().toString().trim();
            String codeFive = fifthCode.getText().toString().trim();

            List<String> Codes = new ArrayList<>();
            if (codeOne.isEmpty()) {
                firstCode.setError("Please Enter at least one class");
                return;
            }
            Codes.add(codeOne);

            if (!codeTwo.isEmpty()){
                Codes.add(codeTwo);
            }

            if (!codeThree.isEmpty()){
                Codes.add(codeThree);
            }

            if (!codeFour.isEmpty()){
                Codes.add(codeFour);
            }

            if (!codeFive.isEmpty()){
                Codes.add(codeFive);
            }


            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("freq_visited").setValue(Codes)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(FrequentlyVisited.this, "Code has been registered successfully!", Toast.LENGTH_LONG ).show();
                        }else{
                            Toast.makeText(FrequentlyVisited.this, "Failed to register!", Toast.LENGTH_LONG ).show();
                        }
                    });

        });
    }
}