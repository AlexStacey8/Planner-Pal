package com.example.staceya4.myapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class profileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Dbhandler database = new Dbhandler(this);
        //getting all the text views I need in order to display the user's information
        TextView textView = (TextView)findViewById(R.id.profileUsername);
        TextView textViewEmail = (TextView)findViewById(R.id.profileEmail);
        TextView textViewForname = (TextView)findViewById(R.id.profileForename);
        TextView textViewSurname = (TextView)findViewById(R.id.profileSurname);
        //getting the user's username from extras
        String username = getIntent().getStringExtra("USERNAME2");
        textView.setText(username);
        //using get function from my database handler class to get the user's details and setting the textviews
        String userEmail = database.getEmail(username);
        textViewEmail.setText(userEmail);
        String userForename = database.getForename(username);
        textViewForname.setText(userForename);
        String userSurname = database.getSurname(username);
        textViewSurname.setText(userSurname);
        //getting user id from database
        int userID = database.getUserID(username);
        ImageView profilePicture = (ImageView)findViewById(R.id.profileImage);
        Bitmap profileImg = database.getProfilePicture(userID);
        if (profileImg == null){
            profilePicture.setImageResource(R.drawable.applogo);
        }else{
            profilePicture.setImageBitmap(profileImg);
        }
    }
    //function that bring up an alert dialog so the user can enter a new email address
    //within this function there is the change email database function
    public void changeEmail(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(profileActivity.this);
        builder.setTitle("Enter New Email");
        final EditText emailEditText = new EditText(profileActivity.this);
        emailEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEditText.setHint("example@example.com");
        builder.setView(emailEditText);
        builder.setPositiveButton("Change Email", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                String email = String.valueOf(emailEditText.getText());
                String username = getIntent().getStringExtra("USERNAME2");
                Dbhandler database = new Dbhandler(profileActivity.this);
                database.changeEmail(email,username);
                TextView textViewEmail = (TextView)findViewById(R.id.profileEmail);
                String userEmail = database.getEmail(username);
                textViewEmail.setText(userEmail);

                Toast toast = Toast.makeText(profileActivity.this, "Sucessfully Changed email",Toast.LENGTH_LONG);
                toast.show();
            };
        });

        builder.setNegativeButton("Cancel",null);
        builder.create();


        builder.show();
    }
    //function that bring up an alert dialog so the user can enter a new username
    //within this function there is the change username database function
    public void changeUsername(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(profileActivity.this);
        builder.setTitle("Enter New Username");
        final EditText usernameEditText = new EditText(profileActivity.this);
        usernameEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        usernameEditText.setHint("New Username");
        builder.setView(usernameEditText);
        builder.setPositiveButton("Change Username", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                String newUsername = String.valueOf(usernameEditText.getText());
                String oldUsername = getIntent().getStringExtra("USERNAME2");
                Dbhandler database = new Dbhandler(profileActivity.this);
                database.changeUsername(newUsername,oldUsername);
                TextView textViewUsername = (TextView)findViewById(R.id.profileUsername);
                //seting textview on profile page to new username
                textViewUsername.setText(newUsername);
                Toast toast = Toast.makeText(profileActivity.this, "Successfully Changed Username",Toast.LENGTH_LONG);
                toast.show();
            };
        });

        builder.setNegativeButton("Cancel",null);
        builder.create();


        builder.show();
    }
    //function that bring up an alert dialog so the user can enter a new password
    //within this function there is the change password database function
    public void changePassword(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(profileActivity.this);
        builder.setTitle("Enter New Password");
        final EditText passwordEditText = new EditText(profileActivity.this);
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEditText.setHint("Password");
        builder.setView(passwordEditText);
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String password = String.valueOf(passwordEditText.getText());
                String username = getIntent().getStringExtra("USERNAME2");
                Dbhandler database = new Dbhandler(profileActivity.this);
                database.changePassword(password, username);
                Toast toast = Toast.makeText(profileActivity.this, "Successfully Changed Password", Toast.LENGTH_LONG);
                toast.show();
            };
        });

        builder.setNegativeButton("Cancel", null);
        builder.create();


        builder.show();
    }
    //The following code is for taking and storing a new profile picture
    //this onclick function run when the profile picture is pressed by the user
    public void changeImage(View view){
        //when pressed the user can take a picture which will then act as their profile pic
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(getPackageManager()) != null){
            startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    //after the user confirms the image they have taken this function is run to store the image into the database
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //get image view by id
        ImageView profilePic = (ImageView)findViewById(R.id.profileImage);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePic.setImageBitmap(imageBitmap);
            Dbhandler db = new Dbhandler(profileActivity.this);
            String username = getIntent().getStringExtra("USERNAME2");
            int userID = db.getUserID(username);
            db.storeProfilePicture(userID,imageBitmap);
        }

    }
}
