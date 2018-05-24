package com.example.staceya4.myapplication;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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

public class taskListActivity extends AppCompatActivity {
    //creating private variables which will be used later
    //the arrayList and arrayAdapter are use to populate the list view
    private ArrayList<String> tasks;
    private ArrayAdapter<String> taskAdapter;
    private ListView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        //read items from text file
        readItems();
        //get listview from xml file
        taskList = (ListView) findViewById(R.id.taskList);
        //create adapter
        taskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        taskList.setAdapter(taskAdapter);
        //calling listViewListener function
        setupListViewListener();

    }
    //function to read from text file
    private void readItems() {
        String username = getIntent().getStringExtra("USERNAME3");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(taskListActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        File todoFile = new File(filesDir,(userID + "TaskList.txt"));
        try {
            tasks = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            tasks = new ArrayList<String>();
        }
    }
    //function to write to file
    private void writeItems() {
        String username = getIntent().getStringExtra("USERNAME3");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(taskListActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        File todoFile = new File(filesDir, (userID + "TaskList.txt"));
        try {
            FileUtils.writeLines(todoFile, tasks);
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
        builder.setTitle("Enter New Task");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText taskEditText = new EditText(this);
        taskEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        taskEditText.setHint("task");
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
                String taskAndDate = task + " - Deadline: " + date;
                taskAdapter.add(taskAndDate);
                writeItems();
                final MediaPlayer addSound = MediaPlayer.create(taskListActivity.this,R.raw.add);
                addSound.start();
                Toast toast = Toast.makeText(taskListActivity.this, "Task added",Toast.LENGTH_LONG);
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
        taskList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, final int pos, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(taskListActivity.this);
                        builder.setTitle("Delete Task");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton){
                                String username = getIntent().getStringExtra("USERNAME3");
                                // Remove the item within array at position
                                tasks.remove(pos);
                                // Refresh the adapter
                                taskAdapter.notifyDataSetChanged();
                                // Return true consumes the long click event (marks it handled)
                                writeItems();
                                final MediaPlayer deleteSound = MediaPlayer.create(taskListActivity.this,R.raw.delete);
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
