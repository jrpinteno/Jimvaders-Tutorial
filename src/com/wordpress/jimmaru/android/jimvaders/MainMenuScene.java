package com.wordpress.jimmaru.android.jimvaders;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.TextMenuItem;

//placeHolder scene class for the main menu, currently only includes a start menu item 
public class MainMenuScene extends MenuScene implements
		IOnMenuItemClickListener {
	final int MENU_START = 0;

	public MainMenuScene() {
		super(MainActivity.getSharedInstance().mCamera);
		setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		IMenuItem startButton = new TextMenuItem(MENU_START, MainActivity
				.getSharedInstance().mFont, "Start");
		startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth()
				/ 2, mCamera.getHeight() / 2 - startButton.getHeight() / 2);
		startButton.setBlendFunction(GL10.GL_SRC_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		addMenuItem(startButton);

		setOnMenuItemClickListener(this);
		MainActivity.getSharedInstance().mMainScne = this;
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
			float arg2, float arg3) {
		switch (arg1.getID()) {
		case MENU_START:
			MainActivity.getSharedInstance().mEngine.setScene(new GameScene());
			return true;
		default:
			break;
		}
		return false;
	}

}
