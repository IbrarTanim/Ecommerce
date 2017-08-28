package com.educareapps.quiz.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "USER".
 */
public class User {

    private Long id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String Age;
    /** Not-null value. */
    private String proPic;
    private int active;
    private int userId;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String name, String Age, String proPic, int active, int userId) {
        this.id = id;
        this.name = name;
        this.Age = Age;
        this.proPic = proPic;
        this.active = active;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getAge() {
        return Age;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAge(String Age) {
        this.Age = Age;
    }

    /** Not-null value. */
    public String getProPic() {
        return proPic;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}