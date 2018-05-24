package com.example.staceya4.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class calenderActivity extends AppCompatActivity {
    //private variables to use later in the code
    //the arraylist and arrayAdapter will be used to populate my list view
    private ArrayList<String> events;
    private ArrayAdapter<String> calenderAdapter;
    private ListView eventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        //read items from text file to populate the ListView
        readItems();
        //get listView from calendar xml
        eventList = (ListView) findViewById(R.id.calenderList);
        //create arrayAdapter
        calenderAdapter = new ArrayAdapter<String>(calenderActivity.this, android.R.layout.simple_list_item_1, events);
        //set the listView Adapter
        eventList.setAdapter(calenderAdapter);
        //calling listViewListener function
        setupListViewListener();

    }
    //read items function
    //this fuction reads from a textfiles and thep populates the listView on the calendar page
    private void readItems() {
        //fet username from extras
        String username = getIntent().getStringExtra("USERNAME3");
        //get a file directory and assign to variable
        File filesDir = getFilesDir();
        //creating database handler object
        Dbhandler db = new Dbhandler(calenderActivity.this);
        //getting user id which will be user to access unique text file
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        //reading from textfile and creating new array list
        File calenderFile = new File(filesDir,(userID + "Calender.txt"));
        try {
            events = new ArrayList<String>(FileUtils.readLines(calenderFile));
        } catch (IOException e) {
            events = new ArrayList<String>();
        }
    }
    //write items to text files
    //each file has a uniqueName based on the user id
    private void writeItems() {
        String username = getIntent().getStringExtra("USERNAME3");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(calenderActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        File calenderFile = new File(filesDir, (userID + "Calender.txt"));
        try {
            //write to the text file
            FileUtils.writeLines(calenderFile, events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    //this function creates a menu to allow people to add to the calendar list view
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    //this function is called when the main menu is pressed
    public boolean onOptionsItemSelected (MenuItem item){
        //createing new alert dialog that allows users to add calendar event
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Event For Calender");
        //setting up linearLayout to put widgets into and then used to set the builder view
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        //creating edit text widget
        final EditText eventEditText = new EditText(this);
        eventEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        eventEditText.setHint("event");
        //setting up a datepicker so each task can have a date
        final DatePicker datePicker = new DatePicker(this);
        //getting all date info as ints
        int dayInt = datePicker.getDayOfMonth();
        int monthInt = datePicker.getMonth();
        int yearInt = datePicker.getYear();
        //converting all ints to string then creating a full date string
        String day = String.valueOf(dayInt);
        String month = String.valueOf(monthInt);
        String year = String.valueOf(yearInt);
        final String date = (day + "/" + month + "/" + year);
        //location editText
        final EditText locationEditText = new EditText(this);
        locationEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        locationEditText.setHint("Enter location");
        //postcode editText
        final EditText postcodeEditText = new EditText(this);
        postcodeEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        postcodeEditText.setHint("Enter Postcode");

        //adding views to the layout I created
        layout.addView(eventEditText);
        layout.addView(datePicker);
        layout.addView(locationEditText);
        layout.addView(postcodeEditText);
        //adding layout to builder
        builder.setView(layout);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                //getting all text entered by user
                String event = String.valueOf(eventEditText.getText());
                String location = String.valueOf(locationEditText.getText());
                String postcode = String.valueOf(postcodeEditText.getText());
                //creating string of all the information
                String calenderEvent = "Event: " + event + " / " + "Date: " + date + " / " + "Location: " + location + " / " + "Postcode: " + postcode;
                //adding calendar event to adapter
                calenderAdapter.add(calenderEvent);
                //write information to file
                writeItems();
                //when the user presses the add button a sound is played to notify the user
                final MediaPlayer addSound = MediaPlayer.create(calenderActivity.this,R.raw.add);
                addSound.start();
                //toast to show the user the event has been added
                Toast toast = Toast.makeText(calenderActivity.this, "Event added",Toast.LENGTH_LONG);
                toast.show();
            };
        });
        //setting cancel button which close dialog
        builder.setNegativeButton("Cancel",null);
        //create and show dialog
        builder.create();
        builder.show();
        return true;

    }
    //setting up an on click listener for the listView items so you can delete them from the list
    private void setupListViewListener() {
        eventList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, final int pos, long id) {
                        //setting up an alertDialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(calenderActivity.this);
                        builder.setTitle("Delete Event");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton){
                                String username = getIntent().getStringExtra("USERNAME3");
                                // Remove the item within array at position
                                events.remove(pos);
                                // Refresh the adapter
                                calenderAdapter.notifyDataSetChanged();
                                // Return true consumes the long click event (marks it handled)
                                writeItems();
                                //when the delete button is press a sound is played to notify the user
                                final MediaPlayer deleteSound = MediaPlayer.create(calenderActivity.this,R.raw.delete);
                                deleteSound.start();
                            };
                        });
                        builder.setNegativeButton("Cancel",null);
                        builder.create();
                        builder.show();
                        return true;
                    }

                });
    }
    //open calendar function
    public void openCalendar(View view) {
        //creating and alertDialog to display a calendar view
        AlertDialog.Builder builder = new AlertDialog.Builder(calenderActivity.this);
        builder.setTitle("Your Calendar");
        CalendarView calandar = new CalendarView(calenderActivity.this);
        builder.setView(calandar);
        builder.setNegativeButton("Close",null);
        builder.create();
        builder.show();
    }
}
