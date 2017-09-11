package com.educareapps.quiz.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.educareapps.quiz.dao.LeaderBoardTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LEADER_BOARD_TABLE".
*/
public class LeaderBoardTableDao extends AbstractDao<LeaderBoardTable, Long> {

    public static final String TABLENAME = "LEADER_BOARD_TABLE";

    /**
     * Properties of entity LeaderBoardTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Board_id = new Property(1, Integer.class, "board_id", false, "BOARD_ID");
        public final static Property Score = new Property(2, long.class, "score", false, "SCORE");
        public final static Property Total_duration = new Property(3, String.class, "total_duration", false, "TOTAL_DURATION");
        public final static Property Negative = new Property(4, String.class, "negative", false, "NEGATIVE");
        public final static Property IsHighscore = new Property(5, boolean.class, "isHighscore", false, "IS_HIGHSCORE");
        public final static Property Created_at = new Property(6, java.util.Date.class, "created_at", false, "CREATED_AT");
        public final static Property User_id = new Property(7, long.class, "user_id", false, "USER_ID");
        public final static Property Test_id = new Property(8, long.class, "test_id", false, "TEST_ID");
    };

    private Query<LeaderBoardTable> userTable_LeaderBoardToUserQuery;
    private Query<LeaderBoardTable> testTable_LeaderBoardToTestQuery;

    public LeaderBoardTableDao(DaoConfig config) {
        super(config);
    }
    
    public LeaderBoardTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LEADER_BOARD_TABLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"BOARD_ID\" INTEGER," + // 1: board_id
                "\"SCORE\" INTEGER NOT NULL ," + // 2: score
                "\"TOTAL_DURATION\" TEXT NOT NULL ," + // 3: total_duration
                "\"NEGATIVE\" TEXT NOT NULL ," + // 4: negative
                "\"IS_HIGHSCORE\" INTEGER NOT NULL ," + // 5: isHighscore
                "\"CREATED_AT\" INTEGER NOT NULL ," + // 6: created_at
                "\"USER_ID\" INTEGER NOT NULL ," + // 7: user_id
                "\"TEST_ID\" INTEGER NOT NULL );"); // 8: test_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LEADER_BOARD_TABLE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, LeaderBoardTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer board_id = entity.getBoard_id();
        if (board_id != null) {
            stmt.bindLong(2, board_id);
        }
        stmt.bindLong(3, entity.getScore());
        stmt.bindString(4, entity.getTotal_duration());
        stmt.bindString(5, entity.getNegative());
        stmt.bindLong(6, entity.getIsHighscore() ? 1L: 0L);
        stmt.bindLong(7, entity.getCreated_at().getTime());
        stmt.bindLong(8, entity.getUser_id());
        stmt.bindLong(9, entity.getTest_id());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public LeaderBoardTable readEntity(Cursor cursor, int offset) {
        LeaderBoardTable entity = new LeaderBoardTable( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // board_id
            cursor.getLong(offset + 2), // score
            cursor.getString(offset + 3), // total_duration
            cursor.getString(offset + 4), // negative
            cursor.getShort(offset + 5) != 0, // isHighscore
            new java.util.Date(cursor.getLong(offset + 6)), // created_at
            cursor.getLong(offset + 7), // user_id
            cursor.getLong(offset + 8) // test_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, LeaderBoardTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setBoard_id(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setScore(cursor.getLong(offset + 2));
        entity.setTotal_duration(cursor.getString(offset + 3));
        entity.setNegative(cursor.getString(offset + 4));
        entity.setIsHighscore(cursor.getShort(offset + 5) != 0);
        entity.setCreated_at(new java.util.Date(cursor.getLong(offset + 6)));
        entity.setUser_id(cursor.getLong(offset + 7));
        entity.setTest_id(cursor.getLong(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(LeaderBoardTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(LeaderBoardTable entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "leaderBoardToUser" to-many relationship of UserTable. */
    public List<LeaderBoardTable> _queryUserTable_LeaderBoardToUser(long user_id) {
        synchronized (this) {
            if (userTable_LeaderBoardToUserQuery == null) {
                QueryBuilder<LeaderBoardTable> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.User_id.eq(null));
                userTable_LeaderBoardToUserQuery = queryBuilder.build();
            }
        }
        Query<LeaderBoardTable> query = userTable_LeaderBoardToUserQuery.forCurrentThread();
        query.setParameter(0, user_id);
        return query.list();
    }

    /** Internal query to resolve the "leaderBoardToTest" to-many relationship of TestTable. */
    public List<LeaderBoardTable> _queryTestTable_LeaderBoardToTest(long test_id) {
        synchronized (this) {
            if (testTable_LeaderBoardToTestQuery == null) {
                QueryBuilder<LeaderBoardTable> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Test_id.eq(null));
                testTable_LeaderBoardToTestQuery = queryBuilder.build();
            }
        }
        Query<LeaderBoardTable> query = testTable_LeaderBoardToTestQuery.forCurrentThread();
        query.setParameter(0, test_id);
        return query.list();
    }

}
