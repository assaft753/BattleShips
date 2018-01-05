package com.moshesteinvortzel.assaftayouri.battleships.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.moshesteinvortzel.assaftayouri.battleships.CubeView;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Core.BattleShip;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Core.Board;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.CubeType;
import com.moshesteinvortzel.assaftayouri.battleships.R;

/**
 * Created by assaftayouri on 10/12/2017.
 */

public class ComputerGridAdapter extends BaseAdapter
{
    private Board board;
    private Context context;
    private int h;

    public ComputerGridAdapter(Board board, int h, Context context)
    {
        this.board = board;
        this.context = context;
        this.h = h;
    }

    public int getCount()
    {
        return BattleShip.SIZE * BattleShip.SIZE;
    }

    @Override
    public Object getItem(int position)
    {
        return this.board.getCube(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CubeView cubeView;
        if (convertView == null)
        {
            cubeView = new CubeView(this.context, this.h);
        } else
        {
            cubeView = (CubeView) convertView;
        }

        cubeView.setBackgroundColor(View.GONE);
        cubeView.cube.setImageResource(0);
        if (this.board.getCube(position).getStatus() == CubeType.None)
        {
            cubeView.setBackgroundColor(Color.LTGRAY);
        } else
        {
            if (board.getCube(position).getStatus() == CubeType.Hit)
            {
                cubeView.cube.setImageResource(R.drawable.hit);
            } else if (board.getCube(position).getStatus() == CubeType.Miss)
            {
                cubeView.setBackgroundColor(0x92485BD2);
            } else if (board.getCube(position).getStatus() == CubeType.Sink)
            {
                cubeView.cube.setImageResource(R.drawable.sink);
            }

        }
        return cubeView;
    }
}
