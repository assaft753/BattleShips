package com.moshesteinvortzel.assaftayouri.battleships.Logic.Core;

import android.sax.EndElementListener;
import android.util.Log;
import android.util.Xml;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.CubeType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.ShipType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.SubmarineType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.HorizontalPosition;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Position;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.VerticalPosition;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by assaftayouri on 04/12/2017.
 */

public class Board
{
    private Cube[][] CubeBoard;
    private ArrayList<Ship> Ships;
    private int Difficulty;

    public Board(DifficultyType diff)
    {
        this.Difficulty = diff.getDifficultyAsNumber();
        this.CubeBoard = new Cube[BattleShip.SIZE][BattleShip.SIZE];

        for (int i = 0; i < BattleShip.SIZE; i++)
        {
            for (int j = 0; j < BattleShip.SIZE; j++)
            {
                this.CubeBoard[i][j] = new Cube();
            }

        }
        this.Ships = new ArrayList<Ship>();
        PositionShips();
    }

    private void PositionShips()
    {
        boolean valid = true;
        Random rnd = new Random();
        ArrayList<Position> positions;
        int rndHOV = rnd.nextInt(101);
        int rndPOS;

        for (int i = 0; i < BattleShip.AMOUNT; i++)
        {
            if (rndHOV % 2 == 0)
            {
                positions = CheckVerticalPositions();
                if (positions != null)
                {
                    rndPOS = rnd.nextInt(positions.size());
                    this.Ships.add(AllocateShipPosition(positions.get(rndPOS), ShipType.Vertical));
                }
                else
                {
                    valid = false;
                    rndHOV = 3;
                    positions = CheckHorizontalPositions();
                    rndPOS = rnd.nextInt(positions.size());
                    this.Ships.add(AllocateShipPosition(positions.get(rndPOS), ShipType.Horizontal));
                }
            }
            else
            {
                positions = CheckHorizontalPositions();
                if (positions != null)
                {
                    rndPOS = rnd.nextInt(positions.size());
                    this.Ships.add(AllocateShipPosition(positions.get(rndPOS), ShipType.Horizontal));
                }
                else
                {
                    valid = false;
                    rndHOV = 2;
                    positions = CheckVerticalPositions();
                    rndPOS = rnd.nextInt(positions.size());
                    this.Ships.add(AllocateShipPosition(positions.get(rndPOS), ShipType.Vertical));
                }
            }

            if (valid == true)
            {
                rndHOV = rnd.nextInt(101);
            }
        }
    }

    private ArrayList<Position> CheckVerticalPositions()
    {
        ArrayList<Position> VertPos = new ArrayList<Position>();
        int CountCubes = 0;
        Boolean exit = false;
        for (int j = 0; j < BattleShip.SIZE; j++)
        {
            for (int i = 0; i < BattleShip.SIZE; i++)
            {
                for (int k = i; k < BattleShip.SIZE && exit == false; k++)
                {
                    if (this.CubeBoard[k][j].getEmpty() == true)
                    {
                        CountCubes++;

                        if (CountCubes == this.Difficulty)
                        {
                            VertPos.add(new VerticalPosition(i, k, j));
                            exit = true;
                        }
                    }
                    else
                    {
                        exit = true;
                    }
                }
                exit = false;
                CountCubes = 0;
            }
        }
        if (VertPos.size() != 0)
        {
            return VertPos;
        }
        return null;

    }

    private ArrayList<Position> CheckHorizontalPositions()
    {
        ArrayList<Position> HorPos = new ArrayList<Position>();
        Boolean exit = false;
        int CountCubes = 0;
        for (int i = 0; i < BattleShip.SIZE; i++)
        {
            for (int j = 0; j < BattleShip.SIZE; j++)
            {
                for (int k = j; k < BattleShip.SIZE && exit == false; k++)
                {
                    if (this.CubeBoard[i][k].getEmpty() == true)
                    {
                        CountCubes++;
                        if (CountCubes == this.Difficulty)
                        {
                            HorPos.add(new HorizontalPosition(j, k, i));
                            exit = true;
                        }
                    }
                    else
                    {
                        exit = true;

                    }
                }
                exit = false;
                CountCubes = 0;
            }
        }
        if (HorPos.size() != 0)
        {
            return HorPos;
        }
        return null;
    }

    private Ship AllocateShipPosition(Position position, ShipType shipType)
    {
        Ship ship = new Ship(this.Difficulty);
        if (shipType == ShipType.Vertical)
        {
            VerticalPosition verticalPosition = (VerticalPosition) position;
            for (int i = verticalPosition.getStart(); i <= verticalPosition.getEnd(); i++)
            {
                Cube cube = this.CubeBoard[i][verticalPosition.getColNum()];//
                cube.setEmpty(false);
                cube.setOrientation(ShipType.Vertical);//
                if (i == verticalPosition.getStart())
                {
                    cube.setSubmarineType(SubmarineType.Start);
                }
                else if (i == verticalPosition.getEnd())
                {
                    cube.setSubmarineType(SubmarineType.End);
                }
                else
                {
                    cube.setSubmarineType(SubmarineType.Middle);
                }
                ship.getCubes().add(cube);
            }
        }
        else
        {
            HorizontalPosition horizontalPosition = (HorizontalPosition) position;
            for (int i = horizontalPosition.getStart(); i <= horizontalPosition.getEnd(); i++)
            {
                Cube cube = this.CubeBoard[horizontalPosition.getRowNum()][i];//
                cube.setEmpty(false);
                cube.setOrientation(ShipType.Horizontal);//
                if (i == horizontalPosition.getStart())
                {
                    cube.setSubmarineType(SubmarineType.Start);
                }
                else if (i == horizontalPosition.getEnd())
                {
                    cube.setSubmarineType(SubmarineType.End);
                }
                else
                {
                    cube.setSubmarineType(SubmarineType.Middle);
                }
                ship.getCubes().add(cube);
            }
        }
        return ship;
    }

