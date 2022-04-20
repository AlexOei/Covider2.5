package com.example.covider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.model.Section;
import com.example.covider.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClassSectionActivity extends AppCompatActivity {
    Button submitClasses, switchViews;
    EditText firstSection, firstCode, secondSection, secondCode;
    EditText thirdCode, thirdSection, fourthCode, fourthSection;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_section);


        submitClasses = findViewById(R.id.submitClasses);
        firstCode = findViewById(R.id.code1);
        firstSection = findViewById(R.id.firstSection);
        secondCode = findViewById(R.id.code2);
        secondSection = findViewById(R.id.section2);
        thirdCode = findViewById(R.id.code3);
        thirdSection = findViewById(R.id.section3);
        fourthCode = findViewById(R.id.code4);
        fourthSection = findViewById(R.id.section4);
        switchViews = findViewById(R.id.frequently);

        switchViews.setOnClickListener(view -> {
            Intent i = new Intent(view.getContext(), FrequentlyVisited.class);
            startActivity(i);
        });


        submitClasses.setOnClickListener(view -> {
            String sectionOne = firstSection.getText().toString().trim();
            String codeOne = firstCode.getText().toString().trim();
            String sectionTwo = secondSection.getText().toString().trim();
            String codeTwo = secondCode.getText().toString().trim();
            String sectionThree = thirdSection.getText().toString().trim();
            String codeThree = thirdCode.getText().toString().trim();
            String sectionFour = fourthSection.getText().toString().trim();
            String codeFour = fourthCode.getText().toString().trim();

//            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users")
//                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//            // get current user object
//            userRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    user = snapshot.getValue(User.class);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//
//            });

//            Map<String,Section> section = new HashMap<>();
//                        Section section1 = new Section();
//                        section1.setSectionNum(sectionOne);
//                        section1.setBuilding(codeOne);
//                        section1.getUser().add(user);
//                        section.put(sectionOne, section1);
//            //DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Sections");
//            //df.setValue(section);
//
//            FirebaseDatabase.getInstance().getReference("Users")
//                    .child("sections").setValue(sectionOne)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                Toast.makeText(ClassSectionActivity.this, "Section has been registered successfully!", Toast.LENGTH_LONG ).show();
//                            }else{
//                                Toast.makeText(ClassSectionActivity.this, "Failed to register!", Toast.LENGTH_LONG ).show();
//                            }
//                        }
//                    });
//


//            DatabaseReference secRef1 = FirebaseDatabase.getInstance().getReference().child("Sections");
//            secRef1.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
            //            if (!snapshot.exists()) {
//                        Map<String,Section> section = new HashMap<>();
//                        Section section1 = new Section();
//                        section1.setSectionNum(sectionOne);
//                        section1.setBuilding(codeOne);
//                        section1.getUser().add(user);
//                        section.put(sectionOne, section1);
//                        secRef1.setValue(section).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()){
//                                    Toast.makeText(ClassSectionActivity.this, "Section has been added successfully!", Toast.LENGTH_LONG ).show();
//                                }else{
//                                    Toast.makeText(ClassSectionActivity.this, "Failed to add!", Toast.LENGTH_LONG ).show();
//                                }
//                            }
//                        });
            //            }
//                }

//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    //Log.d(TAG, databaseError.getMessage());
//                }
//            });

            List<Section> Codes = new ArrayList<>();
            if (sectionOne.isEmpty()) {
                firstSection.setError("Please Enter at least one class");
                return;
            }

            if (codeOne.isEmpty()) {
                firstCode.setError("Please Enter at least one class");
                return;
            }

            Codes.add(new Section(sectionOne,codeOne,null));

            if (!codeTwo.isEmpty()) {
                Codes.add(new Section(sectionTwo,codeTwo,null));
            }

            if (!codeThree.isEmpty()) {
                Codes.add(new Section(sectionThree,codeThree,null));
            }

            if (!codeFour.isEmpty()) {
                Codes.add(new Section(sectionFour,codeFour,null));
            }

            FirebaseDatabase.getInstance().getReference("Users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("should_visit").setValue(Codes)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ClassSectionActivity.this, "Code has been registered successfully!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ClassSectionActivity.this, "Failed to register!", Toast.LENGTH_LONG).show();
                        }
                    });
        });


    }


}