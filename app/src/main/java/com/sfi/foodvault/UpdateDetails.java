package com.sfi.foodvault;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateDetails extends AppCompatActivity {

    EditText itemNameEdit, itemTypeEdit, quantityEdit, preferredStockEdit, expirationDateEdit;
    Button updateButton;
    int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_details);

        itemNameEdit = findViewById(R.id.itemname_edit);
        itemTypeEdit = findViewById(R.id.itemtype_edit);
        quantityEdit = findViewById(R.id.quantity_edit);
        preferredStockEdit = findViewById(R.id.preferredstock_edit);
        expirationDateEdit = findViewById(R.id.expirationdate_edit);
        updateButton = findViewById(R.id.update_item);

        getAndSetIntentData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = itemNameEdit.getText().toString();
                String itemType = itemTypeEdit.getText().toString();
                String quantity = quantityEdit.getText().toString();
                String preferredStock = preferredStockEdit.getText().toString();
                String expirationDate = expirationDateEdit.getText().toString();

                if (itemName.isEmpty() || itemType.isEmpty() || quantity.isEmpty() || preferredStock.isEmpty() || expirationDate.isEmpty()) {
                    Toast.makeText(UpdateDetails.this, "All fields are required. Try again", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseConnection databaseConnection = new DatabaseConnection(UpdateDetails.this);
                    boolean result = databaseConnection.updateFoodItem(itemID, itemName, itemType, quantity, preferredStock, expirationDate);
                    if (result == true) {
                        Toast.makeText(UpdateDetails.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateDetails.this, "Failed to update item", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("itemID") && getIntent().hasExtra("itemname") && getIntent().hasExtra("itemtype") &&
                getIntent().hasExtra("quantity") && getIntent().hasExtra("preferredstock") && getIntent().hasExtra("expirationdate")){
            //Getting data
            itemID = getIntent().getIntExtra("itemID", -1);
            String itemName = getIntent().getStringExtra("itemname");
            String itemType = getIntent().getStringExtra("itemtype");
            String quantity = getIntent().getStringExtra("quantity");
            String preferredStock = getIntent().getStringExtra("preferredstock");
            String expirationDate = getIntent().getStringExtra("expirationdate");

            //Setting data
            itemNameEdit.setText(itemName);
            itemTypeEdit.setText(itemType);
            quantityEdit.setText(quantity);
            preferredStockEdit.setText(preferredStock);
            expirationDateEdit.setText(expirationDate);
        } else {
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }
    }
}