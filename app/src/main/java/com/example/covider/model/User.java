package com.example.covider.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    private String key;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<String> freq_visited ;
    private List<Section> should_visit ;
    private List<Health> health_history ;
    private List<User> closeContacts ;
    private NotificationMessage message;
    private boolean haveCovid;
    private boolean instructor;

    public User(){

    }

    public User(String firstName, String lastName, String email, String password, boolean isInstructor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.instructor = isInstructor;
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

    public List<String> getFreq_visited() {
        return freq_visited;
    }

    public void setFreq_visited(List<String> freq_visited) {
        this.freq_visited = freq_visited;
    }

    public List<Section> getShould_visit() {
        return should_visit;
    }

    public void setShould_visit(List<Section> should_visit) {
        this.should_visit = should_visit;
    }

    public List<Health> getHealth_history() {
        return health_history;
    }

    public void setHealth_history(List<Health> health_history) {
        this.health_history = health_history;
    }

    public List<User> getCloseContacts() {
        return closeContacts;
    }

    public void setCloseContacts(ArrayList<User> closeContacts) {
        this.closeContacts = closeContacts;
    }

    public boolean isHaveCovid() {
        return haveCovid;
    }

    public void setHaveCovid(boolean haveCovid) {
        this.haveCovid = haveCovid;
    }

    public boolean isInstructor() {
        return instructor;
    }

    public void setInstructor(boolean instructor) {
        this.instructor = instructor;
    }

    public NotificationMessage getMessage() {
        return message;
    }

    public void setMessage(NotificationMessage message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
