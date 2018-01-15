package com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Record;

import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * Created by assaftayouri on 08/01/2018.
 */

public class RecordHandler
{
    private SQL_Handler sql_handler;
    private SQLiteDatabase db;
    private final String LIMIT = "10";

    public RecordHandler(Context context)
    {
        sql_handler = new SQL_Handler(context);
    }

    public void InsertRecords(DifficultyType difficultyType, Record record)
    {
        db = sql_handler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SqlEntities.COL_LATITUDE, record.getLocation().latitude);
        values.put(SqlEntities.COL_LONGITUDE, record.getLocation().longitude);
        values.put(SqlEntities.COL_NAME, record.getPlayerName());
        values.put(SqlEntities.COL_SCORE, record.getScore());
        db.insert(difficultyType.toString(), null, values);
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
        Cursor cursor = db.query(difficultyType.toString(), projection, null, null, null, null, sortOrder, LIMIT);
        while (cursor.moveToNext())
        {
            record = new Record();
            record.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SqlEntities._ID))).setPlayerName(cursor.getString(cursor.getColumnIndexOrThrow(SqlEntities.COL_NAME)))
                    .setScore(cursor.getInt(cursor.getColumnIndexOrThrow(SqlEntities.COL_SCORE)))
                    .setLocation(new LatLng(cursor.getDouble(cursor.getColumnIndexOrThrow(SqlEntities.COL_LATITUDE)), cursor.getDouble(cursor.getColumnIndexOrThrow(SqlEntities.COL_LONGITUDE))));
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
