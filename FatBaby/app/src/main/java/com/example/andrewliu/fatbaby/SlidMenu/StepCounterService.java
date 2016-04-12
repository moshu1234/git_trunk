package com.example.andrewliu.fatbaby.SlidMenu;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;


public class StepCounterService extends Service {

    public static Boolean FLAG = false;

    private SensorManager mSensorManager;
    private StepDetector detector;

    private PowerManager mPowerManager;
    private WakeLock mWakeLock;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        FLAG = true;
        Log.e("aaaaa","new step detector");
        detector = new StepDetector(this);

        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(detector,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        mPowerManager = (PowerManager) this
                .getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "S");
        mWakeLock.acquire();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.e("aaaaa","stop service");
        FLAG = false;
        if (detector != null) {
            mSensorManager.unregisterListener(detector);
        }

        if (mWakeLock != null) {
            mWakeLock.release();
        }
    }

}
