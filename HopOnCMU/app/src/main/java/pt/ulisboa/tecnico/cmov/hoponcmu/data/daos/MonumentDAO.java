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

    @Query("SELECT * FROM " + Monument.TABLE_NAME +
            " WHERE " + Monument.COLUMN_NAME + " LIKE '%' || :partName || " +
            "'%' ORDER BY " + Monument.COLUMN_NAME + " ASC")
    public abstract List<Monument> loadMonumentsOrderByName(String partName);

    @Query("SELECT " + Monument.COLUMN_ID + ", " + Monument.COLUMN_NAME + ", " +
            Monument.COLUMN_LATITUDE + ", " + Monument.COLUMN_LONGITUDE + ", " +
            Monument.COLUMN_SIN_LATITUDE + ", " + Monument.COLUMN_SIN_LONGITUDE + ", " +
            Monument.COLUMN_COS_LATITUDE + ", " + Monument.COLUMN_COS_LONGITUDE + ", " +
            "(:sinlat*" + Monument.COLUMN_SIN_LATITUDE + "+:coslat*" +
            Monument.COLUMN_COS_LATITUDE + "*(:coslon*" + Monument.COLUMN_COS_LONGITUDE +
            "+:sinlon*" + Monument.COLUMN_SIN_LONGITUDE + ")) AS " +
            Monument.COLUMN_COS_DISTANCE + " FROM " + Monument.TABLE_NAME +
            " WHERE " + Monument.COLUMN_NAME + " LIKE '%' || :partName || " +
            "'%' ORDER BY " + Monument.COLUMN_COS_DISTANCE + " DESC")
    public abstract List<Monument> loadMonumentsOrderByDistance(
            String partName, double sinlat, double coslat, double sinlon, double coslon);

    @Query("SELECT * FROM " + Monument.TABLE_NAME + " WHERE " + Monument.COLUMN_ID + " = :id")
    public abstract Cursor loadMonumentById(long id);

}
