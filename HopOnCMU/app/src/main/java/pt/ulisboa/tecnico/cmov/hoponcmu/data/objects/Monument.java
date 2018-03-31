package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

@Entity(tableName = Monument.TABLE_NAME,
        indices = {@Index(value = {Monument.COLUMN_NAME}, unique = true)})
public class Monument {

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
    public static final String COLUMN_DISTANCE = "distance";

    /**
     * The unique ID of the monument.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_NAME)
    private String name;

    @ColumnInfo(name = COLUMN_DISTANCE)
    private int distance;

    public Monument() {
    }

    public Monument(String name, int distance) {
        this.name = name;
        this.distance = distance;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
