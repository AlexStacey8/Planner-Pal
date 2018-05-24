package com.example.staceya4.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {
    private ArrayList<String> toDoTasks;
    private ArrayAdapter<String> toDoAdapter;
    private ListView toDoList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        readItems();
        toDoList = (ListView) findViewById(R.id.toDoList);
        toDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, toDoTasks);
        toDoList.setAdapter(toDoAdapter);
        setupListViewListener();


    }
    //function to read from text file
    private void readItems() {
        String username = getIntent().getStringExtra("USERNAME4");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(ToDoListActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        File toDoListFile = new File(filesDir,(userID + "toDoList.txt"));
        try {
            toDoTasks = new ArrayList<String>(FileUtils.readLines(toDoListFile));
        } catch (IOException e) {
            toDoTasks = new ArrayList<String>();
        }
    }
    //function to write to file
    private void writeItems() {
        String username = getIntent().getStringExtra("USERNAME4");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(ToDoListActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        File toDoListFile = new File(filesDir, (userID + "toDoList.txt"));
        try {
            FileUtils.writeLines(toDoListFile, toDoTasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Task To Do");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText taskEditText = new EditText(this);
        taskEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        taskEditText.setHint("task to do");
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
        //adding views to the layout I created
        layout.addView(taskEditText);
        layout.addView(datePicker);
        //adding layout to builder
        builder.setView(layout);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                String task = String.valueOf(taskEditText.getText());
                String toDoTask = task + " - Complete By: " + date;
                toDoAdapter.add(toDoTask);
                writeItems();
                final MediaPlayer addSound = MediaPlayer.create(ToDoListActivity.this,R.raw.add);
                addSound.start();
                Toast toast = Toast.makeText(ToDoListActivity.this, "task added",Toast.LENGTH_SHORT);
                toast.show();
            };
        });
        builder.setNegativeButton("Cancel",null);
        builder.create();
        builder.show();
        return true;

    }
    //setting up on click listener from when a list view item is pressed
    private void setupListViewListener() {
        toDoList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, final int pos, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
                        builder.setTitle("Delete Item");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton){
                                // Remove the item within array at position
                                toDoTasks.remove(pos);
                                // Refresh the adapter
                                toDoAdapter.notifyDataSetChanged();
                                // Return true consumes the long click event (marks it handled)
                                writeItems();
                                final MediaPlayer deleteSound = MediaPlayer.create(ToDoListActivity.this,R.raw.delete);
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

}

