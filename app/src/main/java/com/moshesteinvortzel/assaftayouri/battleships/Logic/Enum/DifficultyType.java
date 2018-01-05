package com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Core.BattleShip;

/**
 * Created by assaftayouri on 04/12/2017.
 */
public enum DifficultyType {
    Easy, Medium, Hard;

    public int getDifficultyAsNumber() {
        switch (this) {
            case Easy:
                return BattleShip.EASY;
            case Medium:
                return BattleShip.MEDIUM;
            case Hard:
                return BattleShip.HARD;
            default:
                return BattleShip.EASY;
        }
    }
}
