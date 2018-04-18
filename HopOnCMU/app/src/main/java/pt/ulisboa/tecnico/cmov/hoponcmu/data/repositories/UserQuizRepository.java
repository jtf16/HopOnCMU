package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

public class UserQuizRepository {

    static AppDatabase appDatabase;

    public UserQuizRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void insertUserQuiz(final UserQuiz... userQuizzes) {
        new GetUserQuizInsertTask().execute(userQuizzes);
    }

    public void deleteUserQuiz(final UserQuiz... userQuizzes) {
        new GetUserQuizDeleteTask().execute(userQuizzes);
    }

    public void updateUserQuiz(final UserQuiz... userQuizzes) {
        new GetUserQuizUpdateTask().execute(userQuizzes);
    }

    private static class GetUserQuizInsertTask extends AsyncTask<UserQuiz, Void, Void> {

        @Override
        protected Void doInBackground(UserQuiz... userQuizzes) {
            appDatabase.userQuizDAO().insertUserQuizzes(userQuizzes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetUserQuizDeleteTask extends AsyncTask<UserQuiz, Void, Void> {

        @Override
        protected Void doInBackground(UserQuiz... userQuizzes) {
            appDatabase.userQuizDAO().deleteUserQuizzes(userQuizzes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetUserQuizUpdateTask extends AsyncTask<UserQuiz, Void, Void> {

        @Override
        protected Void doInBackground(UserQuiz... userQuizzes) {
            appDatabase.userQuizDAO().updateUserQuizzes(userQuizzes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
