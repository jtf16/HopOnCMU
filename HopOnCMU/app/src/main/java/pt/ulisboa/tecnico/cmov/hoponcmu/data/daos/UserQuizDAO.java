package pt.ulisboa.tecnico.cmov.hoponcmu.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

@Dao
public abstract class UserQuizDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertUserQuizzes(UserQuiz... userQuizzes);

    @Update
    public abstract void updateUserQuizzes(UserQuiz... userQuizzes);

    @Delete
    public abstract void deleteUserQuizzes(UserQuiz... userQuizzes);

    @Query("SELECT COUNT(*) FROM " + UserQuiz.TABLE_NAME)
    public abstract int countUserQuizzes();

    @Query("SELECT * FROM " + UserQuiz.TABLE_NAME + " ORDER BY " + UserQuiz.COLUMN_ID + " DESC")
    public abstract List<UserQuiz> loadAllUserQuizzes();

    @Query("SELECT * FROM " + UserQuiz.TABLE_NAME + " WHERE " + UserQuiz.COLUMN_ID + " = :id LIMIT 1")
    public abstract UserQuiz loadUserQuizById(long id);

    @Query("SELECT * FROM " + UserQuiz.TABLE_NAME + " WHERE " + UserQuiz.COLUMN_QUIZ_ID + " = :id" +
            " AND " + UserQuiz.COLUMN_USERNAME + " LIKE :username LIMIT 1")
    public abstract UserQuiz loadUserQuizByIdAndUsername(long id, String username);
}
