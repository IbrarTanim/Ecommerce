package com.educareapps.quiz.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.educareapps.quiz.dao.UserTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_TABLE".
*/
public class UserTableDao extends AbstractDao<UserTable, Long> {

    public static final String TABLENAME = "USER_TABLE";

    /**
     * Properties of entity UserTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property User_id = new Property(1, long.class, "user_id", false, "USER_ID");
        public final static Property User_name = new Property(2, String.class, "user_name", false, "USER_NAME");
        public final static Property User_first_name = new Property(3, String.class, "user_first_name", false, "USER_FIRST_NAME");
        public final static Property User_last_name = new Property(4, String.class, "user_last_name", false, "USER_LAST_NAME");
        public final static Property Email = new Property(5, String.class, "email", false, "EMAIL");
        public final static Property Address = new Property(6, String.class, "address", false, "ADDRESS");
        public final static Property Occupation = new Property(7, String.class, "occupation", false, "OCCUPATION");
        public final static Property Contact_no = new Property(8, String.class, "contact_no", false, "CONTACT_NO");
        public final static Property Created_at = new Property(9, String.class, "created_at", false, "CREATED_AT");
        public final static Property Status = new Property(10, String.class, "status", false, "STATUS");
    };

    private DaoSession daoSession;


    public UserTableDao(DaoConfig config) {
        super(config);
    }
    
    public UserTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_TABLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"USER_ID\" INTEGER NOT NULL ," + // 1: user_id
                "\"USER_NAME\" TEXT NOT NULL ," + // 2: user_name
                "\"USER_FIRST_NAME\" TEXT NOT NULL ," + // 3: user_first_name
                "\"USER_LAST_NAME\" TEXT NOT NULL ," + // 4: user_last_name
                "\"EMAIL\" TEXT NOT NULL ," + // 5: email
                "\"ADDRESS\" TEXT NOT NULL ," + // 6: address
                "\"OCCUPATION\" TEXT NOT NULL ," + // 7: occupation
                "\"CONTACT_NO\" TEXT NOT NULL ," + // 8: contact_no
                "\"CREATED_AT\" TEXT NOT NULL ," + // 9: created_at
                "\"STATUS\" TEXT);"); // 10: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_TABLE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUser_id());
        stmt.bindString(3, entity.getUser_name());
        stmt.bindString(4, entity.getUser_first_name());
        stmt.bindString(5, entity.getUser_last_name());
        stmt.bindString(6, entity.getEmail());
        stmt.bindString(7, entity.getAddress());
        stmt.bindString(8, entity.getOccupation());
        stmt.bindString(9, entity.getContact_no());
        stmt.bindString(10, entity.getCreated_at());
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(11, status);
        }
    }

    @Override
    protected void attachEntity(UserTable entity) {
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
    public UserTable readEntity(Cursor cursor, int offset) {
        UserTable entity = new UserTable( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // user_id
            cursor.getString(offset + 2), // user_name
            cursor.getString(offset + 3), // user_first_name
            cursor.getString(offset + 4), // user_last_name
            cursor.getString(offset + 5), // email
            cursor.getString(offset + 6), // address
            cursor.getString(offset + 7), // occupation
            cursor.getString(offset + 8), // contact_no
            cursor.getString(offset + 9), // created_at
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // status
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUser_id(cursor.getLong(offset + 1));
        entity.setUser_name(cursor.getString(offset + 2));
        entity.setUser_first_name(cursor.getString(offset + 3));
        entity.setUser_last_name(cursor.getString(offset + 4));
        entity.setEmail(cursor.getString(offset + 5));
        entity.setAddress(cursor.getString(offset + 6));
        entity.setOccupation(cursor.getString(offset + 7));
        entity.setContact_no(cursor.getString(offset + 8));
        entity.setCreated_at(cursor.getString(offset + 9));
        entity.setStatus(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserTable entity) {
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
    
}
