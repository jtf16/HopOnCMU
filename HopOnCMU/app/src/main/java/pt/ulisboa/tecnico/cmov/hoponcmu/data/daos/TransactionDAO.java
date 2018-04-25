package pt.ulisboa.tecnico.cmov.hoponcmu.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

@Dao
public abstract class TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertMonuments(Monument... monuments);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertQuizzes(Quiz... quizzes);

    @Update
    public abstract void updateQuizzes(Quiz... quizzes);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertQuestions(Question... questions);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertUserQuizzes(UserQuiz... userQuizzes);

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

    @Query("SELECT " + UserQuiz.TABLE_NAME + "." + UserQuiz.COLUMN_QUIZ_ID + " FROM " + UserQuiz.TABLE_NAME + " INNER JOIN " +
            Quiz.TABLE_NAME + " ON " + UserQuiz.TABLE_NAME + "." + UserQuiz.COLUMN_QUIZ_ID + " = " +
            Quiz.TABLE_NAME + "." + Quiz.COLUMN_ID + " WHERE " + Quiz.TABLE_NAME +
            "." + Quiz.COLUMN_MONUMENT_ID + " LIKE :id AND " + UserQuiz.TABLE_NAME + "." +
            UserQuiz.COLUMN_USERNAME + " LIKE :username")
    public abstract List<Long> loadUserQuizzesByMonumentIdAndUser(String id, String username);

    @Transaction
    public void insertUserQuizAndQuestions(String username, Quiz quiz, Question... questions) {
        // Anything inside this method runs in a single transaction.
        if (quiz != null) {
            if (questions != null) {
                int numQuestions = 0;
                for (Question question : questions) {
                    if (question != null) {
                        question.setQuizID(quiz.getId());
                        insertQuestions(question);
                        numQuestions++;
                    }
                }
                quiz.setNumQuestions(numQuestions);
                updateQuizzes(quiz);
                insertUserQuizzes(new UserQuiz(username, quiz.getId()));
            }
        }
    }

    @Transaction
    public void insertMonumentsAndQuizzes(Monument[] monuments, Quiz... quizzes) {
        // Anything inside this method runs in a single transaction.
        if (monuments != null) {
            insertMonuments(monuments);
            if (quizzes != null) {
                insertQuizzes(quizzes);
            }
        }
    }
}
