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

import com.educareapps.quiz.dao.CSVQuestionTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CSVQUESTION_TABLE".
*/
public class CSVQuestionTableDao extends AbstractDao<CSVQuestionTable, Long> {

    public static final String TABLENAME = "CSVQUESTION_TABLE";

    /**
     * Properties of entity CSVQuestionTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Question_id = new Property(1, long.class, "question_id", false, "QUESTION_ID");
        public final static Property Question = new Property(2, String.class, "question", false, "QUESTION");
        public final static Property Option_one = new Property(3, String.class, "option_one", false, "OPTION_ONE");
        public final static Property Option_two = new Property(4, String.class, "option_two", false, "OPTION_TWO");
        public final static Property Option_three = new Property(5, String.class, "option_three", false, "OPTION_THREE");
        public final static Property Option_four = new Property(6, String.class, "option_four", false, "OPTION_FOUR");
        public final static Property Answer = new Property(7, String.class, "answer", false, "ANSWER");
        public final static Property Question_set_id = new Property(8, long.class, "question_set_id", false, "QUESTION_SET_ID");
    };

    private Query<CSVQuestionTable> questionSetTable_QuestionSetToLanguageQuery;

    public CSVQuestionTableDao(DaoConfig config) {
        super(config);
    }
    
    public CSVQuestionTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CSVQUESTION_TABLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"QUESTION_ID\" INTEGER NOT NULL ," + // 1: question_id
                "\"QUESTION\" TEXT NOT NULL ," + // 2: question
                "\"OPTION_ONE\" TEXT NOT NULL ," + // 3: option_one
                "\"OPTION_TWO\" TEXT NOT NULL ," + // 4: option_two
                "\"OPTION_THREE\" TEXT NOT NULL ," + // 5: option_three
                "\"OPTION_FOUR\" TEXT NOT NULL ," + // 6: option_four
                "\"ANSWER\" TEXT NOT NULL ," + // 7: answer
                "\"QUESTION_SET_ID\" INTEGER NOT NULL );"); // 8: question_set_id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CSVQUESTION_TABLE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CSVQuestionTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getQuestion_id());
        stmt.bindString(3, entity.getQuestion());
        stmt.bindString(4, entity.getOption_one());
        stmt.bindString(5, entity.getOption_two());
        stmt.bindString(6, entity.getOption_three());
        stmt.bindString(7, entity.getOption_four());
        stmt.bindString(8, entity.getAnswer());
        stmt.bindLong(9, entity.getQuestion_set_id());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CSVQuestionTable readEntity(Cursor cursor, int offset) {
        CSVQuestionTable entity = new CSVQuestionTable( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // question_id
            cursor.getString(offset + 2), // question
            cursor.getString(offset + 3), // option_one
            cursor.getString(offset + 4), // option_two
            cursor.getString(offset + 5), // option_three
            cursor.getString(offset + 6), // option_four
            cursor.getString(offset + 7), // answer
            cursor.getLong(offset + 8) // question_set_id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CSVQuestionTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setQuestion_id(cursor.getLong(offset + 1));
        entity.setQuestion(cursor.getString(offset + 2));
        entity.setOption_one(cursor.getString(offset + 3));
        entity.setOption_two(cursor.getString(offset + 4));
        entity.setOption_three(cursor.getString(offset + 5));
        entity.setOption_four(cursor.getString(offset + 6));
        entity.setAnswer(cursor.getString(offset + 7));
        entity.setQuestion_set_id(cursor.getLong(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CSVQuestionTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CSVQuestionTable entity) {
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
    
    /** Internal query to resolve the "questionSetToLanguage" to-many relationship of QuestionSetTable. */
    public List<CSVQuestionTable> _queryQuestionSetTable_QuestionSetToLanguage(long question_set_id) {
        synchronized (this) {
            if (questionSetTable_QuestionSetToLanguageQuery == null) {
                QueryBuilder<CSVQuestionTable> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Question_set_id.eq(null));
                questionSetTable_QuestionSetToLanguageQuery = queryBuilder.build();
            }
        }
        Query<CSVQuestionTable> query = questionSetTable_QuestionSetToLanguageQuery.forCurrentThread();
        query.setParameter(0, question_set_id);
        return query.list();
    }

}
