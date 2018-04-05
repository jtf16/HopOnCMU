package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

import java.io.Serializable;

@Entity(tableName = Monument.TABLE_NAME,
        indices = {@Index(value = {Monument.COLUMN_NAME}, unique = true)})
public class Monument implements Serializable {

    /**
     * The name of the Monument table.
     */
    public static final String TABLE_NAME = "monuments";
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    /**
     * The name of the name column.
     */
    public static final String COLUMN_NAME = "name";
    /**
     * The name of the distance column.
     */
    public static final String COLUMN_LATITUDE = "latitude";
    /**
     * The name of the distance column.
     */
    public static final String COLUMN_LONGITUDE = "longitude";
    private static final long serialVersionUID = -8807331723807741905L;
    /**
     * The unique ID of the monument.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_NAME)
    private String name;

    @ColumnInfo(name = COLUMN_LATITUDE)
    private double latitude;

    @ColumnInfo(name = COLUMN_LONGITUDE)
    private double longitude;

    public Monument() {
    }

    public Monument(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
