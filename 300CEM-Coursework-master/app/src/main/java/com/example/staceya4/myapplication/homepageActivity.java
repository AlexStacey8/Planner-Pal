package com.example.staceya4.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class homepageActivity extends AppCompatActivity {

    public void goToSocail(View view)
    {
        //creating new bundle so i can access the username in the socialPlanner activity
        Bundle extras = new Bundle();
        String username = getIntent().getStringExtra("USERNAME");
        //add username to extras, variable name = "USERNAME2"
        extras.putString("USERNAME2", username);
        //creating a new intent with the social planner module
        Intent socailPlanner = new Intent(homepageActivity.this, socialPlannerActivity.class);
        socailPlanner.putExtras(extras);
        //starting social planner activity
        startActivity(socailPlanner);

    }

    public void goToAcademic(View view)
    {
        //creating new bundle so i can access the username in the socialPlanner activity
        Bundle extras = new Bundle();
        String username = getIntent().getStringExtra("USERNAME");
        //add username to extras, variable name = "USERNAME2"
        extras.putString("USERNAME2", username);
        //creating a new intent with the academic planner module
        Intent academicPlanner = new Intent(homepageActivity.this, academicPlannerActivity.class);
        academicPlanner.putExtras(extras);
        //starting acadmeic planner activity
        startActivity(academicPlanner);
    }

    public void goToProfile(View view){
        //creating new bundle so i can access the username in the socialPlanner activity
        Bundle extras = new Bundle();
        String username = getIntent().getStringExtra("USERNAME");
        //add username to extras, variable name = "USERNAME2"
        extras.putString("USERNAME2", username);
        //creating a new intent with the profile module
        Intent profile = new Intent(homepageActivity.this,profileActivity.class);
        profile.putExtras(extras);
        //starting profile activity
        startActivity(profile);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

    }
}
