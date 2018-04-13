package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.provider.BaseColumns;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = Question.TABLE_NAME,
        indices = {@Index(value = {
                Question.COLUMN_QUIZ_ID, Question.COLUMN_QUESTION}, unique = true)},
        foreignKeys = {
                @ForeignKey(onDelete = CASCADE,
                        entity = Quiz.class, parentColumns = Quiz.COLUMN_ID,
                        childColumns = Question.COLUMN_QUIZ_ID)})
public class Question implements Serializable {

    /**
     * The name of the Question table.
     */
    public static final String TABLE_NAME = "questions";
    /**
     * The name of the ID column.
     */
    public static final String COLUMN_ID = BaseColumns._ID;
    /**
     * The name of the quiz id column.
     */
    public static final String COLUMN_QUIZ_ID = "quiz_id";
    /**
     * The name of the question column.
     */
    public static final String COLUMN_QUESTION = "question";
    /**
     * The name of the option a column.
     */
    public static final String COLUMN_OPTION_A = "option_a";
    /**
     * The name of the option b column.
     */
    public static final String COLUMN_OPTION_B = "option_b";
    /**
     * The name of the option c column.
     */
    public static final String COLUMN_OPTION_C = "option_c";
    /**
     * The name of the option d column.
     */
    public static final String COLUMN_OPTION_D = "option_d";
    /**
     * The name of the answer column.
     */
    public static final String COLUMN_ANSWER = "answer";
    private static final long serialVersionUID = -8807331723807741905L;
    /**
     * The unique ID of the question.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    @ColumnInfo(name = COLUMN_QUIZ_ID)
    public long quizID;

    @ColumnInfo(name = COLUMN_QUESTION)
    private String question;

    @ColumnInfo(name = COLUMN_OPTION_A)
    private String optionA;

    @ColumnInfo(name = COLUMN_OPTION_B)
    private String optionB;

    @ColumnInfo(name = COLUMN_OPTION_C)
    private String optionC;

    @ColumnInfo(name = COLUMN_OPTION_D)
    private String optionD;

    @ColumnInfo(name = COLUMN_ANSWER)
    private AnswerOption answer;

    public Question() {
    }

    public Question(String question, String optionA, String optionB, String optionC, String optionD) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public Question(String question, String optionA, String optionB, String optionC, String optionD, AnswerOption answer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuizID() {
        return quizID;
    }

    public void setQuizID(long quizID) {
        this.quizID = quizID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public AnswerOption getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerOption answer) {
        this.answer = answer;
    }
}
