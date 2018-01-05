package com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary;

/**
 * Created by assaftayouri on 04/12/2017.
 */

public class HorizontalPosition extends Position {

    private int RowNum;

    public HorizontalPosition(int start, int end, int rowNum) {
        super(start, end);
        this.RowNum = rowNum;
    }

    public int getRowNum() {
        return this.RowNum;
    }

    @Override
    public String toString() {
        return "HorizontalPosition{" +
                "RowNum=" + RowNum + " " + super.toString() + " " +
                '}';
    }
}
