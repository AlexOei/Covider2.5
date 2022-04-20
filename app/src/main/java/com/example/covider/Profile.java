package com.example.covider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        TextView first = findViewById(R.id.name_first);
        TextView last = findViewById(R.id.name_last);
        TextView email = findViewById(R.id.email_profile);
        TextView haveCovid = findViewById(R.id.covid_profile);
        Button viewClass = findViewById(R.id.view_class);
        Button viewSections = findViewById(R.id.view_sections);
        //TextView instructor = findViewById(R.id.isInstructor);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                first.setText(user.getFirstName());
                last.setText(user.getLastName());
                email.setText(user.getEmail());
                haveCovid.setText(String.valueOf(user.isHaveCovid()));
                if (user.isInstructor()) {
                    viewClass.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        viewClass.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), InstructorNotification.class);
            startActivity(i);
        });



    }
}