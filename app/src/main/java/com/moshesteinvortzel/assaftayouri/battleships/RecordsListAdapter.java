package com.moshesteinvortzel.assaftayouri.battleships;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.TokenWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.moshesteinvortzel.assaftayouri.battleships.Adapters.RecordDecorator;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Record;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by assaftayouri on 12/01/2018.
 */

public class RecordsListAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<RecordDecorator> records;

    public RecordsListAdapter(Context context, ArrayList<RecordDecorator> records)
    {
        this.records = records;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return records.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        TextView rank;
        TextView playerName;
        TextView score;
        TextView location;
        View row;
        String locationString;
        if (view == null)
        {
            row = mInflater.inflate(R.layout.record_list_element, viewGroup, false);
        }

        else
        {
            row = view;
        }

        rank = (TextView) row.findViewById(R.id.rank);
        playerName = (TextView) row.findViewById(R.id.player_name);
        score = (TextView) row.findViewById(R.id.score);
        location = (TextView) row.findViewById(R.id.location);

        if (records.get(i).toFill)
        {
            //row.getBackground().setAlpha(255);
        }
        else
        {
            //row.getBackground().setAlpha(0);
        }

        rank.setText(i + 1 + ".");
        playerName.setText(records.get(i).record.playerName);
        score.setText(String.valueOf(records.get(i).record.score));
        locationString = "Lat:" + String.valueOf(records.get(i).record.location.latitude)
                + "\n" + "long:" + String.valueOf(records.get(i).record.location.longitude);
        location.setText(locationString);
        return row;
    }
}
