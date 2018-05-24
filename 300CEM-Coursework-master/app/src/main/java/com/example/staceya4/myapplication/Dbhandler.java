package com.example.staceya4.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by staceya4 on 08/11/2017.
 */

public class Dbhandler extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating user table to store user information
        db.execSQL("CREATE TABLE users (colUserID INTEGER PRIMARY KEY AUTOINCREMENT,colForename TEXT, colSurname TEXT, colEmail TEXT, colUsername TEXT, colPassword TEXT, colImage BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Dbhandler(Context context){
        super(context,"plannerPalDB",null,1);
    }

    //function to login user
    public boolean loginUser(String username, String password) {
        //getting readble database to execute sql on
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM users WHERE colUsername= '" + username + "'AND colPassword ='" + password + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            return true;
        }
        else
        {
            return  false;
        }
    }
    //function to get the email of the user who is currently logged into the app
    public String getEmail(String username)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT colEmail FROM users WHERE colUsername = '" + username + "'";

        Cursor cursor = db.rawQuery(query, null);
        String value = null;

        while(cursor.moveToNext())
        {
            value = cursor.getString(0);
            return value;
        }
        return value;
    }
    //function to get forename of the user who is currently logged in
    public String getForename(String username)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT colForename FROM users WHERE colUsername = '" + username + "'";

        Cursor cursor = db.rawQuery(query, null);
        String value = null;

        while(cursor.moveToNext())
        {
            value = cursor.getString(0);
            return value;
        }
        return value;
    }
    //function to get surname
    public String getSurname(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT colSurname FROM users WHERE colUsername = '" + username + "'";

        Cursor cursor = db.rawQuery(query, null);
        String value = null;

        while(cursor.moveToNext())
        {
            value = cursor.getString(0);
            return value;
        }
        return value;
    }
    //functions to change email, password and username
    public boolean changeEmail(String email, String username){
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE users SET colEmail = '" + email + "' WHERE colUsername = '" + username + "'";
        db.execSQL(query);
        return true;
    }
    public boolean changeUsername(String username, String username2){
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE users SET colUsername = '" + username + "' WHERE colUsername = '" + username2 + "'";
        db.execSQL(query);
        return true;
    }
    public boolean changePassword(String password, String username){
        SQLiteDatabase db = getReadableDatabase();
        String query = "UPDATE users SET colPassword = '" + password + "' WHERE colUsername = '" + username + "'";
        db.execSQL(query);
        return true;
    }
    //function to add user into database
    public void addUser(User user) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        //creating content values
        ContentValues contentValues = new ContentValues();
        contentValues.put("colForename", user.getForename());
        contentValues.put("colSurname", user.getSurname());
        contentValues.put("colEmail", user.getEmail());
        contentValues.put("colUsername", user.getUsername());
        contentValues.put("colPassword", user.getPassword());


        long result = sqLiteDatabase.insert("users", null, contentValues);

        if (result > 0) {
            Log.d("DbHandler", "inserted successfully");
        }
        else {
            Log.d("DbHandler", "failed to insert");
        }
        sqLiteDatabase.close();
    }
    //get user id from database based on username
    public Integer getUserID(String username){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT colUserID FROM users WHERE colUsername = '" + username + "'";
        Cursor cursor = db.rawQuery(query, null);
        int value = 0;

        while(cursor.moveToNext())
        {
            value = cursor.getInt(0);
            return value;
        }
        return value;

    }
    //fetching profile picture from database function, this is required as for the profile page i need to access the profile picture
    public Bitmap getProfilePicture(int userID){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT colImage FROM users WHERE colUserID = '" + userID + "'";
        Cursor cursor = db.rawQuery(query,null);
        Bitmap image = null;
        while(cursor.moveToNext()){
            //getting blob from database
            byte[] photo = cursor.getBlob(0);
            if (photo == null){
                continue;
            }else {
                //creating byte array to generate bitmap image using bitmapFactory
                ByteArrayInputStream imageStream = new ByteArrayInputStream(photo);
                image = BitmapFactory.decodeStream(imageStream);
                return image;
            }
        }
        return image;
    }
    //this function will store the image into the database
    public void storeProfilePicture(int userID, Bitmap image){
        SQLiteDatabase db = Dbhandler.this.getWritableDatabase();
        Bitmap bitmap = image;
        //creating byte array output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //compressing bitmap image that is take by the device camera
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[] byteArray = outputStream.toByteArray();
        //setting up content values to update database
        ContentValues values = new ContentValues();
        values.put("colImage",byteArray);
        String where = "colUserID = '" + userID + "'";
        //updating user table by adding new image
        db.update("users",values,where,null);
    }

}
