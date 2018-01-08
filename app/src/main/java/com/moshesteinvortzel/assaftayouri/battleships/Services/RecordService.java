package com.moshesteinvortzel.assaftayouri.battleships.Services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Enum.DifficultyType;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.SQL.RecordHandler;
import com.moshesteinvortzel.assaftayouri.battleships.Logic.Secondary.Record;

import java.io.StreamCorruptedException;

public class RecordService extends Service implements LocationListener, Runnable
{
    private RecordHandler recordHandler;
    private final IBinder apiRecord;
    private RecordApi recordApi;
    private LocationManager locationManager;
    private String playerName;
    private int playerScore;
    private DifficultyType playerDifficultyType;
    private Location location;

    public RecordService()
    {
        super();
        apiRecord = new RecordApi();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        if (recordHandler == null)
        {
            recordHandler = new RecordHandler(getApplicationContext());
        }
        if (locationManager == null)
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        return apiRecord;
    }


    @Override
    public void onLocationChanged(Location location)
    {
        locationManager.removeUpdates(this);
        this.location = location;
        new Thread(this).start();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }

    @Override
    public void onProviderEnabled(String s)
    {

    }

    @Override
    public void onProviderDisabled(String s)
    {

    }

    @Override
    public void run()
    {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        recordHandler.InsertRecords(playerDifficultyType, new Record(latLng, playerScore, playerName));
        System.out.println(location.toString() + " name " + playerName + " score " + playerScore);
        recordHandler.CloseSQl();
    }


    public class RecordApi extends Binder
    {
        public void InsertRecord(String name, int score, DifficultyType difficultyType)
        {
            RecordService.this.InsertRecord(name, score, difficultyType);
        }
    }

    public void InsertRecord(String name, int score, DifficultyType difficultyType)
    {
        playerName = name;
        playerScore = score;
        playerDifficultyType = difficultyType;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }


    }
}
