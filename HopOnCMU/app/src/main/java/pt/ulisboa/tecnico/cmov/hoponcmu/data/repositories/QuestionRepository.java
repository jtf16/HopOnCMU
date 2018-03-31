package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;

public class QuestionRepository {

    static AppDatabase appDatabase;

    public QuestionRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void insertQuestion(final Question... questions) {
        new GetQuestionInsertTask().execute(questions);
    }

    public void deleteQuestion(final Question... questions) {
        new GetQuestionDeleteTask().execute(questions);
    }

    public void updateQuestion(final Question... questions) {
        new GetQuestionUpdateTask().execute(questions);
    }

    private static class GetQuestionInsertTask extends AsyncTask<Question, Void, Void> {

        @Override
        protected Void doInBackground(Question... questions) {
            appDatabase.questionDAO().insertQuestions(questions);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetQuestionDeleteTask extends AsyncTask<Question, Void, Void> {

        @Override
        protected Void doInBackground(Question... questions) {
            appDatabase.questionDAO().deleteQuestions(questions);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetQuestionUpdateTask extends AsyncTask<Question, Void, Void> {

        @Override
        protected Void doInBackground(Question... questions) {
            appDatabase.questionDAO().updateQuestions(questions);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
