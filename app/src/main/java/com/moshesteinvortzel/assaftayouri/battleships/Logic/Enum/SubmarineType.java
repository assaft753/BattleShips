package com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum;

import com.moshesteinvortzel.assaftayouri.battleships.R;

/**
 * Created by assaftayouri on 10/12/2017.
 */

public enum SubmarineType
{
    End, Start, Middle;

    public int getIdOfImage(ShipType shipType)
    {
        if (shipType == ShipType.Horizontal)
        {
            switch (this)
            {
                case Start:
                    return R.drawable.starthorizontal;
                case Middle:
                    return R.drawable.middlehorizontal;
                case End:
                    return R.drawable.endhorizontal;
            }

        }
        else
        {
            switch (this)
            {
                case Start:
                    return R.drawable.startvertical;
                case Middle:
                    return R.drawable.middlevertical;
                case End:
                    return R.drawable.endvertical;
            }

        }
        return 0;
    }
}
