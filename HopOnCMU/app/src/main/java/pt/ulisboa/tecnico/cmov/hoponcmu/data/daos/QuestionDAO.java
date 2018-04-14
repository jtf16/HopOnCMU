package pt.ulisboa.tecnico.cmov.hoponcmu.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;

@Dao
public abstract class QuestionDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertQuestions(Question... questions);

    @Update
    public abstract void updateQuestions(Question... questions);

    @Delete
    public abstract void deleteQuestions(Question... questions);

    @Query("SELECT COUNT(*) FROM " + Question.TABLE_NAME)
    public abstract int countQuestions();

    @Query("SELECT * FROM " + Question.TABLE_NAME + " ORDER BY " + Question.COLUMN_ID + " DESC")
    public abstract List<Question> loadAllQuestions();

    @Query("SELECT * FROM " + Question.TABLE_NAME + " WHERE " + Question.COLUMN_ID + " = :id")
    public abstract Question loadQuestionById(long id);
}
