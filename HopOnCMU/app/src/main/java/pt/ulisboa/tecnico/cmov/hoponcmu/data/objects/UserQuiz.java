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

@Entity(tableName = UserQuiz.TABLE_NAME,
        indices = {@Index(value = {
                UserQuiz.COLUMN_QUIZ_ID, UserQuiz.COLUMN_USERNAME}, unique = true)},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = User.COLUMN_USERNAME,
                        childColumns = UserQuiz.COLUMN_USERNAME),
                @ForeignKey(onDelete = CASCADE,
                        entity = Quiz.class, parentColumns = Quiz.COLUMN_ID,
                        childColumns = UserQuiz.COLUMN_QUIZ_ID)})
public class UserQuiz implements Serializable {

    /**
     * The name of the Question table.
     */
    public static final String TABLE_NAME = "user_quizzes";
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    /**
     * The name of the username column.
     */
    public static final String COLUMN_USERNAME = "username";
    /**
     * The name of the quiz id column.
     */
    public static final String COLUMN_QUIZ_ID = "quiz_id";
    /**
     * The name of the open time column.
     */
    public static final String COLUMN_OPEN_TIME = "open_time";
    /**
     * The name of the submit time column.
     */
    public static final String COLUMN_SUBMIT_TIME = "submit_time";
    /**
     * The name of the score column.
     */
    public static final String COLUMN_SCORE = "score";
    private static final long serialVersionUID = -8807331723807741905L;
    /**
     * The unique ID of the question.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_QUIZ_ID)
    public long quizID;

    @ColumnInfo(name = COLUMN_USERNAME)
    private String username;

    @ColumnInfo(name = COLUMN_OPEN_TIME)
    private Date openTime;

    @ColumnInfo(name = COLUMN_SUBMIT_TIME)
    private Date submitTime;

    @ColumnInfo(name = COLUMN_SCORE)
    private int score = -1;

    public UserQuiz() {
    }

    public UserQuiz(String username, long quizID) {
        this.username = username;
        this.quizID = quizID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getQuizID() {
        return quizID;
    }

    public void setQuizID(long quizID) {
        this.quizID = quizID;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
