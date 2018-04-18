package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import java.io.Serializable;
import java.util.Date;

public class UserQuiz implements Serializable {

    public long id;
    private String username;
    public long quizID;
    private Date openTime;
    private Date submitTime;
    private int score = -1;
    private static final long serialVersionUID = -8807331723807741905L;

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
