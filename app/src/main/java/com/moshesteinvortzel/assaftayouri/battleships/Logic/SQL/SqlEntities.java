package com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL;

import android.provider.BaseColumns;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;

/**
 * Created by assaftayouri on 08/01/2018.
 */

public class SqlEntities implements BaseColumns
{
    public static final String COL_NAME = "name";
    public static final String COL_SCORE = "score";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_LATITUDE = "latitude";
    public static final DifficultyType[] DIFFICULTIES = {DifficultyType.Easy, DifficultyType.Medium, DifficultyType.Hard};
}
