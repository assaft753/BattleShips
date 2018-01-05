package com.moshesteinvortzel.assaftayouri.battleships;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by assaftayouri on 10/12/2017.
 */

public class CubeView extends LinearLayout
{
    public ImageView cube;

    public CubeView(Context context, int h)
    {
        super(context);
        this.setOrientation(VERTICAL);
        cube = new ImageView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        cube.setLayoutParams(layoutParams);
        addView(cube);
    }

}
