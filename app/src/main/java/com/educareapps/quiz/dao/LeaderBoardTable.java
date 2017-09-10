package com.educareapps.quiz.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "LEADER_BOARD_TABLE".
 */
public class LeaderBoardTable {

    private Long id;
    private long board_id;
    private long score;
    /** Not-null value. */
    private String total_duration;
    /** Not-null value. */
    private String negative;
    private boolean isHighscore;
    private long user_id;
    private long test_id;

    public LeaderBoardTable() {
    }

    public LeaderBoardTable(Long id) {
        this.id = id;
    }

    public LeaderBoardTable(Long id, long board_id, long score, String total_duration, String negative, boolean isHighscore, long user_id, long test_id) {
        this.id = id;
        this.board_id = board_id;
        this.score = score;
        this.total_duration = total_duration;
        this.negative = negative;
        this.isHighscore = isHighscore;
        this.user_id = user_id;
        this.test_id = test_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getBoard_id() {
        return board_id;
    }

    public void setBoard_id(long board_id) {
        this.board_id = board_id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    /** Not-null value. */
    public String getTotal_duration() {
        return total_duration;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTotal_duration(String total_duration) {
        this.total_duration = total_duration;
    }

    /** Not-null value. */
    public String getNegative() {
        return negative;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setNegative(String negative) {
        this.negative = negative;
    }

    public boolean getIsHighscore() {
        return isHighscore;
    }

    public void setIsHighscore(boolean isHighscore) {
        this.isHighscore = isHighscore;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getTest_id() {
        return test_id;
    }

    public void setTest_id(long test_id) {
        this.test_id = test_id;
    }

}