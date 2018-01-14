package com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by assaftayouri on 08/01/2018.
 */

public class Record
{
    private LatLng location;
    private int score;
    private String playerName;
    private int id;

    public Record()
    {
    }

    public Record(LatLng location, int score, String playerName)
    {
        this.location = location;
        this.score = score;
        this.playerName = playerName;
    }

    public LatLng getLocation()
    {
        return location;
    }

    public Record setLocation(LatLng location)
    {
        this.location = location;
        return this;
    }

    public int getScore()
    {
        return score;
    }

    public Record setScore(int score)
    {
        this.score = score;
        return this;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public Record setPlayerName(String playerName)
    {
        this.playerName = playerName;
        return this;
    }

    public int getId()
    {
        return id;
    }

    public Record setId(int id)
    {
        this.id = id;
        return this;
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
