package com.wordpress.jimmaru.android.jimvaders;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.input.touch.TouchEvent;

//an overlay scene that appears at the end of a round calculating and showing the player's accuracy
public class ResultScene extends CameraScene implements IOnSceneTouchListener {
	boolean done;

	public ResultScene(Camera mCamera) {
		super(mCamera);
		setBackgroundEnabled(false);
		GameScene scene = (GameScene) MainActivity.getSharedInstance().mMainScne;
		float accurecy = 1 - (float) scene.missCount / scene.bulletCount;
		if (Float.isNaN(accurecy))
			accurecy = 0;
		accurecy *= 100;
		Text result = new Text(0, 0, MainActivity.getSharedInstance().mFont,
				"Accurecy: " + String.format("%.2f", accurecy) + "%");

		final int x = (int) (mCamera.getWidth() / 2 - result.getWidth() / 2);
		final int y = (int) (mCamera.getHeight() / 2 - result.getHeight() / 2);

		done = false;
		result.setPosition(x, mCamera.getHeight() + result.getHeight());
		MoveYModifier mod = new MoveYModifier(5, result.getY(), y) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				done = true;
			}
		};
		attachChild(result);
		result.registerEntityModifier(mod);
		setOnSceneTouchListener(this);
	}

	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		if (!done)
			return true;
		((GameScene) MainActivity.getSharedInstance().mMainScne).resetValues();
		return true;
	}

}
