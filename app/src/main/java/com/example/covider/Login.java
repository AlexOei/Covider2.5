package com.example.covider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Login extends AppCompatActivity {
    Button btn_lregister, btn_llogin;
    EditText et_lemail, et_lpassword;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    TextView forgotTextLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_xiao);

        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }

        et_lemail = findViewById(R.id.et_lemail);
        et_lpassword = findViewById(R.id.et_lpassword);
        btn_llogin = findViewById(R.id.btn_llogin);
        btn_lregister = findViewById(R.id.btn_lregister);
        forgotTextLink = findViewById(R.id.forgotPassword);

        btn_llogin.setOnClickListener(view -> {
            String email = et_lemail.getText().toString().trim();
            String password = et_lpassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                et_lemail.setError("USC email is required");
                et_lemail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                et_lpassword.setError("Password is required");
                et_lpassword.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_lemail.setError("Provide a valid USC email!");
                et_lemail.requestFocus();
                return;
            }

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Login.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btn_lregister.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Register.class);
            startActivity(i);
        });

        forgotTextLink.setOnClickListener(view -> {
            EditText resetMail = new EditText(view.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("Reset Password?");
            passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link.");
            passwordResetDialog.setView(resetMail);


                passwordResetDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                    String mail = resetMail.getText().toString();
                    if (!mail.isEmpty() && mail != null) {
                        fAuth.sendPasswordResetEmail(mail)
                                .addOnSuccessListener(unused -> Toast.makeText(Login.this, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(Login.this, "Error! Reset Link is not Sent " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }

//                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(Login.this, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(Login.this, "Error! Reset Link is not Sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
                });

                passwordResetDialog.setNegativeButton("No", (dialogInterface, i) -> {

                });
            passwordResetDialog.create().show();
        });
    }
}