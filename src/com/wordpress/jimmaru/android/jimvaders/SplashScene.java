package com.wordpress.jimmaru.android.jimvaders;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.DelayModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.util.modifier.IModifier;

//a place holder scene for splash screen, currently only shows a green background scene
public class SplashScene extends Scene {
	public SplashScene() {
		setBackground(new ColorBackground(0.09804f, 0.6274f, 0));
		DelayModifier dMod = new DelayModifier(2,
				new IEntityModifierListener() {

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
						MainActivity.getSharedInstance().getEngine().setScene(
								new MainMenuScene());
					}
				});

		registerEntityModifier(dMod);
	}

}
