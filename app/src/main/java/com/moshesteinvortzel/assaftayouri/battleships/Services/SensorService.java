package com.moshesteinvortzel.assaftayouri.battleships.Services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SensorService extends Service implements SensorEventListener
{
    private static final float THRESHOLDG = 3;
    private static final float THRESHOLDM = 15;
    private float[] initialGravity;
    private float[] initialMagnetic;
    float[] mGravity = null;
    float[] mGeomagnetic = null;
    private boolean isFirstTime;
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private IsensorChangable isensorChangable;

    public SensorService()
    {
        isFirstTime = true;
        initialGravity = new float[3];
        initialMagnetic = new float[3];
    }

    public interface IsensorChangable
    {
        public void StartAnimationBySensor();

        public void StopAnimationBySensor();

    }

    @Override
    public IBinder onBind(Intent intent)
    {
        mSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null && mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null)
        {
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
        else
        {
            return null;
        }
        return new SensorApi();

    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        this.mSensorManager.unregisterListener(this);
        return super.onUnbind(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            mGravity = sensorEvent.values;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            mGeomagnetic = sensorEvent.values;
        }
        if (mGravity != null && mGeomagnetic != null)
        {
            if (isFirstTime)
            {
                isFirstTime = false;
                initialGravity[0] = mGravity[0];
                initialGravity[1] = mGravity[1];
                initialGravity[2] = mGravity[2];
                initialMagnetic[0] = mGeomagnetic[0];
                initialMagnetic[1] = mGeomagnetic[1];
                initialMagnetic[2] = mGeomagnetic[2];
            }
            else
            {
                if (CheckOrientation(initialGravity, mGravity, initialMagnetic, mGeomagnetic))
                {

                    isensorChangable.StartAnimationBySensor();
                }
                else
                {
                    isensorChangable.StopAnimationBySensor();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    public class SensorApi extends Binder
    {
        public void Init(IsensorChangable isensorChangable)
        {
            SensorService.this.Init(isensorChangable);
        }

    }

    private void Init(IsensorChangable isensorChangable)
    {
        this.isensorChangable = isensorChangable;
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private boolean CheckOrientation(float[] initialGravity, float[] currentGravity, float[] initialMagnetic, float[] currentMagnetic)
    {
        float xG = Math.abs(initialGravity[0] - currentGravity[0]);
        float yG = Math.abs(initialGravity[1] - currentGravity[1]);
        float zG = Math.abs(initialGravity[2] - currentGravity[2]);
        float xM = Math.abs(initialMagnetic[0] - currentMagnetic[0]);
        float yM = Math.abs(initialMagnetic[1] - currentMagnetic[1]);
        float zM = Math.abs(initialMagnetic[2] - currentMagnetic[2]);
        //System.out.println("x=" + x + " y=" + y + " z=" + z + " threshold=" + threshold);

        if (xG > THRESHOLDG || yG > THRESHOLDG || xM > THRESHOLDM || yM > THRESHOLDM || zM > THRESHOLDM)
        {
            return true;
        }
        return false;
    }
}
