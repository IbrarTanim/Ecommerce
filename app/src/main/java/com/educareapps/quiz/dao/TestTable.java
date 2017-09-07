package com.educareapps.quiz.dao;

import java.util.List;
import com.educareapps.quiz.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "TEST_TABLE".
 */
public class TestTable {

    private Long id;
    private long test_id;
    /** Not-null value. */
    private String question_start_from;
    /** Not-null value. */
    private String question_start_to;
    private String status;
    private String created_at;
    private long question_set_id;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TestTableDao myDao;

    private List<LeaderBoardTable> leaderBoardToTest;

    public TestTable() {
    }

    public TestTable(Long id) {
        this.id = id;
    }

    public TestTable(Long id, long test_id, String question_start_from, String question_start_to, String status, String created_at, long question_set_id) {
        this.id = id;
        this.test_id = test_id;
        this.question_start_from = question_start_from;
        this.question_start_to = question_start_to;
        this.status = status;
        this.created_at = created_at;
        this.question_set_id = question_set_id;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTestTableDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTest_id() {
        return test_id;
    }

    public void setTest_id(long test_id) {
        this.test_id = test_id;
    }

    /** Not-null value. */
    public String getQuestion_start_from() {
        return question_start_from;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setQuestion_start_from(String question_start_from) {
        this.question_start_from = question_start_from;
    }

    /** Not-null value. */
    public String getQuestion_start_to() {
        return question_start_to;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setQuestion_start_to(String question_start_to) {
        this.question_start_to = question_start_to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getQuestion_set_id() {
        return question_set_id;
    }

    public void setQuestion_set_id(long question_set_id) {
        this.question_set_id = question_set_id;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<LeaderBoardTable> getLeaderBoardToTest() {
        if (leaderBoardToTest == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LeaderBoardTableDao targetDao = daoSession.getLeaderBoardTableDao();
            List<LeaderBoardTable> leaderBoardToTestNew = targetDao._queryTestTable_LeaderBoardToTest(id);
            synchronized (this) {
                if(leaderBoardToTest == null) {
                    leaderBoardToTest = leaderBoardToTestNew;
                }
            }
        }
        return leaderBoardToTest;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetLeaderBoardToTest() {
        leaderBoardToTest = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
