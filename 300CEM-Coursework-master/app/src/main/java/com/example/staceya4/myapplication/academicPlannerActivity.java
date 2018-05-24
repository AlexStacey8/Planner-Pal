package com.example.staceya4.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class academicPlannerActivity extends AppCompatActivity {
    //go to calendar page function
    public void goToCalender(View view)
    {
        //creating new bundle so i can access the username in the calendar activity
        Bundle extras = new Bundle();
        //get username from extras
        String username = getIntent().getStringExtra("USERNAME2");
        //creating a new intent with the calandar module
        Intent calender = new Intent(academicPlannerActivity.this, calenderActivity.class);
        //add username to extras, variable name = "USERNAME3"
        extras.putString("USERNAME3", username);
        calender.putExtras(extras);
        //start calendar activity
        startActivity(calender);
    }
    public void goToTasks(View view)
    {
        //creating new bundle so i can access the username in the taskList activity
        Bundle extras = new Bundle();
        String username = getIntent().getStringExtra("USERNAME2");
        //creating a new intent with the taskList module
        Intent tasks = new Intent(academicPlannerActivity.this, taskListActivity.class);
        //add username to extras, variable name = "USERNAME3"
        extras.putString("USERNAME3", username);
        tasks.putExtras(extras);
        //start taskList activity
        startActivity(tasks);
    }
    public void goToNotes(View view)
    {
        //creating new bundle so i can access the username in the notes activity
        Bundle extras = new Bundle();
        String username = getIntent().getStringExtra("USERNAME2");
        //creating a new intent with the notes module
        Intent notes = new Intent(academicPlannerActivity.this, notesActivity.class);
        extras.putString("USERNAME3", username);
        notes.putExtras(extras);
        //start notes activity
        startActivity(notes);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_planner);

    }
}
