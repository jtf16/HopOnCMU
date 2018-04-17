package pt.ulisboa.tecnico.cmov.hoponcmu.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

@Dao
public abstract class QuizDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertQuizzes(Quiz... quizzes);

    @Update
    public abstract void updateQuizzes(Quiz... quizzes);

    @Delete
    public abstract void deleteQuizzes(Quiz... quizzes);

    @Query("SELECT COUNT(*) FROM " + Quiz.TABLE_NAME)
    public abstract int countQuizzes();

    @Query("SELECT * FROM " + Quiz.TABLE_NAME + " ORDER BY " + Quiz.COLUMN_ID + " DESC")
    public abstract List<Quiz> loadAllQuizzes();

    @Query("SELECT * FROM " + Quiz.TABLE_NAME + " WHERE " + Quiz.COLUMN_ID + " = :id LIMIT 1")
    public abstract Quiz loadQuizById(long id);
}
