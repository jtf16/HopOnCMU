package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

import java.io.Serializable;

import javax.crypto.*;

public class User implements Serializable {
    
    private static final long serialVersionUID = -8807331723807741905L;

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int ranking;
    private int score;
    private long time;
    private transient SecretKey sharedSecret = null;

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

    public SecretKey getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(SecretKey sharedSecret) {
        this.sharedSecret = sharedSecret;
    }
}
