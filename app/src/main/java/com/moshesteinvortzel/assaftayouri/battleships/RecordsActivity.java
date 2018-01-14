package com.moshesteinvortzel.assaftayouri.battleships;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moshesteinvortzel.assaftayouri.battleships.Adapters.RecordDecorator;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL.RecordHandler;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Position;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Record;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity implements OnMapReadyCallback, RecordsTableFragment.IRecordTableClickable, GoogleMap.OnMarkerClickListener
{
    private GoogleMap mMap;
    private RecordHandler recordHandler;
    private ArrayList<Record> records;
    private SupportMapFragment mapFragment;
    private RecordsTableFragment recordsTableFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Bundle bundle = getIntent().getExtras();
        recordHandler = new RecordHandler(getApplicationContext());
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        records = recordHandler.GetRecords(DifficultyType.values()[bundle.getInt(getString(R.string.keyDifficulty))]);
        mapFragment.getMapAsync(this);
        recordsTableFragment = (RecordsTableFragment) getFragmentManager().findFragmentById(R.id.records_fragment);
        recordsTableFragment.InitRecords(records);
        recordsTableFragment.onRecordTableClick = this;

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        for (Record record : records)
        {
            mMap.addMarker(new MarkerOptions().position(record.location).title(record.playerName)).setTag(record);
        }

    }

    @Override
    public void OnRecordTableClick(Record record)
    {
        mMap.animateCamera(CameraUpdateFactory.newLatLng(record.location));
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        recordsTableFragment.SetRecordHighlight((Record) marker.getTag());
        return true;
    }
}
