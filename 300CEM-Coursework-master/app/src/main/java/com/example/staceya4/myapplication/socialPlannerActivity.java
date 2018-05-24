package com.example.staceya4.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class socialPlannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_planner);
    }
    //function to go to calender page
    public void goToCalenderS(View view)
    {
        Bundle extras = new Bundle();
        String username = getIntent().getStringExtra("USERNAME2");
        Intent calender = new Intent(socialPlannerActivity.this, calenderActivity.class);
        extras.putString("USERNAME3", username);
        calender.putExtras(extras);
        startActivity(calender);
    }
    //function to go to the toDolist page
    public void goToDoList(View view){
        Bundle extras = new Bundle();
        String username = getIntent().getStringExtra("USERNAME2");
        extras.putString("USERNAME4", username);
        Intent toDo = new Intent(socialPlannerActivity.this,ToDoListActivity.class);
        toDo.putExtras(extras);
        startActivity(toDo);
    }
    //function to go to the chopping list page
    public void goToShoppingList(View view){
        Bundle extras = new Bundle();
        String username = getIntent().getStringExtra("USERNAME2");
        extras.putString("USERNAME5", username);
        Intent shoppingList= new Intent(socialPlannerActivity.this,shoppingListActivity.class);
        shoppingList.putExtras(extras);
        startActivity(shoppingList);
    }
}
