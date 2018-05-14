package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import java.io.Serializable;

public class Quiz implements Serializable {

    private static final long serialVersionUID = -8807331723807741905L;

    public long id;
    public String monumentID;
    private String name;
    private int numQuestions = 0;

    public Quiz() {
    }

    public Quiz(String monumentID, String name) {
        this.monumentID = monumentID;
        this.name = name;
    }

    public Quiz(String monumentID, String name, int numQuestions) {
        this.monumentID = monumentID;
        this.name = name;
        this.numQuestions = numQuestions;
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

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }
}
