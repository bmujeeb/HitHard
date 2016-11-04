package com.game.hitnmiss;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Club {
    public static Club instance;

    // Cannon texture Atlas.
    private BitmapTextureAtlas clubTextureAtlas;

    // Cannon texture region.
    private TextureRegion clubTextureRegion;

    // Cannon texture sprite.
    public Sprite clubSprite;

    private Camera mCamera;

    private RotationModifier rotationModifier;

    public static Club getSharedInstance() {
	if (instance == null)
	    instance = new Club();
	return instance;
    }

    private Club() {

	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	clubTextureAtlas = new BitmapTextureAtlas(new TextureManager(), 160, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	clubTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.clubTextureAtlas, BaseActivity.getSharedInstance(),
		"bat.png", 0, 0);

	float play_x = clubTextureRegion.getWidth();
	float play_y = clubTextureRegion.getHeight();

	// click on cannon to launch the bullet.
	clubSprite = new Sprite(play_x, play_y, clubTextureRegion, new VertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

		if (pAreaTouchEvent.isActionDown()) {
		    clubSprite.setRotationCenter(clubSprite.getWidth(), clubSprite.getHeight() / 2);
		    rotationModifier = new RotationModifier(0.5f, 0, -180);
		    clubSprite.registerEntityModifier(new RotationModifier(0.5f, 0, -180));
		}
		return true;
	    }
	};
	mCamera = BaseActivity.getSharedInstance().camera;

	clubSprite.setPosition(5, mCamera.getHeight() / 2 - 60);

	// Important to display texture on the screen.
	BaseActivity.getSharedInstance().getTextureManager().loadTexture(clubTextureAtlas);
    }

    public void resetClub() {
	clubSprite.unregisterEntityModifier(rotationModifier);
	clubSprite.registerEntityModifier(new RotationModifier(0.1f, -180, 0));
    }
}
