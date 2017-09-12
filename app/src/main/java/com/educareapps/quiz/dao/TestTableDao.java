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

import com.educareapps.quiz.dao.TestTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEST_TABLE".
*/
public class TestTableDao extends AbstractDao<TestTable, Long> {

    public static final String TABLENAME = "TEST_TABLE";

    /**
     * Properties of entity TestTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Test_id = new Property(1, long.class, "test_id", false, "TEST_ID");
        public final static Property Test_name = new Property(2, String.class, "test_name", false, "TEST_NAME");
        public final static Property Question_start_from = new Property(3, String.class, "question_start_from", false, "QUESTION_START_FROM");
        public final static Property Question_start_to = new Property(4, String.class, "question_start_to", false, "QUESTION_START_TO");
        public final static Property Status = new Property(5, String.class, "status", false, "STATUS");
        public final static Property Created_at = new Property(6, String.class, "created_at", false, "CREATED_AT");
        public final static Property Total_time = new Property(7, String.class, "total_time", false, "TOTAL_TIME");
        public final static Property Total_mark = new Property(8, String.class, "total_mark", false, "TOTAL_MARK");
        public final static Property Question_set_id = new Property(9, long.class, "question_set_id", false, "QUESTION_SET_ID");
    };

    private DaoSession daoSession;

    private Query<TestTable> questionSetTable_TestToQuestionSetQuery;

    public TestTableDao(DaoConfig config) {
        super(config);
    }
    
    public TestTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEST_TABLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"TEST_ID\" INTEGER NOT NULL ," + // 1: test_id
                "\"TEST_NAME\" TEXT NOT NULL ," + // 2: test_name
                "\"QUESTION_START_FROM\" TEXT NOT NULL ," + // 3: question_start_from
                "\"QUESTION_START_TO\" TEXT NOT NULL ," + // 4: question_start_to
                "\"STATUS\" TEXT," + // 5: status
                "\"CREATED_AT\" TEXT," + // 6: created_at
                "\"TOTAL_TIME\" TEXT NOT NULL ," + // 7: total_time
                "\"TOTAL_MARK\" TEXT NOT NULL ," + // 8: total_mark
                "\"QUESTION_SET_ID\" INTEGER NOT NULL );"); // 9: question_set_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEST_TABLE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TestTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getTest_id());
        stmt.bindString(3, entity.getTest_name());
        stmt.bindString(4, entity.getQuestion_start_from());
        stmt.bindString(5, entity.getQuestion_start_to());
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(6, status);
        }
 
        String created_at = entity.getCreated_at();
        if (created_at != null) {
            stmt.bindString(7, created_at);
        }
        stmt.bindString(8, entity.getTotal_time());
        stmt.bindString(9, entity.getTotal_mark());
        stmt.bindLong(10, entity.getQuestion_set_id());
    }

    @Override
    protected void attachEntity(TestTable entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TestTable readEntity(Cursor cursor, int offset) {
        TestTable entity = new TestTable( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // test_id
            cursor.getString(offset + 2), // test_name
            cursor.getString(offset + 3), // question_start_from
            cursor.getString(offset + 4), // question_start_to
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // status
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // created_at
            cursor.getString(offset + 7), // total_time
            cursor.getString(offset + 8), // total_mark
            cursor.getLong(offset + 9) // question_set_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TestTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTest_id(cursor.getLong(offset + 1));
        entity.setTest_name(cursor.getString(offset + 2));
        entity.setQuestion_start_from(cursor.getString(offset + 3));
        entity.setQuestion_start_to(cursor.getString(offset + 4));
        entity.setStatus(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreated_at(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTotal_time(cursor.getString(offset + 7));
        entity.setTotal_mark(cursor.getString(offset + 8));
        entity.setQuestion_set_id(cursor.getLong(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TestTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TestTable entity) {
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
    
    /** Internal query to resolve the "testToQuestionSet" to-many relationship of QuestionSetTable. */
    public List<TestTable> _queryQuestionSetTable_TestToQuestionSet(long question_set_id) {
        synchronized (this) {
            if (questionSetTable_TestToQuestionSetQuery == null) {
                QueryBuilder<TestTable> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Question_set_id.eq(null));
                questionSetTable_TestToQuestionSetQuery = queryBuilder.build();
            }
        }
        Query<TestTable> query = questionSetTable_TestToQuestionSetQuery.forCurrentThread();
        query.setParameter(0, question_set_id);
        return query.list();
    }

}
