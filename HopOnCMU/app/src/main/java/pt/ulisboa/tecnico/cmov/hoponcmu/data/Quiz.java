package pt.ulisboa.tecnico.cmov.hoponcmu.data;

import java.io.Serializable;

public class Quiz implements Serializable {

    private String name;
    private int numberOfQuestions;

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
