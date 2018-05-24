package com.example.staceya4.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.UnicodeSetSpanner;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class registrationActivity extends AppCompatActivity {


    private EditText forename;
    private EditText surname;
    private EditText email;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //geting all values from the editText widgets in the xml file
        forename = (EditText) findViewById(R.id.forename);
        surname = (EditText) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

    }
    //save user inputs to the database after getting all the editText inputs as strings
    public void saveToDatabase(View view){
        String aForename = forename.getText().toString();
        String aSurname = surname.getText().toString();
        String aEmail = email.getText().toString();
        String aUsername = username.getText().toString();
        String aPassword = password.getText().toString();
        Dbhandler db = new Dbhandler(this);
        db.addUser(new User(aForename,aSurname,aEmail,aUsername,aPassword));

        Toast.makeText(registrationActivity.this,
                "Account created", Toast.LENGTH_LONG).show();

        Intent login = new Intent(registrationActivity.this, loginActivity.class);
        startActivity(login);
    }


}
