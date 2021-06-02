package com.database.room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddUpdateUser extends AppCompatActivity {

    public static final String EXTRA_ID = "com.database.room.EXTRA_ID";
    public static final String EXTRA_FNAME = "com.database.room.EXTRA_FNAME";
    public static final String EXTRA_LNAME = "com.database.room.EXTRA_LNAME";
    public static final String EXTRA_ADDRESS = "com.database.room.EXTRA_ADDRESS";

    EditText addfname, addlname, addadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        EditText addfname = findViewById(R.id.addfname);
        EditText addlname = findViewById(R.id.addlname);
        EditText addadd = findViewById(R.id.addadd);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher_background);
        //This effectively prepares the AddUpdate activity for the editing operation.
        //The intent is the same as the intent used in the MainActivity to trigger the
        //transfer of data from the MainActivity to the update activity
        //If you have an integer or numerical value then within the .getStringExtra(EXTRA_XXX, 1);
        //you will need to specify the default value to avoid a null value
        Intent update = getIntent();
        if (update.hasExtra(EXTRA_ID)){
            setTitle("Edit User");
            addfname.setText(update.getStringExtra(EXTRA_FNAME));
            addlname.setText(update.getStringExtra(EXTRA_LNAME));
            addadd.setText(update.getStringExtra(EXTRA_ADDRESS));
        }else{
            setTitle("Add User");
        }

    }
    private void saveUser() {
        EditText addfname = findViewById(R.id.addfname);
        EditText addlname = findViewById(R.id.addlname);
        EditText addadd = findViewById(R.id.addadd);

        String fname = addfname.getText().toString();
        String lname = addlname.getText().toString();
        String addr = addadd.getText().toString();
        if (fname.trim().isEmpty() || lname.trim().isEmpty()|| addr.trim().isEmpty()) {
            Toast.makeText(this, "Please insert User Information", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_FNAME, fname);
        data.putExtra(EXTRA_LNAME, lname);
        data.putExtra(EXTRA_ADDRESS, addr);
        //This allows you to use the saveUser function to update the entry however the ID value
        //won't trigger when you add a new user, only when you update and pass data with the Intent.
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){data.putExtra(EXTRA_ID, id);}
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addusermenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveuser) {
            saveUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}