package com.game.hitnmiss;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

//placeHolder scene class for the main menu, currently only includes a start menu item 
public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener, OnKeyListener {
    private BaseActivity activity;
    private final int MENU_START = 0;
    private final int MENU_HELP = 1;
    private final int MENU_ABOUT = 2;

    private BitmapTextureAtlas menuBgTexture;
    private TextureRegion menuBgTextureRegion;
    private SpriteBackground menuBgSprite;

    public MainMenuScene() {
	super(BaseActivity.getSharedInstance().camera);
	activity = BaseActivity.getSharedInstance();

	loadBackground();

	IMenuItem startButton = new TextMenuItem(MENU_START, activity.menuFonts, activity.getString(R.string.start), activity.getVertexBufferObjectManager());
	startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth() / 2, mCamera.getHeight() / 2 - startButton.getHeight());

	IMenuItem helpButton = new TextMenuItem(MENU_HELP, activity.menuFonts, activity.getString(R.string.help), activity.getVertexBufferObjectManager());
	helpButton.setPosition(mCamera.getWidth() / 2 - helpButton.getWidth() / 2, mCamera.getHeight() / 2);

	IMenuItem aboutButton = new TextMenuItem(MENU_ABOUT, activity.menuFonts, activity.getString(R.string.about), activity.getVertexBufferObjectManager());
	aboutButton.setPosition(mCamera.getWidth() / 2 - aboutButton.getWidth() / 2, mCamera.getHeight() / 2 + aboutButton.getHeight());

	addMenuItem(startButton);
	addMenuItem(helpButton);
	addMenuItem(aboutButton);

	setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
	switch (arg1.getID()) {
	case MENU_START:
	    activity.setCurrentScene(new GameScene());
	    return true;
	case MENU_HELP:
	    activity.setCurrentScene(new GameHelp(BaseActivity.getSharedInstance().camera));
	    return true;
	case MENU_ABOUT:
	    return true;
	default:
	    break;
	}
	return false;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
	if (KeyEvent.KEYCODE_BACK == keyCode) {
	    BaseActivity.getSharedInstance().finish();
	}
	return false;
    }

    private void loadBackground() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	menuBgTexture = new BitmapTextureAtlas(new TextureManager(), 1200, 1200, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	menuBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBgTexture, BaseActivity.getSharedInstance(), "menu_screen.png", 0, 0);
	menuBgSprite = new SpriteBackground(new Sprite(0, 0, menuBgTextureRegion, new VertexBufferObjectManager()));
	BaseActivity.getSharedInstance().getTextureManager().loadTexture(menuBgTexture);
	this.setBackground(menuBgSprite);
    }
}
