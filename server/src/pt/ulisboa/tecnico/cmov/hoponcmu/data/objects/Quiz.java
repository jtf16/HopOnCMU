package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import java.io.Serializable;
import java.util.Date;

public class Quiz implements Serializable {

    private static final long serialVersionUID = -8807331723807741905L;

    public long id;
    public String monumentID;
    private String name;
    private Date openTime;
    private Date submitTime;
    private int score = -1;

    public Quiz() {
    }

    public Quiz(String monumentID, String name) {
        this.monumentID = monumentID;
        this.name = name;
    }

    public Quiz(long id, String monumentID, String name) {
        this.id = id;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
