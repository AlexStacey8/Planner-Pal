package com.example.staceya4.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    //go to login page
    public void goToLogin(View view)
    {
        Intent login = new Intent(MainActivity.this, loginActivity.class);
        startActivity(login);
    }
    //go to registration page
    public void goToRegistration(View view)
    {
        Intent registration = new Intent(MainActivity.this, registrationActivity.class);
        startActivity(registration);
    }
    //to be remove later just a placeholder


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


}
