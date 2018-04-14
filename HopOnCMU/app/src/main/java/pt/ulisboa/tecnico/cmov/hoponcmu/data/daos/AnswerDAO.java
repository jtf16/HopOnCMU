package pt.ulisboa.tecnico.cmov.hoponcmu.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Answer;

@Dao
public abstract class AnswerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insertAnswers(Answer... answers);

    @Update
    public abstract void updateAnswers(Answer... answers);

    @Delete
    public abstract void deleteAnswers(Answer... answers);

    @Query("SELECT COUNT(*) FROM " + Answer.TABLE_NAME)
    public abstract int countAnswers();

    @Query("SELECT * FROM " + Answer.TABLE_NAME + " ORDER BY " + Answer.COLUMN_ID + " DESC")
    public abstract List<Answer> loadAllAnswers();

    @Query("SELECT * FROM " + Answer.TABLE_NAME + " WHERE " + Answer.COLUMN_USERNAME +
            " LIKE :username AND " + Answer.COLUMN_QUIZ_ID + " = :quizID ORDER BY " +
            Answer.COLUMN_QUESTION_ID + " DESC")
    public abstract List<Answer> loadAnswersByUserAndQuiz(String username, long quizID);

    @Query("SELECT * FROM " + Answer.TABLE_NAME + " WHERE " + Answer.COLUMN_ID + " = :id")
    public abstract Answer loadAnswerById(long id);
}
