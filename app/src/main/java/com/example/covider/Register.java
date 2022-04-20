package com.example.covider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covider.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText et_firstname, et_lastname, et_email, et_password;
    Button btn_register, btn_login;
    FirebaseAuth fAuth;
    //FirebaseFirestore fStore;
    DatabaseReference reference;
    String userID;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_xiao);

        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        radioGroup = findViewById(R.id.radioGroup);

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ClassSectionActivity.class));
        }

        btn_login.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });

        btn_register.setOnClickListener(view -> {
            String firstName = et_firstname.getText().toString().trim();
            String lastName = et_lastname.getText().toString().trim();
            String email = et_email.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            boolean isInstructor;
            int radioButtonID = radioGroup.getCheckedRadioButtonId();

            if (validateInput(firstName, lastName, email, password)) {
                if (radioButtonID == -1) {
                    Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    selectedRadioButton = findViewById(radioButtonID);
                    String selectedRbText = selectedRadioButton.getText().toString();
                    if ("Instructor".equals(selectedRbText)) {
                        isInstructor = true;
                    } else {
                        isInstructor = false;
                    }
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        User user = new User(firstName, lastName, email, password, isInstructor);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_LONG ).show();
                                    }else{
                                        Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_LONG ).show();
                                    }
                                });
                    }else{
                        Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_LONG ).show();
                    }
                });
            }

//            if (firstName.isEmpty()) {
//                et_firstname.setError("First Name is required");
//                et_firstname.requestFocus();
//                return;
//            }
//
//            if (lastName.isEmpty()) {
//                et_lastname.setError("Last Name is required");
//                et_lastname.requestFocus();
//                return;
//            }
//
//            if (email.isEmpty()) {
//                et_email.setError("USC email is required");
//                et_email.requestFocus();
//                return;
//            }
//            if (password.isEmpty()) {
//                et_password.setError("Password is required");
//                et_password.requestFocus();
//                return;
//            }
//
//            if (password.length() < 6){
//                et_password.setError("Password must be 6 or more characters");
//                et_password.requestFocus();
//            }

//            if (radioButtonID == -1) {
//                Toast.makeText(getApplicationContext(), "Please select one choice", Toast.LENGTH_SHORT).show();
//                return;
//            } else {
//                selectedRadioButton = findViewById(radioButtonID);
//                String selectedRbText = selectedRadioButton.getText().toString();
//                if (selectedRbText == "Instructor") {
//                    isInstructor = true;
//                } else {
//                    isInstructor = false;
//                }
//            }
//
//            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()){
//                        Map<String, String>  freq_visited = new HashMap<String, String>();
//                        freq_visited.put("", "");
//                        Map<String, String>  should_visit = new HashMap<String, String>();
//                        should_visit.put("", "");
//                        ArrayList<String> health_history = new ArrayList<String>();
//                        health_history.add("");
//                        ArrayList<User> closeContacts = new ArrayList<User>();
//                        //User user = new User(firstName, lastName, email, password, isInstructor, freq_visited, should_visit, health_history, closeContacts, false);
//                        User user = new User(firstName, lastName, email, password, isInstructor);
//                        FirebaseDatabase.getInstance().getReference("Users")
//                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_LONG ).show();
//                                        }else{
//                                            Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_LONG ).show();
//                                        }
//                                    }
//                                });
//                    }else{
//                        Toast.makeText(Register.this, "Failed to register!", Toast.LENGTH_LONG ).show();
//                    }
//                }
//            });
        });
    }

    boolean validateInput(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty()) {
            et_firstname.setError("First Name is required");
            et_firstname.requestFocus();
            return false;
        }

        if (lastName.isEmpty()) {
            et_lastname.setError("Last Name is required");
            et_lastname.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            et_email.setError("USC email is required");
            et_email.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            et_password.setError("Password is required");
            et_password.requestFocus();
            return false;
        }

        if (password.length() < 6){
            et_password.setError("Password must be 6 or more characters");
            et_password.requestFocus();
            return false;
        }
        return true;
    }
}