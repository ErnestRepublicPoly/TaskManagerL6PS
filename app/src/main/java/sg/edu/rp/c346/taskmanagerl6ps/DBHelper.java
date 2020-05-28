package sg.edu.rp.c346.taskmanagerl6ps;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "task.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_TASK = "Task";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_DESCRIPTION = "Description";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createToDoTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT ) ";
        db.execSQL(createToDoTableSql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_TASK + " ADD COLUMN module_name TEXT ");

    }


    public long insertTask(String name, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, desc);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result);
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        return result;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> notes = new ArrayList<Task>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + ","
                + COLUMN_DESCRIPTION + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String todoContent = cursor.getString(1);
                String date = cursor.getString(2);
                Task note = new Task(id, todoContent, date);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

}
