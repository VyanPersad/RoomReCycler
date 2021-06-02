package com.database.room;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Ensure that these 2 values are different.
    public static final int USER_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final UserAdapter adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        Button addUser = findViewById(R.id.addUser);
        addUser.setOnClickListener(view -> {
            startActivityForResult(new Intent(MainActivity.this, AddUpdateUser.class), USER_REQUEST);
        });
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> {
            startActivityForResult(new Intent(MainActivity.this, AddUpdateUser.class), USER_REQUEST);
        });

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, users -> adapter.submitList(users));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                userViewModel.delete(adapter.getUserPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "User Deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(user -> {
            Intent update = new Intent(MainActivity.this, AddUpdateUser.class);
            update.putExtra(AddUpdateUser.EXTRA_ID, user.getId());
            update.putExtra(AddUpdateUser.EXTRA_FNAME, user.getFirstname());
            update.putExtra(AddUpdateUser.EXTRA_LNAME, user.getLastname());
            update.putExtra(AddUpdateUser.EXTRA_ADDRESS, user.getAddress());
            startActivityForResult(update, EDIT_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_REQUEST && resultCode == RESULT_OK) {
            String fname = data.getStringExtra(AddUpdateUser.EXTRA_FNAME);
            String lname = data.getStringExtra(AddUpdateUser.EXTRA_LNAME);
            String addr = data.getStringExtra(AddUpdateUser.EXTRA_ADDRESS);

            User user = new User(fname, lname, addr);
            userViewModel.insert(user);
            Toast.makeText(this, "User saved", Toast.LENGTH_LONG).show();
        }
        else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddUpdateUser.EXTRA_ID,-1);
            if(id == -1){Toast.makeText(this, "User not Updated", Toast.LENGTH_LONG).show();return;}
            String fname = data.getStringExtra(AddUpdateUser.EXTRA_FNAME);
            String lname = data.getStringExtra(AddUpdateUser.EXTRA_LNAME);
            String addr = data.getStringExtra(AddUpdateUser.EXTRA_ADDRESS);

            User user = new User(fname, lname, addr);
            user.setId(id);
            userViewModel.update(user);
            Toast.makeText(this, "User Updated", Toast.LENGTH_LONG).show();
        }
        else {Toast.makeText(this, "User not saved", Toast.LENGTH_LONG).show();}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenumenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAllentries) {
            userViewModel.deleteAllUsers();
            Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}