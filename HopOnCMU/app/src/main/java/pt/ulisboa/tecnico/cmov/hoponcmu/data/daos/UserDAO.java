package pt.ulisboa.tecnico.cmov.hoponcmu.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

@Dao
public abstract class UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insertUsers(User... users);

    @Update
    public abstract void updateUsers(User... users);

    @Delete
    public abstract void deleteUsers(User... users);

    @Query("SELECT COUNT(*) FROM " + User.TABLE_NAME)
    public abstract int countUsers();

    @Query("SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME +
            " LIKE '%' || :partUsername || '%' ORDER BY " + User.COLUMN_RANKING + " ASC")
    public abstract List<User> loadAllUsers(String partUsername);
}
