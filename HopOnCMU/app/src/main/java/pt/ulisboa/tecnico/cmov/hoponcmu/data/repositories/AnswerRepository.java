package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Answer;

public class AnswerRepository {

    static AppDatabase appDatabase;

    public AnswerRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void insertAnswer(final Answer... answers) {
        new GetAnswerInsertTask().execute(answers);
    }

    public void deleteAnswer(final Answer... answers) {
        new GetAnswerDeleteTask().execute(answers);
    }

    public void updateAnswer(final Answer... answers) {
        new GetAnswerUpdateTask().execute(answers);
    }

    private static class GetAnswerInsertTask extends AsyncTask<Answer, Void, Void> {

        @Override
        protected Void doInBackground(Answer... answers) {
            appDatabase.answerDAO().insertAnswers(answers);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetAnswerDeleteTask extends AsyncTask<Answer, Void, Void> {

        @Override
        protected Void doInBackground(Answer... answers) {
            appDatabase.answerDAO().deleteAnswers(answers);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetAnswerUpdateTask extends AsyncTask<Answer, Void, Void> {

        @Override
        protected Void doInBackground(Answer... answers) {
            appDatabase.answerDAO().updateAnswers(answers);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
