package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

public class TransactionRepository {

    static AppDatabase appDatabase;
    private Context context;

    public TransactionRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
        this.context = context;
    }

    public void insertQuizUserQuizAndQuestions(String username, Quiz quiz, Question... questions) {
        new InsertQuizUserQuizAndQuestionsTask(context, username, quiz).execute(questions);
    }

    private static class InsertQuizUserQuizAndQuestionsTask extends AsyncTask<Question, Void, Void> {

        private Context context;
        private String username;
        private Quiz quiz;

        InsertQuizUserQuizAndQuestionsTask(Context context, String username, Quiz quiz) {
            this.context = context;
            this.username = username;
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
            UserQuizRepository userQuizRepository = new UserQuizRepository(context);
            userQuizRepository.insertUserQuiz(new UserQuiz(username, quiz.getId()));
        }
    }
}
