package com.database.room;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import java.util.List;
//This is the repository
public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> AllUsers;

    public UserRepository(Application application){
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userdao();
        AllUsers = userDao.getAllUsers();
    }
    public void insert(User user){
        new InsertUserAsynTask(userDao).execute(user);
    }
    public void update(User user){
        new UpdateUserAsynTask(userDao).execute(user);
    }

    public void delete(User user){
        new DeleteUserAsynTask(userDao).execute(user);
    }

    public void deleteAllUsers(){
        new DelAlltUserAsynTask(userDao).execute();
    }

    public LiveData<List<User>> getGetAllUsers(){
        return AllUsers;
    }

    private static class InsertUserAsynTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        private InsertUserAsynTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }
    private static class DeleteUserAsynTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        private DeleteUserAsynTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }
    private static class UpdateUserAsynTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;
        private UpdateUserAsynTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }
    private static class DelAlltUserAsynTask extends AsyncTask<Void, Void, Void>{
        private UserDao userDao;
        private DelAlltUserAsynTask(UserDao userDao){
            this.userDao = userDao;
        }
        @Override
        protected Void doInBackground(Void... voidss) {
            userDao.deleteAllUsers();
            return null;
        }
    }
}
