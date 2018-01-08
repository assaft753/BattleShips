package com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by assaftayouri on 08/01/2018.
 */

public class Record
{
    public LatLng location;
    public int score;
    public String playerName;

    public Record()
    {

    }

    public Record(LatLng location, int score, String playerName)
    {
        this.location = location;
        this.score = score;
        this.playerName = playerName;
    }
}
