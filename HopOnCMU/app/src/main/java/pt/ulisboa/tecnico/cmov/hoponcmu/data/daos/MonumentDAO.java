package pt.ulisboa.tecnico.cmov.hoponcmu.data.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;

@Dao
public abstract class MonumentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] insertMonuments(Monument... monuments);

    @Update
    public abstract void updateMonuments(Monument... monuments);

    @Delete
    public abstract void deleteMonuments(Monument... monuments);

    @Query("SELECT COUNT(*) FROM " + Monument.TABLE_NAME)
    public abstract int countMonuments();

    @Query("SELECT * FROM " + Monument.TABLE_NAME + " WHERE " + Monument.COLUMN_NAME +
            " LIKE '%' || :partName || '%' ORDER BY " + Monument.COLUMN_LATITUDE + " ASC")
    public abstract List<Monument> loadAllMonuments(String partName);

    @Query("SELECT * FROM " + Monument.TABLE_NAME + " WHERE " + Monument.COLUMN_ID + " = :id")
    public abstract Cursor loadMonumentById(long id);
}
