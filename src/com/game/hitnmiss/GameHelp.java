package com.game.hitnmiss;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class GameHelp extends CameraScene {

    BaseActivity activity;

    private BitmapTextureAtlas helpBgTexture;
    private TextureRegion helpBgTextureRegion;
    private SpriteBackground helpBgSprite;

    private String helpText = "The game is simple to play, touch cannon to throw \nthe ball & touch the bat to hit the ball in the direction \nof target.";

    public GameHelp(Camera camera) {
	super(camera);
	activity = BaseActivity.getSharedInstance();
	loadBackground();

	Text result = new Text(0, 0, activity.helpFonts, helpText, BaseActivity.getSharedInstance().getVertexBufferObjectManager());

	result.setPosition(camera.getWidth() / 5, camera.getHeight() / 3);
	attachChild(result);
    }

    private void loadBackground() {

	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	helpBgTexture = new BitmapTextureAtlas(new TextureManager(), 1200, 1200, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	helpBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(helpBgTexture, BaseActivity.getSharedInstance(), "help_screen.png", 0, 0);
	helpBgSprite = new SpriteBackground(new Sprite(0, 0, helpBgTextureRegion, new VertexBufferObjectManager()));
	BaseActivity.getSharedInstance().getTextureManager().loadTexture(helpBgTexture);
	this.setBackground(helpBgSprite);
    }

}
