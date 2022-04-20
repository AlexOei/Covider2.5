package com.example.covider;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.model.Health;
import com.example.covider.utils.TimeUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CovidSymptoms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_symptoms);
    }

    boolean covid = false;
    boolean fever = false;
    boolean soreThroat = false;
    boolean symptoms = false;

    public void covidHandler(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yesForCovidPositive:
                if (checked)
                    covid = true;

                break;
            case R.id.noForCovidPositive:
                if (checked)
                    covid = false;
                    break;
        }
    }

    public void feverHandler(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yesForFever:
                if (checked)
                    fever = true;
                break;
            case R.id.noForFever:
                if (checked)
                    fever = false;
                    break;
        }
    }

    public void soreThroatHandler(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yesForSoreThroat:
                if (checked)
                    soreThroat = true;
                break;
            case R.id.noForSoreThroat:
                if (checked)
                    soreThroat = false;
                    break;
        }
    }

    public void otherSymptomsHandler(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yesForOtherSymptoms:
                if (checked)
                    symptoms = true;
                break;
            case R.id.noForOtherSymptoms:
                if (checked)
                    symptoms = false;
                break;
        }
    }

    public void submitButtonHandler(View view) {
        boolean covids = covid&&fever&&soreThroat&&symptoms;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.child("health_history").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<Health>> indicator = new GenericTypeIndicator<List<Health>>() {};
                List<Health> healthList = snapshot.getValue(indicator);
                if (healthList == null) healthList = new ArrayList<>();
                if (healthList.size()>0){
                    Health latest =  healthList.get(healthList.size()-1);

                    if (TimeUtil.today(latest.getTime())){
                        Toast.makeText(CovidSymptoms.this, "Submitted today!", Toast.LENGTH_LONG ).show();
                        finish();
                        return;
                    }
                }
                healthList.add(new Health(System.currentTimeMillis(),covids));
                ref.child("health_history").setValue(healthList);
                ref.child("haveCovid").setValue(covids).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CovidSymptoms.this, "submit successfully!", Toast.LENGTH_LONG ).show();
                            finish();
                        }else{
                            Toast.makeText(CovidSymptoms.this, "Failed to submit!", Toast.LENGTH_LONG ).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}