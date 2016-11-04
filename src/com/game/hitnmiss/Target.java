package com.game.hitnmiss;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Target {

    private BitmapTextureAtlas targetTextureAtlas;
    private TextureRegion targetTextureRegion;
    public Sprite targetSprite;

    public int hp;
    // the max health for each enemy
    protected final int MAX_HEALTH = 1;

    public Target() {

	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	this.targetTextureAtlas = new BitmapTextureAtlas(new TextureManager(), 64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	this.targetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.targetTextureAtlas,
		BaseActivity.getSharedInstance(), "target.png", 0, 0);

	float play_x = targetTextureRegion.getWidth();
	float play_y = targetTextureRegion.getHeight();
	targetSprite = new Sprite(play_x, play_y, targetTextureRegion, new VertexBufferObjectManager());

	init();
    }

    // method for initializing the Enemy object , used by the constructor and
    // the EnemyPool class
    public void init() {
	hp = MAX_HEALTH;
	// Load Texture
	BaseActivity.getSharedInstance().getTextureManager().loadTexture(targetTextureAtlas);
    }

    public void clean() {
	targetSprite.clearEntityModifiers();
	targetSprite.clearUpdateHandlers();
    }

    // method for applying hit and checking if enemy died or not
    // returns false if enemy died
    public boolean gotHit() {
	synchronized (this) {
	    hp--;
	    if (hp <= 0)
		return false;
	    else
		return true;
	}
    }
}
