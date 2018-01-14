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
    private float threshold;
    private float[] initialOrientaion;
    float[] mGravity = null;
    float[] mGeomagnetic = null;
    private boolean isFirstTime;
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private IsensorChangable isensorChangable;

    public SensorService()
    {
        isFirstTime = false;
        threshold = (float) 1.5;
        initialOrientaion = new float[3];
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
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null && mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                if (isFirstTime == false)
                {
                    isFirstTime = true;
                    initialOrientaion[0] = orientation[0];
                    initialOrientaion[1] = orientation[1];
                    initialOrientaion[2] = orientation[2];
                    //threshold=(initialOrientaion[0]+initialOrientaion[1]+initialOrientaion[2])/3;
                    System.out.println(initialOrientaion[0] + "!!!!!!!!!!!!!!!!!!!");
                    System.out.println(initialOrientaion[1] + "!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(initialOrientaion[2] + "!!!!!!!!!!!!!!!!!!!!!!");
                }
                else
                {
                    if (CheckOrientation(initialOrientaion, orientation))
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
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private boolean CheckOrientation(float[] initialOrientaion, float[] current)
    {
        //System.out.println((Math.toDegrees(current[0])));
        //System.out.println((Math.toDegrees(current[1])));
        //System.out.println((Math.toDegrees(current[2])));

        float x = Math.abs(initialOrientaion[0] - current[0]);
        float y = Math.abs(initialOrientaion[1] - current[1]);
        float z = Math.abs(initialOrientaion[2] - current[2]);
        //System.out.println("x="+x+" y="+y+" z="+z+" threshold="+threshold);

        if (x > threshold || y > threshold || z > threshold)
        {
            return true;
        }
        return false;
    }


}
