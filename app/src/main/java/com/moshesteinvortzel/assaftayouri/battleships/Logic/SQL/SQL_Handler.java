package com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;

/**
 * Created by assaftayouri on 08/01/2018.
 */

public class SQL_Handler extends SQLiteOpenHelper
{

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + "?" + " (" +
                    SqlEntities._ID + " INTEGER PRIMARY KEY," +
                    SqlEntities.COL_NAME + " TEXT," +
                    SqlEntities.COL_SCORE + " INTEGER," +
                    SqlEntities.COL_LONGITUDE + " REAL," +
                    SqlEntities.COL_LATITUDE + " REAL)";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Records.db";

    public SQL_Handler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String query;
        for (DifficultyType difficultyType : SqlEntities.DIFFICULTIES)
        {
            query = SQL_CREATE_ENTRIES.replace("?", difficultyType.toString());
            sqLiteDatabase.execSQL(query);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
