package com.database.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//This is the table
@Entity(tableName = "UserTable")
public class User {
    //These are the columns of the table
    @PrimaryKey(autoGenerate = true)
    private int id;
    //@ColumnInfo(name = "First Name") for further definition of the column if needed
    private String firstname;
    //@ColumnInfo(name = "Last Name")
    private String lastname;
    //@ColumnInfo(name = "Address")
    private String address;

    //To generate the getter and setter alt+Insert or right click and click generate
    //Same for the constructor

    //Constructor class
    public User(String firstname, String lastname, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
