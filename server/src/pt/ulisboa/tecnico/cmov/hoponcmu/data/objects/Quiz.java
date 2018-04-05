package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import java.io.Serializable;

public class Quiz implements Serializable {

    private static final long serialVersionUID = -8807331723807741905L;

    public long id;
    public long monumentID;
    private String name;

    public Quiz() {
    }

    public Quiz(long monumentID, String name) {
        this.monumentID = monumentID;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMonumentID() {
        return monumentID;
    }

    public void setMonumentID(long monumentID) {
        this.monumentID = monumentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
