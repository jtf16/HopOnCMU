package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

import java.io.Serializable;

@Entity(tableName = User.TABLE_NAME,
        indices = {@Index(value = {User.COLUMN_USERNAME}, unique = true)})
public class User implements Serializable {

    /**
     * The name of the User table.
     */
    public static final String TABLE_NAME = "users";
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    /**
     * The name of the username column.
     */
    public static final String COLUMN_USERNAME = "username";
    /**
     * The name of the first name column.
     */
    public static final String COLUMN_FIRST_NAME = "first_name";
    /**
     * The name of the last name column.
     */
    public static final String COLUMN_LAST_NAME = "last_name";
    /**
     * The name of the email column.
     */
    public static final String COLUMN_EMAIL = "email";
    /**
     * The name of the password column.
     */
    public static final String COLUMN_PASSWORD = "password";
    /**
     * The name of the ranking column.
     */
    public static final String COLUMN_RANKING = "ranking";
    /**
     * The name of the score column.
     */
    public static final String COLUMN_SCORE = "score";
    /**
     * The name of the time column.
     */
    public static final String COLUMN_TIME = "time";
    private static final long serialVersionUID = -8807331723807741905L;
    /**
     * The unique ID of the user.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_USERNAME)
    private String username;

    @ColumnInfo(name = COLUMN_FIRST_NAME)
    private String firstName;

    @ColumnInfo(name = COLUMN_LAST_NAME)
    private String lastName;

    @ColumnInfo(name = COLUMN_EMAIL)
    private String email;

    @ColumnInfo(name = COLUMN_PASSWORD)
    private String password;

    @ColumnInfo(name = COLUMN_RANKING)
    private int ranking;

    @ColumnInfo(name = COLUMN_SCORE)
    private int score;

    @ColumnInfo(name = COLUMN_TIME)
    private long time;

    public User() {
    }

    public User(String username, String firstName, String lastName, String email, String password, int score, long time) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.score = score;
        this.time = time;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
