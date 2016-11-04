package com.game.hitnmiss;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;
import android.util.DisplayMetrics;

public class BaseActivity extends SimpleBaseGameActivity {

    static int CAMERA_WIDTH;
    static int CAMERA_HEIGHT;

    private DisplayMetrics displaymetrics;

    public Font gameFonts;
    public Font menuFonts;
    public Font helpFonts;
    public Font scoreCardFonts;
    public Camera camera;

    // A reference to the current scene
    public Scene mCurrentScene;
    public static BaseActivity instance;

    public static BaseActivity getSharedInstance() {

	return instance;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
	instance = this;

	displaymetrics = new DisplayMetrics();
	getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

	CAMERA_WIDTH = displaymetrics.widthPixels;
	CAMERA_HEIGHT = displaymetrics.heightPixels;

	camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

	return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    protected void onCreateResources() {
	gameFonts = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
		Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
	gameFonts.load();
	scoreCardFonts = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
		Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20);
	scoreCardFonts.load();
	menuFonts = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
		Typeface.create(Typeface.SERIF, Typeface.NORMAL), 48);
	menuFonts.load();
	helpFonts = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256,
		Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 26);
	helpFonts.load();
    }

    @Override
    protected Scene onCreateScene() {
	mEngine.registerUpdateHandler(new FPSLogger());
	mCurrentScene = new SplashScene();
	return mCurrentScene;
    }

    // to change the current main scene
    public void setCurrentScene(Scene scene) {
	mCurrentScene = null;
	mCurrentScene = scene;
	getEngine().setScene(mCurrentScene);

    }

    @Override
    public void onBackPressed() {
	if (mCurrentScene instanceof GameScene)
	    ((GameScene) mCurrentScene).detach();
	mCurrentScene = null;
	finish();
    }

}