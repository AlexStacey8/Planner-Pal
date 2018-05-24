package com.example.staceya4.myapplication;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class notesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        readItems();
    }
    //read items from text file
    private void readItems() {
        String username = getIntent().getStringExtra("USERNAME3");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(notesActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        File notesFile = new File(filesDir,(userID + "Notes.txt"));
        EditText notesEditText = (EditText)findViewById(R.id.notes);
        try {
            notesEditText.setText(FileUtils.readFileToString(notesFile));
        } catch (IOException e) {
            notesEditText.setText("");
        }
    }
    //write items to text file
    private void writeItems() {
        String username = getIntent().getStringExtra("USERNAME3");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(notesActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        EditText notesEditText = (EditText)findViewById(R.id.notes);
        String notes = notesEditText.getText().toString();
        File notesFile = new File(filesDir, (userID + "Notes.txt"));
        try {
            FileUtils.writeStringToFile(notesFile,notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //function that saves notes page
    public void saveNotes(View view){
        //write items to text file
        writeItems();
        //sound played when the save button is pressed as well as toast
        final MediaPlayer addSound = MediaPlayer.create(notesActivity.this,R.raw.add);
        addSound.start();
        Toast toast = Toast.makeText(notesActivity.this, "Notes Saved",Toast.LENGTH_LONG);
        toast.show();
    }
    //function to clear notes page
    public void clearAll(View view){
        //creating and alert dialog so the user can confirm their action
        AlertDialog.Builder builder = new AlertDialog.Builder(notesActivity.this);
        builder.setTitle("Clear Note Page");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                EditText notesEDitText = (EditText)findViewById(R.id.notes);
                //set text to be empty
                notesEDitText.setText("");
                //write changes to file
                writeItems();
                //play delete sound when user confirms
                final MediaPlayer deleteSound = MediaPlayer.create(notesActivity.this,R.raw.delete);
                deleteSound.start();
            };
        });
        builder.setNegativeButton("Cancel",null);
        builder.create();
        builder.show();

    }

}
