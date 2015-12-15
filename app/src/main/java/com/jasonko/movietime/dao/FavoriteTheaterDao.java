package com.jasonko.movietime.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.jasonko.movietime.dao.FavoriteTheater;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table FAVORITE_THEATER.
*/
public class FavoriteTheaterDao extends AbstractDao<FavoriteTheater, Long> {

    public static final String TABLENAME = "FAVORITE_THEATER";

    /**
     * Properties of entity FavoriteTheater.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Theater_id = new Property(1, int.class, "theater_id", false, "THEATER_ID");
        public final static Property Area_id = new Property(2, int.class, "area_id", false, "AREA_ID");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
        public final static Property Phone = new Property(5, String.class, "phone", false, "PHONE");
    };


    public FavoriteTheaterDao(DaoConfig config) {
        super(config);
    }
    
    public FavoriteTheaterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'FAVORITE_THEATER' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'THEATER_ID' INTEGER NOT NULL ," + // 1: theater_id
                "'AREA_ID' INTEGER NOT NULL ," + // 2: area_id
                "'NAME' TEXT NOT NULL ," + // 3: name
                "'ADDRESS' TEXT NOT NULL ," + // 4: address
                "'PHONE' TEXT NOT NULL );"); // 5: phone
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'FAVORITE_THEATER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, FavoriteTheater entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getTheater_id());
        stmt.bindLong(3, entity.getArea_id());
        stmt.bindString(4, entity.getName());
        stmt.bindString(5, entity.getAddress());
        stmt.bindString(6, entity.getPhone());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public FavoriteTheater readEntity(Cursor cursor, int offset) {
        FavoriteTheater entity = new FavoriteTheater( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // theater_id
            cursor.getInt(offset + 2), // area_id
            cursor.getString(offset + 3), // name
            cursor.getString(offset + 4), // address
            cursor.getString(offset + 5) // phone
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, FavoriteTheater entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTheater_id(cursor.getInt(offset + 1));
        entity.setArea_id(cursor.getInt(offset + 2));
        entity.setName(cursor.getString(offset + 3));
        entity.setAddress(cursor.getString(offset + 4));
        entity.setPhone(cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(FavoriteTheater entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(FavoriteTheater entity) {
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
