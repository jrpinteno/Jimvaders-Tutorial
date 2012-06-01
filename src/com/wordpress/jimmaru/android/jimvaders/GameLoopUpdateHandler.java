package com.wordpress.jimmaru.android.jimvaders;

import org.anddev.andengine.engine.handler.IUpdateHandler;

public class GameLoopUpdateHandler implements IUpdateHandler {

	@Override
	public void onUpdate(float arg0) {
		// not a good practice, but a quick and dirty way for main game loop
//		((GameScene) MainActivity.getSharedInstance().mMainScne).moveShip();
		((GameScene) MainActivity.getSharedInstance().mMainScne).cleaner();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

}
