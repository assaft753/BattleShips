package com.moshesteinvortzel.assaftayouri.battleships.Logic.Core;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.CubeType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.ShipType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.SubmarineType;

/**
 * Created by assaftayouri on 04/12/2017.
 */

public class Cube
{
    private CubeType Status;
    private boolean Empty;
    private SubmarineType submarineType;
    private ShipType orientation;

    public Cube()
    {
        this.Status = CubeType.None;
        this.Empty = true;
    }

    public CubeType getStatus()
    {
        return Status;
    }

    public void setStatus(CubeType status)
    {
        Status = status;
    }

    public void setEmpty(boolean empty)
    {
        Empty = empty;
    }

    public Boolean getEmpty()
    {
        return this.Empty;
    }

    public void setSubmarineType(SubmarineType submarineType)
    {
        this.submarineType = submarineType;
    }

    public SubmarineType getSubmarineType()
    {
        return this.submarineType;
    }

    public void setOrientation(ShipType orientation)
    {
        this.orientation = orientation;
    }

    public ShipType getOrientation()
    {
        return this.orientation;
    }
}
