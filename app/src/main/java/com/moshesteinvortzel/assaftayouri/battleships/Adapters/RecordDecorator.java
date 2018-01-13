package com.moshesteinvortzel.assaftayouri.battleships.Adapters;

import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Record;

/**
 * Created by assaftayouri on 13/01/2018.
 */

public class RecordDecorator
{
    public Record record;
    public Boolean toFill;
    public RecordDecorator(Record record)
    {
        this.record=record;
        this.toFill=false;
    }

    public boolean IfRecordEquals(Record record)
    {
        return this.record.equals(record);
    }

}
