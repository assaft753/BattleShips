package com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Record;

import java.util.ArrayList;

/**
 * Created by assaftayouri on 08/01/2018.
 */

public class RecordHandler
{
    private SQL_Handler sql_handler;
    private SQLiteDatabase db;

    public RecordHandler(Context context)
    {
        sql_handler = new SQL_Handler(context);
    }

    public void InsertRecords(DifficultyType difficultyType,Record record)
    {
        db = sql_handler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlEntities.COL_LATITUDE, record.location.latitude);
        values.put(SqlEntities.COL_LONGITUDE, record.location.longitude);
        values.put(SqlEntities.COL_NAME, record.playerName);
        values.put(SqlEntities.COL_SCORE, record.score);
        long newRowId = db.insert(difficultyType.toString(), null, values);
    }

    public ArrayList<Record> GetRecords(DifficultyType difficultyType)
    {
        double lati;
        double longi;
        Record record;
        ArrayList<Record> records = new ArrayList<Record>();
        db = sql_handler.getReadableDatabase();
        String[] projection = {
                SqlEntities._ID,
                SqlEntities.COL_NAME,
                SqlEntities.COL_SCORE,
                SqlEntities.COL_LATITUDE,
                SqlEntities.COL_LONGITUDE
        };

        String sortOrder = SqlEntities.COL_SCORE + " ASC";
        Cursor cursor = db.query(difficultyType.toString(), projection, null, null, null, null, sortOrder, "10");
        while (cursor.moveToNext())
        {
            record = new Record(cursor.getInt(cursor.getColumnIndexOrThrow(SqlEntities._ID)));
            record.playerName = cursor.getString(cursor.getColumnIndexOrThrow(SqlEntities.COL_NAME));
            record.score = cursor.getInt(cursor.getColumnIndexOrThrow(SqlEntities.COL_SCORE));
            lati = cursor.getDouble(cursor.getColumnIndexOrThrow(SqlEntities.COL_LATITUDE));
            longi = cursor.getDouble(cursor.getColumnIndexOrThrow(SqlEntities.COL_LONGITUDE));
            record.location = new LatLng(lati, longi);
            records.add(record);
        }
        cursor.close();
        return records;
    }
    public void CloseSQl()
    {
        sql_handler.close();
    }

}
