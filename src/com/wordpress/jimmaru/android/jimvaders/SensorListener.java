package com.wordpress.jimmaru.android.jimvaders;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorListener implements SensorEventListener {

	static SensorListener instance;

	public static SensorListener getSharedInstance() {
		if (instance == null)
			instance = new SensorListener();
		return instance;
	}

	public SensorListener() {
		instance = this;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				((GameScene) MainActivity.getSharedInstance().mMainScne).accellerometerSpeedX = event.values[1];
				break;

			default:
				break;
			}
		}

	}

}
