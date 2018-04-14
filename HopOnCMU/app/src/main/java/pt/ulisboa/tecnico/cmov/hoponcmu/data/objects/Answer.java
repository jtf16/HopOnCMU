package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = Answer.TABLE_NAME,
        indices = {@Index(value = {Answer.COLUMN_USERNAME,
                Answer.COLUMN_QUESTION_ID}, unique = true)},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = User.COLUMN_USERNAME,
                        childColumns = Answer.COLUMN_USERNAME),
                @ForeignKey(onDelete = CASCADE,
                        entity = Quiz.class, parentColumns = Quiz.COLUMN_ID,
                        childColumns = Answer.COLUMN_QUIZ_ID),
                @ForeignKey(onDelete = CASCADE,
                        entity = Question.class, parentColumns = Question.COLUMN_ID,
                        childColumns = Answer.COLUMN_QUESTION_ID)})
public class Answer implements Serializable {

    /**
     * The name of the Answers table.
     */
    public static final String TABLE_NAME = "answers";
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
     * The name of the question id column.
     */
    public static final String COLUMN_QUESTION_ID = "question_id";
    /**
     * The name of the answer column.
     */
    public static final String COLUMN_ANSWER = "answer";
    private static final long serialVersionUID = -8807331723807741905L;
    /**
     * The unique ID of the answer.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_USERNAME)
    private String username;

    @ColumnInfo(name = COLUMN_QUIZ_ID)
    private long quizID;

    @ColumnInfo(name = COLUMN_QUESTION_ID)
    private long questionID;

    @ColumnInfo(name = COLUMN_ANSWER)
    private AnswerOption answer;

    public Answer() {
    }

    public Answer(String username, long quizID, long questionID) {
        this.username = username;
        this.quizID = quizID;
        this.questionID = questionID;
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

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public AnswerOption getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerOption answer) {
        this.answer = answer;
    }
}
