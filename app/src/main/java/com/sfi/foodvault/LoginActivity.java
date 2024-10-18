package com.sfi.foodvault;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.sfi.foodvault.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseConnection databaseConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseConnection = new DatabaseConnection(this);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                if(email.isEmpty() || password.isEmpty())
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkAccount = databaseConnection.checkEmailPassword(email, password);

                    if (checkAccount == true) {
                        Toast.makeText(LoginActivity.this, "Access granted", Toast.LENGTH_SHORT).show();
                        Cursor cursor = databaseConnection.getUserIdByEmail(email);
                        if (cursor.moveToFirst()) {
                            long userID = cursor.getLong(0);
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            intent.putExtra("userID", userID);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Account Details", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}