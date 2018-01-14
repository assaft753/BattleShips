package com.moshesteinvortzel.assaftayouri.battleships;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.moshesteinvortzel.assaftayouri.battleships.Adapters.RecordDecorator;
import com.moshesteinvortzel.assaftayouri.battleships.Adapters.RecordsListAdapter;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Record;

import java.util.ArrayList;

/**
 * Created by assaftayouri on 12/01/2018.
 */

public class RecordsTableFragment extends Fragment
{
    public interface IRecordTableClickable
    {
        public void OnRecordTableClick(Record record);
    }

    private ListView recordsList;
    public ArrayList<RecordDecorator> records;
    public IRecordTableClickable onRecordTableClick;

    public RecordsTableFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.record_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        recordsList = (ListView) getView().findViewById(R.id.records_list);
        recordsList.setAdapter(new RecordsListAdapter(getActivity().getApplicationContext(), records));
        recordsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                view.getFocusables(i);
                view.setSelected(true);
                onRecordTableClick.OnRecordTableClick(records.get(i).record);
                System.out.println("enter");
            }
        });


    }

    public void SetRecordHighlight(Record record)
    {
        for (int i = 0; i < records.size(); i++)
        {

            if (records.get(i).IfRecordEquals(record))
            {
                recordsList.requestFocusFromTouch();
                recordsList.smoothScrollToPosition(i);
                recordsList.setSelection(i);
                recordsList.performItemClick(recordsList.getAdapter().getView(i, null, null), i, i);
                //((RecordsListAdapter)recordsList.getAdapter()).notifyDataSetChanged();
                break;
            }

        }
    }

    public void InitRecords(ArrayList<Record> records)
    {
        ArrayList<RecordDecorator> recordsDecorator=new ArrayList<RecordDecorator>();
        for(Record record:records)
        {
            recordsDecorator.add(new RecordDecorator(record));
        }
        this.records=recordsDecorator;
    }
}


