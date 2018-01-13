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
    private int id;
    public Record(int id)
    {
        this.id=id;
    }

    public Record(LatLng location, int score, String playerName)
    {
        this.location = location;
        this.score = score;
        this.playerName = playerName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return id == record.id;
    }
}
