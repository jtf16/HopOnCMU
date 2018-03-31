package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class UserRepository {

    static AppDatabase appDatabase;

    public UserRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void insertUser(final User... users) {
        new GetUserInsertTask().execute(users);
    }

    public void deleteUser(final User... users) {
        new GetUserDeleteTask().execute(users);
    }

    public void updateUser(final User... users) {
        new GetUserUpdateTask().execute(users);
    }

    private static class GetUserInsertTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDAO().insertUsers(users);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetUserDeleteTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDAO().deleteUsers(users);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetUserUpdateTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            appDatabase.userDAO().updateUsers(users);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
