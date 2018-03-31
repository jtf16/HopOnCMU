package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class QuizRepository {

    static AppDatabase appDatabase;

    public QuizRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void insertQuiz(final Quiz... quizzes) {
        new GetQuizInsertTask().execute(quizzes);
    }

    public void deleteQuiz(final Quiz... quizzes) {
        new GetQuizDeleteTask().execute(quizzes);
    }

    public void updateQuiz(final Quiz... quizzes) {
        new GetQuizUpdateTask().execute(quizzes);
    }

    private static class GetQuizInsertTask extends AsyncTask<Quiz, Void, Void> {

        @Override
        protected Void doInBackground(Quiz... quizzes) {
            appDatabase.quizDAO().insertQuizzes(quizzes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetQuizDeleteTask extends AsyncTask<Quiz, Void, Void> {

        @Override
        protected Void doInBackground(Quiz... quizzes) {
            appDatabase.quizDAO().deleteQuizzes(quizzes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetQuizUpdateTask extends AsyncTask<Quiz, Void, Void> {

        @Override
        protected Void doInBackground(Quiz... quizzes) {
            appDatabase.quizDAO().updateQuizzes(quizzes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
