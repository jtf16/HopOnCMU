package pt.ulisboa.tecnico.cmov.hoponcmu.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

@Dao
public abstract class TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertQuizzes(Quiz... quizzes);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertQuestions(Question... questions);

    /**
     * Here will be all the queries needed to perform the
     * methods with annotation @Transaction
     */
    @Query("SELECT * FROM " + Monument.TABLE_NAME + " where " +
            Monument.COLUMN_NAME + " LIKE :name LIMIT 1")
    public abstract Monument loadMonumentByName(String name);

    @Query("SELECT * FROM " + Quiz.TABLE_NAME + " where " + Quiz.COLUMN_NAME +
            " LIKE :name AND " + Quiz.COLUMN_MONUMENT_ID + " = :id LIMIT 1")
    public abstract Quiz loadQuizByNameAndMonumentId(String name, long id);

    @Query("SELECT " + Monument.TABLE_NAME + ".* FROM " + Quiz.TABLE_NAME + " INNER JOIN " +
            Monument.TABLE_NAME + " ON " + Quiz.TABLE_NAME + "." + Quiz.COLUMN_MONUMENT_ID + " = " +
            Monument.TABLE_NAME + "." + Monument.COLUMN_ID + " WHERE " + Quiz.TABLE_NAME +
            "." + Quiz.COLUMN_ID + " = :id LIMIT 1")
    public abstract Monument loadMonumentByQuizId(long id);

    @Query("SELECT " + Quiz.TABLE_NAME + ".* FROM " + Quiz.TABLE_NAME + " INNER JOIN " +
            Monument.TABLE_NAME + " ON " + Quiz.TABLE_NAME + "." + Quiz.COLUMN_MONUMENT_ID + " = " +
            Monument.TABLE_NAME + "." + Monument.COLUMN_ID + " WHERE " + Monument.TABLE_NAME +
            "." + Monument.COLUMN_NAME + " LIKE '%' || :name || '%' OR " + Quiz.TABLE_NAME + "." +
            Quiz.COLUMN_NAME + " LIKE '%' || :name || '%'")
    public abstract List<Quiz> loadQuizzesByMonumentName(String name);

    @Query("SELECT " + Quiz.TABLE_NAME + ".* FROM " + Quiz.TABLE_NAME + " INNER JOIN " +
            Monument.TABLE_NAME + " ON " + Quiz.TABLE_NAME + "." + Quiz.COLUMN_MONUMENT_ID + " = " +
            Monument.TABLE_NAME + "." + Monument.COLUMN_ID + " INNER JOIN " +
            UserQuiz.TABLE_NAME + " ON " + Quiz.TABLE_NAME + "." + Quiz.COLUMN_ID + " = " +
            UserQuiz.TABLE_NAME + "." + UserQuiz.COLUMN_QUIZ_ID + " WHERE (" + Monument.TABLE_NAME +
            "." + Monument.COLUMN_NAME + " LIKE '%' || :name || '%' OR " + Quiz.TABLE_NAME + "." +
            Quiz.COLUMN_NAME + " LIKE '%' || :name || '%') AND " + UserQuiz.TABLE_NAME +
            "." + UserQuiz.COLUMN_USERNAME + " LIKE :username")
    public abstract List<Quiz> loadQuizzesByMonumentNameAndUsername(String name, String username);

    @Query("SELECT " + Question.TABLE_NAME + ".* FROM " + Question.TABLE_NAME + " INNER JOIN " +
            Quiz.TABLE_NAME + " ON " + Question.TABLE_NAME + "." + Question.COLUMN_QUIZ_ID + " = " +
            Quiz.TABLE_NAME + "." + Quiz.COLUMN_ID + " WHERE " + Quiz.TABLE_NAME +
            "." + Quiz.COLUMN_ID + " = :id")
    public abstract List<Question> loadQuestionsByQuizId(long id);

    @Transaction
    public void insertQuizAndQuestions(Quiz quiz, Question... questions) {
        // Anything inside this method runs in a single transaction.
        if (quiz != null) {
            long id = insertQuizzes(quiz)[0];
            if (id != -1 && questions != null) {
                for (Question question : questions) {
                    if (question != null) {
                        question.setQuizID(id);
                        insertQuestions(question);
                    }
                }
            }
        }
    }
}
