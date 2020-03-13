package com.pbd.ifttw.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pbd.ifttw.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SQLiteRoutineDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "IFTTWDB";
    private static final String TABLE_NAME = "ROUTINE";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONDITION_TYPE = "condition_type";
    private static final String KEY_CONDITION_VALUE = "condition_value";
    private static final String KEY_ACTION_TYPE = "action_type";
    private static final String KEY_ACTION_VALUE = "action_value";
    private static final String KEY_STATUS = "status";

    private static final String[] COLUMNS = { KEY_ID,
            KEY_NAME,
            KEY_CONDITION_TYPE,
            KEY_CONDITION_VALUE,
            KEY_ACTION_TYPE,
            KEY_ACTION_VALUE,
            KEY_STATUS
    };

    public SQLiteRoutineDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE ROUTINE ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "condition_type TEXT," +
                "condition_value TEXT," +
                "action_type TEXT," +
                "action_value TEXT," +
                "status TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public List<Routine> allRoutine() {
        List<Routine> routines = new ArrayList<>();
        String selectQuery = "SELECT  * FROM ROUTINE";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Routine routine = new Routine();
                routine.setId(cursor.getString(0));
                routine.setName(cursor.getString(1));
                routine.setCondition_type(cursor.getString(2));
                routine.setCondition_value(cursor.getString(3));
                routine.setAction_type(cursor.getString(4));
                routine.setAction_value(cursor.getString(5));
                routine.setStatus(cursor.getString(6));
                routines.add(routine);
            } while (cursor.moveToNext());
        }

        db.close();
        return routines;
    }

    public void addRoutine(Routine routine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO ROUTINE " +
                "VALUES('" + routine.getId() + "','"
                + routine.getName() + "','"
                + routine.getCondition_type() + "','"
                + routine.getCondition_value() + "','"
                + routine.getAction_type() + "','"
                + routine.getAction_value() + "','"
                + routine.getStatus() + "');");
        db.close();
    }
}