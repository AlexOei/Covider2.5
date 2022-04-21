package com.example.covider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BuildingCheck extends AppCompatActivity {


    String code = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_check);
        Intent intent = this.getIntent();
        TextView title = findViewById(R.id.title);
        if (intent != null){
            String name = intent.getStringExtra("Building");
            code = intent.getStringExtra("Code");
            title.setText("Check In for "+ name);



        }
    }

    boolean covid = false;
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

    public void submitButtonHandler(View view) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Buildings")
                .child(code);
        ref.child("health_history").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<Health>> indicator = new GenericTypeIndicator<List<Health>>() {};
                List<Health> healthList = snapshot.getValue(indicator);
                if (healthList == null) healthList = new ArrayList<>();
                if (healthList.size()>0){
                    Health latest =  healthList.get(healthList.size()-1);

                    if (TimeUtil.today(latest.getTime())){
                        Toast.makeText(BuildingCheck.this, "Submitted today!", Toast.LENGTH_LONG ).show();
                        finish();
                        return;
                    }
                }
                healthList.add(new Health(System.currentTimeMillis(),covid));
                ref.child("health_history").setValue(healthList);
                ref.child("haveCovid").setValue(covid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(BuildingCheck.this, "submit successfully!", Toast.LENGTH_LONG ).show();
                            finish();
                        }else{
                            Toast.makeText(BuildingCheck.this, "Failed to submit!", Toast.LENGTH_LONG ).show();
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