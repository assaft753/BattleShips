package com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary;

/**
 * Created by assaftayouri on 04/12/2017.
 */

public class Position {
    private int Start;
    private int End;

    public Position(int start, int end) {
        this.Start = start;
        this.End = end;
    }

    public int getStart() {
        return this.Start;
    }

    public int getEnd() {
        return this.End;
    }

    @Override
    public String toString() {
        return "Position{" +
                "Start=" + Start +
                ", End=" + End +
                '}';
    }
}

