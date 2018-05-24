package com.example.staceya4.myapplication;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class shoppingListActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemAdapter;
    private ListView shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        readItems();
        shoppingList = (ListView) findViewById(R.id.shoppingList);
        itemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
        shoppingList.setAdapter(itemAdapter);
        setupListViewListener();


    }
    //function to read from text file
    private void readItems() {
        String username = getIntent().getStringExtra("USERNAME5");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(shoppingListActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        File shoppingListFile = new File(filesDir,(userID + "ShoppingList.txt"));
        try {
            items = new ArrayList<String>(FileUtils.readLines(shoppingListFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }
    //function to write to a text file
    private void writeItems() {
        String username = getIntent().getStringExtra("USERNAME5");
        File filesDir = getFilesDir();
        Dbhandler db = new Dbhandler(shoppingListActivity.this);
        int intID = db.getUserID(username);
        String userID = String.valueOf(intID);
        File shoppingListFile = new File(filesDir, (userID + "ShoppingList.txt"));
        try {
            FileUtils.writeLines(shoppingListFile, items);
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
    //create an alertDialog to add item to the shopping list
    public boolean onOptionsItemSelected (MenuItem item){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter New Item");
        final EditText itemEditText = new EditText(this);
        itemEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        itemEditText.setHint("item");
        builder.setView(itemEditText);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                String item = String.valueOf(itemEditText.getText());
                itemAdapter.add(item);
                writeItems();
                final MediaPlayer addSound = MediaPlayer.create(shoppingListActivity.this,R.raw.add);
                addSound.start();
                Toast toast = Toast.makeText(shoppingListActivity.this, "item added",Toast.LENGTH_SHORT);
                toast.show();
            };
        });
        builder.setNegativeButton("Cancel",null);
        builder.create();
        builder.show();
        return true;

    }
    //function to clear list view so the user can start another shopping list
    public void deleteAll(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all items");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                items.removeAll(items);
                itemAdapter.notifyDataSetChanged();
                writeItems();
                final MediaPlayer deleteSound = MediaPlayer.create(shoppingListActivity.this,R.raw.delete);
                deleteSound.start();
                Toast toast = Toast.makeText(shoppingListActivity.this, "item deleted",Toast.LENGTH_SHORT);
                toast.show();
            };
        });
        builder.setNegativeButton("Cancel",null);
        builder.create();
        builder.show();

    }
    //on list view click listener so users can delete a item from the list
    private void setupListViewListener() {
        shoppingList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, final int pos, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(shoppingListActivity.this);
                        builder.setTitle("Delete Item");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton){
                                // Remove the item within array at position
                                items.remove(pos);
                                // Refresh the adapter
                                itemAdapter.notifyDataSetChanged();
                                // Return true consumes the long click event (marks it handled)
                                writeItems();
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

