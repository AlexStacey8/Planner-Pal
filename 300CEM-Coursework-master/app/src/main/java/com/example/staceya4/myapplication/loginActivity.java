package com.example.staceya4.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    //creating a loging function
    public void login(View view) {
        //get values of the user inputs
        EditText username = (EditText) findViewById(R.id.loginUsername);
        EditText password = (EditText) findViewById(R.id.loginPassword);
        //taking the inputs and converting them to strings
        String loginUsername = username.getText().toString();
        String loginPassword = password.getText().toString();
        //create new datbase object and new bundle
        Dbhandler database = new Dbhandler(this);
        Bundle extras = new Bundle();

        //validate user input
        if (database.loginUser(loginUsername, loginPassword)) {
            //if successful go to homepage and put username into extras
            Intent passLogin = new Intent(loginActivity.this, homepageActivity.class);
            extras.putString("USERNAME", loginUsername);
            passLogin.putExtras(extras);
            startActivity(passLogin);
            Toast.makeText(loginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
        } else {
            //if unsuccessful  inform the user with a toast
            Toast.makeText(loginActivity.this, "Failed To Login", Toast.LENGTH_SHORT).show();

        }
    }










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
