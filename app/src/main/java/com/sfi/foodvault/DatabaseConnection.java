package com.sfi.foodvault;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseConnection extends SQLiteOpenHelper {

    public static final String databaseName = "FoodVault.db";

    public DatabaseConnection(@Nullable Context context) {
        super(context, "FoodVault.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase VaultDatabase) {
        VaultDatabase.execSQL("create Table allUsers(userID INTEGER PRIMARY KEY AUTOINCREMENT, firstName TEXT, lastName TEXT, userName TEXT UNIQUE, email TEXT, contactNo TEXT, password TEXT)");
        VaultDatabase.execSQL("create Table foodItems(itemID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, itemName TEXT, itemType TEXT, quantity TEXT, preferredStock TEXT, expirationDate TEXT, FOREIGN KEY (userID) REFERENCES allUsers(userID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase VaultDatabase, int oldVersion, int newVersion) {
        VaultDatabase.execSQL("drop Table if exists allUsers");
        VaultDatabase.execSQL("drop Table if exists foodItems");
    }
     public Boolean insertAccountDetails(String firstName, String lastName, String userName, String email, String contactNo, String password) {
        SQLiteDatabase VaultDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstName);
        contentValues.put("lastname", lastName);
        contentValues.put("username", userName);
        contentValues.put("email", email);
        contentValues.put("contactno", contactNo);
        contentValues.put("password", password);
        long result = VaultDatabase.insert("allUsers", null, contentValues);

        if (result == -1){
            return false;
        } else{
            return true;
        }
     }

     public Boolean checkEmail(String email){
        SQLiteDatabase VaultDatabase = this.getWritableDatabase();
        Cursor cursor = VaultDatabase.rawQuery("SELECT * from allUsers where email = ?", new String[]{email});

        if (cursor.getCount() > 0){
            return true;
        } else{
            return false;
        }
     }

     public Boolean checkEmailPassword(String email, String password){
         SQLiteDatabase VaultDatabase = this.getWritableDatabase();
         Cursor cursor = VaultDatabase.rawQuery("SELECT * from allUsers where email = ? and password = ?", new String[]{email, password});

         if (cursor.getCount() > 0){
             return true;
         } else{
             return false;
         }
     }

    public Cursor getUserIdByEmail(String email) {
        SQLiteDatabase VaultDatabase = this.getReadableDatabase();
        return VaultDatabase.rawQuery("SELECT userID from allUsers where email = ?", new String[]{email});
    }

    public Boolean insertFoodItems(int userID, String itemName, String itemType, String quantity, String preferredStock, String expirationDate) {
        SQLiteDatabase VaultDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userID);
        contentValues.put("itemname", itemName);
        contentValues.put("itemtype", itemType);
        contentValues.put("quantity", quantity);
        contentValues.put("preferredstock", preferredStock);
        contentValues.put("expirationdate", expirationDate);
        long result = VaultDatabase.insert("foodItems", null, contentValues);

        if (result == -1){
            return false;
        } else{
            return true;
        }
    }

    public Cursor getAllItems(int userID) {
        SQLiteDatabase VaultDatabase = this.getReadableDatabase();
        return VaultDatabase.rawQuery("SELECT * from foodItems WHERE userID = ?", new String[]{String.valueOf(userID)});
    }

    public Boolean updateFoodItem(int itemID, String itemName, String itemType, String quantity, String preferredStock, String expirationDate) {
        SQLiteDatabase VaultDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemname", itemName);
        contentValues.put("itemtype", itemType);
        contentValues.put("quantity", quantity);
        contentValues.put("preferredstock", preferredStock);
        contentValues.put("expirationdate", expirationDate);
        long result = VaultDatabase.update("foodItems", contentValues, "itemID = ?", new String[]{String.valueOf(itemID)});

        if (result == -1){
            return false;
        } else{
            return true;
        }
    }

}