    public void reArrangeShips()
    {
        for (int i = 0; i < BattleShip.SIZE; i++)
        {
            for (int j = 0; j < BattleShip.SIZE; j++)
            {
                if (this.CubeBoard[i][j].getStatus() != CubeType.Sink)
                {
                    CubeBoard[i][j] = new Cube();
                }
            }
        }
        rePositionShips();

    }

    private void rePositionShips()
    {
        boolean valid = true;
        Random rnd = new Random();
        ArrayList<Position> positions;
        int rndHOV = rnd.nextInt(101);
        int rndPOS;

        for (int i = 0; i < BattleShip.AMOUNT; i++)
        {
            if (this.Ships.get(i).getIsSinked() == false)
            {
                if (rndHOV % 2 == 0)
                {
                    positions = CheckVerticalPositions();
                    if (positions != null)
                    {
                        rndPOS = rnd.nextInt(positions.size());
                        reAllocateShipPosition(positions.get(rndPOS), ShipType.Vertical, this.Ships.get(i));
                    }
                    else
                    {
                        valid = false;
                        rndHOV = 3;
                        positions = CheckHorizontalPositions();
                        rndPOS = rnd.nextInt(positions.size());
                        reAllocateShipPosition(positions.get(rndPOS), ShipType.Horizontal, this.Ships.get(i));
                    }
                }
                else
                {
                    positions = CheckHorizontalPositions();
                    if (positions != null)
                    {
                        rndPOS = rnd.nextInt(positions.size());
                        reAllocateShipPosition(positions.get(rndPOS), ShipType.Horizontal, this.Ships.get(i));
                    }
                    else
                    {
                        valid = false;
                        rndHOV = 2;
                        positions = CheckVerticalPositions();
                        rndPOS = rnd.nextInt(positions.size());
                        reAllocateShipPosition(positions.get(rndPOS), ShipType.Vertical, this.Ships.get(i));
                    }
                }

                if (valid == true)
                {
                    rndHOV = rnd.nextInt(101);
                }
            }
        }
    }

    private void reAllocateShipPosition(Position position, ShipType shipType, Ship ship)
    {
        if (shipType == ShipType.Vertical)
        {
            VerticalPosition verticalPosition = (VerticalPosition) position;
            for (int i = verticalPosition.getStart(), j = 0; i <= verticalPosition.getEnd(); j++, i++)
            {
                this.CubeBoard[i][verticalPosition.getColNum()] = ship.getCubes().get(j);
                ship.getCubes().get(j).setOrientation(ShipType.Vertical);
            }
        }
        else
        {
            HorizontalPosition horizontalPosition = (HorizontalPosition) position;
            for (int i = horizontalPosition.getStart(), j = 0; i <= horizontalPosition.getEnd(); j++, i++)
            {
                this.CubeBoard[horizontalPosition.getRowNum()][i] = ship.getCubes().get(j);
                ship.getCubes().get(j).setOrientation(ShipType.Horizontal);
            }
        }
    }

    public Cube[][] getCubes()
    {
        return this.CubeBoard;
    }

    public Cube getCube(int position)
    {
        return this.CubeBoard[position / BattleShip.SIZE][position % BattleShip.SIZE];
    }

    public CubeType Shoot(int x, int y)
    {

        Cube cube = this.CubeBoard[x][y];
        if (cube.getStatus() == CubeType.None)
        {
            if (cube.getEmpty() == false)
            {
                cube.setStatus(CubeType.Hit);
                UpdateShips();
                return CubeType.Hit;
            }
            else
            {
                cube.setStatus(CubeType.Miss);
                return CubeType.Miss;
            }
        }
        return CubeType.None;
    }

    private void UpdateShips()
    {
        for (Ship ship : Ships)
        {
            ship.UpdateSinkedShips();
        }
    }

    public Boolean CheckIfWin()
    {
        for (Ship ship : Ships)
        {
            if (ship.getIsSinked() == false)
            {
                return false;
            }
        }
        return true;
    }

    public Boolean checkIfTouched(int pos)
    {
        int x = pos / BattleShip.SIZE;
        int y = pos % BattleShip.SIZE;
        return this.CubeBoard[x][y].getStatus() != CubeType.None;
    }

    public boolean HitPlayerBoard()
    {
        for (int i = 0; i < BattleShip.SIZE; i++)
        {
            for (int j = 0; j < BattleShip.SIZE; j++)
            {
                if (CubeBoard[i][j].getStatus() == CubeType.None && ! CubeBoard[i][j].getEmpty())
                {
                    Shoot(i, j);
                    return CheckIfWin();
                }
            }
        }
        return false;
    }
}
