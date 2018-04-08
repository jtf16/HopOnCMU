package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class TransactionRepository {

    static AppDatabase appDatabase;

    public TransactionRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void insertQuizAndQuestions(Quiz quiz, Question... questions) {
        new InsertQuizAndQuestionsTask(quiz).execute(questions);
    }

    private static class InsertQuizAndQuestionsTask extends AsyncTask<Question, Void, Void> {

        private Quiz quiz;

        InsertQuizAndQuestionsTask(Quiz quiz) {
            this.quiz = quiz;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            appDatabase.transactionDAO().insertQuizAndQuestions(quiz, questions);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
