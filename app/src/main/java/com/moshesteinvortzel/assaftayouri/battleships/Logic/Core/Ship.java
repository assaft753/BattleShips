package com.moshesteinvortzel.assaftayouri.battleships.Logic.Core;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.CubeType;

import java.io.InterruptedIOException;
import java.util.ArrayList;

/**
 * Created by assaftayouri on 04/12/2017.
 */

public class Ship
{
    private ArrayList<Cube> Cubes;
    private boolean IsSinked;
    private int LengthOfCubes;

    public Ship(int lengthOfCubes)
    {
        this.IsSinked = false;
        this.LengthOfCubes = lengthOfCubes;
        this.Cubes = new ArrayList<Cube>();
    }

    public boolean getIsSinked()
    {
        return IsSinked;
    }

    public ArrayList<Cube> getCubes()
    {
        return Cubes;
    }

    public void UpdateSinkedShips()
    {
        boolean sinked = true;
        for (int i = 0; i < this.Cubes.size(); i++)
        {
            if (this.Cubes.get(i).getStatus() == CubeType.None)
            {
                sinked = false;
                break;
            }
        }
        if (sinked == true)
        {
            for (Cube cube : this.Cubes)
            {
                cube.setStatus(CubeType.Sink);
            }
            this.IsSinked = true;
        }
    }
}
