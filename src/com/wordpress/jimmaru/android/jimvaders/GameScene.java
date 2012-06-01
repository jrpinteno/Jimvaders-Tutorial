package com.wordpress.jimmaru.android.jimvaders;

import java.util.Iterator;
import java.util.LinkedList;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;

//Main game scene class that have all the game objects on it

public class GameScene extends Scene implements IOnSceneTouchListener {

	public Ship ship;
	public float accellerometerSpeedX;
	Camera mCamera;
	SensorManager sensorManager;
	public LinkedList<Bullet> bulletList;
	public int missCount;
	public int bulletCount;

	public GameScene() {
		setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		// attaching an EnemyLayer entity with 12 enemies on it
		attachChild(new EnemyLayer(12));
		bulletList = new LinkedList<Bullet>();
		mCamera = MainActivity.getSharedInstance().mCamera;

		setOnSceneTouchListener(this);

		ship = new Ship();
		attachChild(ship.sprite);

		MainActivity.getSharedInstance().setMainScene(this);

		sensorManager = (SensorManager) MainActivity.getSharedInstance()
				.getSystemService(BaseGameActivity.SENSOR_SERVICE);

		new SensorListener();

		sensorManager.registerListener(SensorListener.getSharedInstance(),
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);

		resetValues();

	}

	// method to reset values and restart the game
	public void resetValues() {
		missCount = 0;
		bulletCount = 0;
		ship.restart();
		EnemyLayer.purgeAndRestart();
		clearChildScene();
		registerUpdateHandler(new GameLoopUpdateHandler());
	}

	public void moveShip() {
		ship.moveShip(accellerometerSpeedX);
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		if (!CoolDown.sharedCoolDown().checkValidity())
			return false;

		synchronized (this) {
			ship.shoot();
		}
		return true;
	}

	// main object cleaner and collision detection method
	public void cleaner() {
		synchronized (this) {
			// if all Enemies are killed
			if (EnemyLayer.isEmpty()) {
				setChildScene(new ResultScene(mCamera));
				clearUpdateHandlers();
			}
			Iterator<Enemy> eIt = EnemyLayer.getIterator();
			while (eIt.hasNext()) {
				Enemy e = eIt.next();
				Iterator<Bullet> it = bulletList.iterator();
				while (it.hasNext()) {
					Bullet b = it.next();
					if (b.sprite.getY() <= -b.sprite.getHeight()) {
						BulletPool.sharedBulletPool().recyclePoolItem(b);
						it.remove();
						missCount++;
						continue;
					}

					if (b.sprite.collidesWith(e.sprite)) {

						if (!e.gotHit()) {
							EnemyPool.sharedEnemyPool().recyclePoolItem(e);
							eIt.remove();
						}
						BulletPool.sharedBulletPool().recyclePoolItem(b);
						it.remove();
						break;
					}
				}

			}
		}
	}

}
