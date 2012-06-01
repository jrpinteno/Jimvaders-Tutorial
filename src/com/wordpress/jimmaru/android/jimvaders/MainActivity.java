package com.wordpress.jimmaru.android.jimvaders;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class MainActivity extends BaseGameActivity {
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;

	private BitmapTextureAtlas mFontTexture;

	public PhysicsWorld mPhysicsWorld;
	public Font mFont;
	public Camera mCamera;
	public Scene mMainScne;
	public static MainActivity instance;
	public Engine mEngine;

	public static final FixtureDef FIXTURE_DEF = PhysicsFactory
			.createFixtureDef(1, 0.5f, 0.5f);

	public static MainActivity getSharedInstance() {
		return instance;

	}
	
	@Override
	public Engine onLoadEngine() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		mEngine = new Engine(new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));

		instance = this;

		return mEngine;
	}

	@Override
	public void onLoadResources() {

		mFontTexture = new BitmapTextureAtlas(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mFont = new Font(mFontTexture, Typeface.create(Typeface.DEFAULT,
				Typeface.BOLD), 32, true, Color.BLACK);

		mEngine.getTextureManager().loadTexture(mFontTexture);
		mEngine.getFontManager().loadFont(mFont);
		// enableAccelerometerSensor(new AccelerometerListener());

	}

	@Override
	public Scene onLoadScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		mPhysicsWorld = new PhysicsWorld(new Vector2(0,
				SensorManager.GRAVITY_EARTH), false);

		// loading the splash screen/scene
		mMainScne = new SplashScene();
		return mMainScne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

	public void setMainScene(Scene scene) {
		mMainScne = scene;
	}

}