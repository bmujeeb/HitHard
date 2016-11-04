package com.game.hitnmiss;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Singleton class that creates the display object and its characteristics of
 * cannon that will throw the ball.
 * 
 * @author bilal
 * 
 */
public class Cannon {
    public static Cannon instance;

    // Cannon texture Atlas.
    private BitmapTextureAtlas cannonTextureAtlas;

    // Cannon texture region.
    private TextureRegion cannonTextureRegion;

    // Cannon texture sprite.
    public Sprite cannonSprite;

    public MoveXModifier mod;

    Camera mCamera;

    public static Cannon getSharedInstance() {
	if (instance == null)
	    instance = new Cannon();
	return instance;
    }

    private Cannon() {

	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	this.cannonTextureAtlas = new BitmapTextureAtlas(new TextureManager(), 100, 100, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	this.cannonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.cannonTextureAtlas, BaseActivity.getSharedInstance(),
		"cannon.png", 0, 0);

	float play_x = cannonTextureRegion.getWidth();
	float play_y = cannonTextureRegion.getHeight();

	// click on cannon to launch the bullet.
	cannonSprite = new Sprite(play_x, play_y, cannonTextureRegion, new VertexBufferObjectManager()) {
	    @Override
	    public boolean onAreaTouched(final TouchEvent pAreaTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if (!CoolDown.getSharedInstance().checkValidity())
		    return false;
		synchronized (this) {
		    shoot();
		}
		return true;
	    }
	};
	mCamera = BaseActivity.getSharedInstance().camera;

	cannonSprite.setPosition(mCamera.getWidth() - cannonSprite.getWidth(), mCamera.getHeight() / 2 - (cannonSprite.getHeight() / 2));

	// Important to display texture on the screen.
	BaseActivity.getSharedInstance().getTextureManager().loadTexture(cannonTextureAtlas);

    }

    // shoots bullets
    public void shoot() {
	GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;

	BaseBall b = BaseballPool.sharedBulletPool().obtainPoolItem();
	b.baseballSprite.setPosition(cannonSprite.getX() + cannonSprite.getWidth() / 2, cannonSprite.getY());
	mod = new MoveXModifier(2.0f, mCamera.getWidth() - cannonSprite.getWidth() - b.baseballSprite.getWidth(), -b.baseballSprite.getWidth());

	b.baseballSprite.setVisible(true);
	b.baseballSprite.detachSelf();
	scene.attachChild(b.baseballSprite);
	scene._baseballList.add(b);
	b.baseballSprite.registerEntityModifier(mod);

	scene._bulletCount++;
    }

}
