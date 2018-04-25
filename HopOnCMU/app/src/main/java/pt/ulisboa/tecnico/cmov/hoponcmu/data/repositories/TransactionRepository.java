package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class TransactionRepository {

    static AppDatabase appDatabase;
    private Context context;

    public TransactionRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
        this.context = context;
    }

    public void insertUserQuizAndQuestions(String username, Quiz quiz, Question... questions) {
        new InsertUserQuizAndQuestionsTask(username, quiz).execute(questions);
    }

    public void insertMonumentsAndQuizzes(Monument[] monuments, Quiz... quizzes) {
        new InsertMonumentsAndQuizzesTask(monuments).execute(quizzes);
    }

    private static class InsertUserQuizAndQuestionsTask extends AsyncTask<Question, Void, Void> {

        private String username;
        private Quiz quiz;

        InsertUserQuizAndQuestionsTask(String username, Quiz quiz) {
            this.username = username;
            this.quiz = quiz;
        }

        @Override
        protected Void doInBackground(Question... questions) {
            appDatabase.transactionDAO().insertUserQuizAndQuestions(username, quiz, questions);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class InsertMonumentsAndQuizzesTask extends AsyncTask<Quiz, Void, Void> {

        private Monument[] monuments;

        InsertMonumentsAndQuizzesTask(Monument[] monuments) {
            this.monuments = monuments;
        }

        @Override
        protected Void doInBackground(Quiz... quizzes) {
            appDatabase.transactionDAO().insertMonumentsAndQuizzes(monuments, quizzes);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
