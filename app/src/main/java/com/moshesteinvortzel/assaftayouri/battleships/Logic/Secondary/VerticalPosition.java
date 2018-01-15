package com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary;

import android.sax.StartElementListener;

/**
 * Created by assaftayouri on 04/12/2017.
 */

public class VerticalPosition extends Position
{

    private int ColNum;

    public VerticalPosition(int start, int end, int colNum)
    {
        super(start, end);
        this.ColNum = colNum;
    }

    public int getColNum()
    {
        return this.ColNum;
    }

    @Override
    public String toString()
    {
        return "VerticalPosition{" +
                "ColNum=" + ColNum + " " + super.toString() + " " +
                '}';
    }
}
