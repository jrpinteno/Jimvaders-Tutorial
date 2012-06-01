package com.wordpress.jimmaru.android.jimvaders;

import junit.framework.Assert;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveXModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.shape.Shape;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

//ship class , main playing object
public class Ship {

	public Shape sprite;
	public Body body;
	private boolean moveable;
	public static Ship instance;

	public static Ship getSharedInstance() {
		if (instance == null)
			instance = new Ship();
		return instance;
	}

	public Ship() {

		sprite = new Rectangle(0, 0, 70, 30);
		body = PhysicsFactory.createBoxBody(
				MainActivity.getSharedInstance().mPhysicsWorld, sprite,
				BodyType.DynamicBody,
				MainActivity.getSharedInstance().FIXTURE_DEF);
		

		Camera mCamera = MainActivity.getSharedInstance().mCamera;
		sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
				mCamera.getHeight() - sprite.getHeight() - 10);
		moveable = true;

		instance = this;
	}

	// method responsible for moving the ship on screen
	public void moveShip(float accellerometerSpeedX) {
		if (!moveable)
			return;
		Camera mCamera = MainActivity.getSharedInstance().mCamera;

		if (accellerometerSpeedX != 0) {

			int lL = 0;
			int rL = (int) (mCamera.getWidth() - (int) sprite.getWidth());

			float newX;

			// Calculate New X,Y Coordinates within Limits
			if (sprite.getX() >= lL)
				newX = sprite.getX() + accellerometerSpeedX;
			else
				newX = lL;
			if (newX <= rL)
				newX = sprite.getX() + accellerometerSpeedX;
			else
				newX = rL;

			// Double Check That New X,Y Coordinates are within Limits
			if (newX < lL)
				newX = lL;
			else if (newX > rL)
				newX = rL;

			sprite.setPosition(newX, sprite.getY());
		}
	}

	// shoots bullets
	public void shoot() {
		Scene scene = MainActivity.getSharedInstance().mMainScne;

		// check if gameScene is active or not
		Assert.assertEquals("Not on GameScene yet", true,
				scene instanceof GameScene);

		Bullet b = BulletPool.sharedBulletPool().obtainPoolItem();
		b.sprite.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite
				.getY());
		MoveYModifier mod = new MoveYModifier(1.5f, b.sprite.getY(),
				0 - b.sprite.getHeight());

		b.sprite.setVisible(true);
		b.sprite.detachSelf();
		scene.attachChild(b.sprite, 1);
		((GameScene) scene).bulletList.add(b);
		b.sprite.registerEntityModifier(mod);

		((GameScene) scene).bulletCount++;
	}

	// resets the ship to the middle of the screen
	public void restart() {
		moveable = false;
		Camera mCamera = MainActivity.getSharedInstance().mCamera;
		MoveXModifier mod = new MoveXModifier(0.2f, sprite.getX(), mCamera
				.getWidth()
				/ 2 - sprite.getWidth() / 2) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				super.onModifierFinished(pItem);
				moveable = true;
			}
		};
		sprite.clearEntityModifiers();
		sprite.registerEntityModifier(mod);

	}
}
