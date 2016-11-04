package com.game.hitnmiss;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;

public class SplashScene extends Scene {
    BaseActivity activity;

    // private BuildableBitmapTextureAtlas backgroundTextureAtlas;
    // private ITextureRegion bgTextureRegion;
    // private SpriteBackground bgSprite;

    private BitmapTextureAtlas backgroundTexture;
    private TextureRegion bgTextureRegion;
    private SpriteBackground bgSprite;

    public SplashScene() {
	activity = BaseActivity.getSharedInstance();
	// backgroundTextureAtlas = new BuildableBitmapTextureAtlas(new
	// TextureManager(), 1000, 1000,
	// TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	// SVGBitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	// bgTextureRegion =
	// SVGBitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTextureAtlas,
	// BaseActivity.getSharedInstance(), "splash.svg",
	// BaseActivity.CAMERA_WIDTH, BaseActivity.CAMERA_WIDTH);
	// bgSprite = new SpriteBackground(new Sprite(0, 0, bgTextureRegion, new
	// VertexBufferObjectManager()));
	//
	// try {
	// backgroundTextureAtlas.build(new
	// BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource,
	// BitmapTextureAtlas>(1, 1, 1));
	// } catch (TextureAtlasBuilderException e) {
	// e.printStackTrace();
	// }
	//
	// BaseActivity.getSharedInstance().getTextureManager().loadTexture(backgroundTextureAtlas);
	// setBackground(bgSprite);

	loadResources();
	loadBackground();
    }

    private void loadBackground() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	backgroundTexture = new BitmapTextureAtlas(new TextureManager(), 1200, 1200, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	bgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundTexture, BaseActivity.getSharedInstance(), "splash.png", 0, 0);
	bgSprite = new SpriteBackground(new Sprite(0, 0, bgTextureRegion, new VertexBufferObjectManager()));
	BaseActivity.getSharedInstance().getTextureManager().loadTexture(backgroundTexture);
	this.setBackground(bgSprite);
    }

    void loadResources() {
	DelayModifier dMod = new DelayModifier(5, new IEntityModifierListener() {
	    @Override
	    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		// TODO Auto-generated method stub
	    }

	    @Override
	    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
		activity.setCurrentScene(new MainMenuScene());
	    }
	});

	registerEntityModifier(dMod);
    }

}
