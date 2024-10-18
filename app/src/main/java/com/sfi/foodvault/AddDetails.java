package com.sfi.foodvault;

import androidx.appcompat.app.AppCompatActivity;

import com.sfi.foodvault.databinding.DetailsPageBinding;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AddDetails extends AppCompatActivity {

    DetailsPageBinding binding;
    DatabaseConnection databaseConnection;
    long userID;
    boolean isUpdate;
    int itemID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DetailsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseConnection = new DatabaseConnection(this);
        userID = getIntent().getLongExtra("userID", -1);

        // Check if this is an update operation
        isUpdate = getIntent().getBooleanExtra("isUpdate", false);
        itemID = getIntent().getIntExtra("itemID", -1);

        if (isUpdate && itemID != -1) {
            // Populate fields with existing data if updating
            Cursor cursor = databaseConnection.getAllItems((int) userID);
            if (cursor.moveToFirst()) {
                binding.itemname.setText(cursor.getString(2));
                binding.itemtype.setText(cursor.getString(3));
                binding.quantity.setText(cursor.getString(4));
                binding.preferredstock.setText(cursor.getString(5));
                binding.expirationdate.setText(cursor.getString(6));
            }
        }

        binding.addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = binding.itemname.getText().toString();
                String itemType = binding.itemtype.getText().toString();
                String quantity = binding.quantity.getText().toString();
                String preferredStock = binding.preferredstock.getText().toString();
                String expirationDate = binding.expirationdate.getText().toString();

                if (itemName.isEmpty() || itemType.isEmpty() || quantity.isEmpty() || preferredStock.isEmpty() || expirationDate.isEmpty()) {
                    Toast.makeText(AddDetails.this, "All fields are required. Try again", Toast.LENGTH_SHORT).show();
                } else {
                    boolean result;
                    if (isUpdate) {
                        result = databaseConnection.updateFoodItem(itemID, itemName, itemType, quantity, preferredStock, expirationDate);
                    } else {
                        result = databaseConnection.insertFoodItems((int) userID, itemName, itemType, quantity, preferredStock, expirationDate);
                    }

                    if (result) {
                        Toast.makeText(AddDetails.this, isUpdate ? "Item updated successfully" : "Item added successfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(AddDetails.this, isUpdate ? "Failed to update item" : "Failed to add item", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}