package com.database.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);

    @Query("DELETE FROM UserTable")
    void deleteAllUsers();

    @Query("SELECT * FROM UserTable")
    LiveData<List<User>> getAllUsers();
    //As a LiveData object the database automatically updates on changes.
}
