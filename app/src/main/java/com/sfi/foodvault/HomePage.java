package com.sfi.foodvault;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sfi.foodvault.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    ActivityHomeBinding binding;
    DatabaseConnection databaseConnection;
    ArrayList<Integer> itemID;
    ArrayList<String> itemName, itemType, quantity, preferredStock, expirationDate;
    ItemAdapter itemAdapter;
    long userID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton addButton = findViewById(R.id.addbutton);

        userID = getIntent().getLongExtra("userID", -1);

        // action to add items
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, AddDetails.class);
                intent.putExtra("userID", userID);
                startActivityForResult(intent,1);
            }
        });

        databaseConnection = new DatabaseConnection(HomePage.this);
        itemID = new ArrayList<>();
        itemName = new ArrayList<>();
        itemType = new ArrayList<>();
        quantity = new ArrayList<>();
        preferredStock = new ArrayList<>();
        expirationDate = new ArrayList<>();

        storeData(userID);

        itemAdapter = new ItemAdapter( this, itemID, itemName, itemType, quantity, preferredStock, expirationDate);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode== RESULT_OK) {
            // Clear existing data before refreshing
            itemID.clear();
            itemName.clear();
            itemType.clear();
            quantity.clear();
            preferredStock.clear();
            expirationDate.clear();

            storeData(userID);

            // Notify the adapter about data change
            itemAdapter.notifyDataSetChanged();
        }
    }

     public void storeData(long userID) {
         if (userID != -1) {
             Cursor cursor = databaseConnection.getAllItems((int) userID);
             if (cursor.getCount() == 0) {
                 Toast.makeText(this, "No items found", Toast.LENGTH_SHORT).show();
             } else {
                 while (cursor.moveToNext()) {
                     itemID.add(cursor.getInt(0));
                     itemName.add(cursor.getString(2));
                     itemType.add(cursor.getString(3));
                     quantity.add(cursor.getString(4));
                     preferredStock.add(cursor.getString(5));
                     expirationDate.add(cursor.getString(6));
                 }
             }
         }
     }
}
