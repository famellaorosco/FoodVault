package com.sfi.foodvault;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.sfi.foodvault.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivitySignupBinding binding;
        DatabaseConnection databaseConnection;

        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseConnection = new DatabaseConnection(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.firstname.getText().toString();
                String lastName = binding.lastname.getText().toString();
                String userName = binding.username.getText().toString();
                String email = binding.signupEmail.getText().toString();
                String contactNo = binding.contactno.getText().toString();
                String password = binding.signupPassword.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || email.isEmpty() || contactNo.isEmpty() || password.isEmpty())
                    Toast.makeText(SignupActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                else {
                        Boolean checkUserEmail = databaseConnection.checkEmail(email);

                        if (checkUserEmail == false) {
                            Boolean insert = databaseConnection.insertAccountDetails(firstName, lastName, userName, email, contactNo, password);

                            if (insert == true) {
                                Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                Cursor cursor = databaseConnection.getUserIdByEmail(email);
                                if (cursor.moveToFirst()) {
                                    long userID = cursor.getLong(0);
                                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                    intent.putExtra("userID", userID);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(SignupActivity.this, "Failed to create account", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignupActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }