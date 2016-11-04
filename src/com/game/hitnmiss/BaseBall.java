package com.game.hitnmiss;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BaseBall {

    private BitmapTextureAtlas baseballTextureAtlas;
    private TextureRegion baseballTextureRegion;
    public Sprite baseballSprite;

    public BaseBall() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	baseballTextureAtlas = new BitmapTextureAtlas(new TextureManager(), 50, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

	baseballTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(baseballTextureAtlas, BaseActivity.getSharedInstance(), "baseball.png",
		0, 0);

	float play_x = baseballTextureRegion.getWidth();
	float play_y = baseballTextureRegion.getHeight();
	baseballSprite = new Sprite(play_x, play_y, baseballTextureRegion, new VertexBufferObjectManager());

	BaseActivity.getSharedInstance().getTextureManager().loadTexture(baseballTextureAtlas);

    }
}
