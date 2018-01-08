package com.moshesteinvortzel.assaftayouri.battleships.Logic.Core;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;

import com.google.android.gms.maps.model.LatLng;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.PlayerType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.CubeType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL.RecordHandler;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL.SQL_Handler;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Position;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by assaftayouri on 04/12/2017.
 */

public class BattleShip
{
    public static final int EASY = 5;
    public static final int MEDIUM = 4;
    public static final int HARD = 3;
    public static final int SIZE = 9;
    public static final int AMOUNT = 4;

    private Board PlayerBoard;

    private Board ComputerBoard;
    private HashMap<PlayerType, Board> BoardByTurn;
    private DifficultyType difficultyType;
    public PlayerType Turn;

    public BattleShip(DifficultyType diff, Context context)
    {
        this.difficultyType = diff;
        this.PlayerBoard = new Board(diff);
        this.ComputerBoard = new Board(diff);
        this.BoardByTurn = new HashMap<PlayerType, Board>();
        this.BoardByTurn.put(PlayerType.Player, this.ComputerBoard);
        this.BoardByTurn.put(PlayerType.Computer, this.PlayerBoard);
        Turn = PlayerType.Player;
    }

    public DifficultyType getDifficultyType()
    {
        return this.difficultyType;
    }

    private Boolean Play(int position, PlayerType turn)
    {
        int x = position / BattleShip.SIZE;
        int y = position % BattleShip.SIZE;
        CubeType cy;
        boolean win = false;
        Board currentBoard = this.BoardByTurn.get(turn);
        cy = currentBoard.Shoot(x, y);
        if (cy == CubeType.Hit || cy == CubeType.Miss)
        {

            if (cy == CubeType.Hit)
            {
                return currentBoard.CheckIfWin();
            }
        }
        return false;
    }

    public Boolean playerShoot(int pos)
    {
        Turn = PlayerType.Player;
        return Play(pos, PlayerType.Player);
    }

    public void ToggleTurn()
    {
        if (this.Turn == PlayerType.Computer)
        {
            this.Turn = PlayerType.Player;
        }
        else
        {
            this.Turn = PlayerType.Computer;
        }
    }

    public Board getPlayerBoard()
    {
        return PlayerBoard;
    }

    public Board getComputerBoard()
    {
        return ComputerBoard;
    }

    public Boolean computerShoot()
    {
        computerThink();
        Turn = PlayerType.Computer;
        Random rnd = new Random();
        int pos = rnd.nextInt(BattleShip.SIZE * BattleShip.SIZE);
        while (this.BoardByTurn.get(Turn).checkIfTouched(pos) == true)
        {
            pos = rnd.nextInt(BattleShip.SIZE * BattleShip.SIZE);
        }
        return Play(pos, PlayerType.Computer);
    }

    private void computerThink()
    {
        try
        {
            Random rnd = new Random();
            int x = rnd.nextInt(1500) + 1;
            Thread.sleep(x);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

    public void reArrangeShips()
    {
        this.ComputerBoard.reArrangeShips();
    }

}
