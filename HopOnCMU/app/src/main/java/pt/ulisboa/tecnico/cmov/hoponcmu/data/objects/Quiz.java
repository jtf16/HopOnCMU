package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

import java.io.Serializable;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = Quiz.TABLE_NAME,
        indices = {@Index(value = {Quiz.COLUMN_NAME}, unique = true)},
        foreignKeys = {
                @ForeignKey(onDelete = CASCADE,
                        entity = Monument.class, parentColumns = Monument.COLUMN_ID,
                        childColumns = Quiz.COLUMN_MONUMENT_ID)})
public class Quiz implements Serializable {

    /**
     * The name of the Quiz table.
     */
    public static final String TABLE_NAME = "quizzes";
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    /**
     * The name of the monument id column.
     */
    public static final String COLUMN_MONUMENT_ID = "monument_id";
    /**
     * The name of the name column.
     */
    public static final String COLUMN_NAME = "name";
    /**
     * The name of the open time column.
     */
    public static final String COLUMN_OPEN_TIME = "open_time";
    /**
     * The name of the submit time column.
     */
    public static final String COLUMN_SUBMIT_TIME = "submit_time";
    private static final long serialVersionUID = -8807331723807741905L;
    /**
     * The unique ID of the user.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_MONUMENT_ID)
    public String monumentID;

    @ColumnInfo(name = COLUMN_NAME)
    private String name;

    @ColumnInfo(name = COLUMN_OPEN_TIME)
    private Date openTime;

    @ColumnInfo(name = COLUMN_SUBMIT_TIME)
    private Date submitTime;

    public Quiz() {
    }

    public Quiz(String monumentID, String name) {
        this.monumentID = monumentID;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonumentID() {
        return monumentID;
    }

    public void setMonumentID(String monumentID) {
        this.monumentID = monumentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}
