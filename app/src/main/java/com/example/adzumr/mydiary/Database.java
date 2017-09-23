package com.example.adzumr.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adzumr on 9/20/17.
 */

public class Database extends SQLiteOpenHelper {

    public static final String POINTS_TABLE = "POINTS";
    public static final String ID_COLUMN = "ID";
    public static final String  X_COLUMN = "X";
    public static final String  Y_COLUMN = "Y";

    public Database(Context context) {
        super(context, "DiaryDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = String.format("create table %s (%s INTEGER PRIMARY KEY, %s INTEGER NOT NULL, %s INTEGER NOT NULL)", POINTS_TABLE, ID_COLUMN, X_COLUMN, Y_COLUMN);
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void getPoints (List<Point> points){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(POINTS_TABLE, null, null);

        int i = 0;

        for (Point point: points){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ID_COLUMN, i);
            contentValues.put(X_COLUMN, point.x);
            contentValues.put(Y_COLUMN, point.y);

            sqLiteDatabase.insert(POINTS_TABLE, null, contentValues);

            i++;
        }

        sqLiteDatabase.close();
    }


//    This Metthod Retrive The Stored Points
    public List<Point> retrievePoints(){
        List<Point> points = new ArrayList<Point>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = String.format("SELECT %s, %s FROM %s ORDER BY %s", X_COLUMN, Y_COLUMN, POINTS_TABLE, ID_COLUMN);
       Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()){
            int x = cursor.getInt(0);
            int y = cursor.getInt(1);

            points.add(new Point (x, y));
        }
        database.close();
        return points;
    }
}
