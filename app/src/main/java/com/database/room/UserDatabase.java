package com.database.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;
    public abstract UserDao userdao();
    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,
                    "UserTable").
                    fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class populatedatabseAsyncTask extends AsyncTask<Void, Void, Void>{
        private UserDao userDao;
        private populatedatabseAsyncTask(UserDatabase db){
            userDao = db.userdao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            userDao.insert(new User("Ralph","Lundgreen","Poland"));
            userDao.insert(new User("Ralph","Lundgreen","Poland"));
            userDao.insert(new User("Ralph","Lundgreen","Poland"));
            return null;
        }
    }
}
