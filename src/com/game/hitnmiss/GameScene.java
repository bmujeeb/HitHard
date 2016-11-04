package com.game.hitnmiss;

import java.util.Iterator;
import java.util.LinkedList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.util.Log;

public class GameScene extends Scene {
    public Cannon _cannon;
    public Club _club;
    public Camera _camera;
    public LinkedList<BaseBall> _baseballList;
    public int _bulletCount;
    public int _missCount;

    private BitmapTextureAtlas _backgroundTexture;
    private TextureRegion _bgTextureRegion;
    private SpriteBackground _bgSprite;

    private BaseActivity _activity;
    private Text _points;
    private Text _attempts;

    private int levelPoints;
    private int totalPoints;
    private int shotAttempts;
    private int totalAttempts;

    public GameScene() {

	_activity = BaseActivity.getSharedInstance();
	attachChild(new TargetLayer(GameLevels.getInstance()));

	_camera = BaseActivity.getSharedInstance().camera;
	_cannon = Cannon.getSharedInstance();
	_cannon.cannonSprite.detachSelf();

	_club = Club.getSharedInstance();
	_club.clubSprite.detachSelf();

	_baseballList = new LinkedList<BaseBall>();

	attachChild(_cannon.cannonSprite);
	attachChild(_club.clubSprite);

	loadBackground();

	_cannon.cannonSprite.setVisible(true);
	_club.clubSprite.setVisible(true);

	BaseActivity.getSharedInstance().setCurrentScene(this);

	// register both to make it work.
	registerTouchArea(_cannon.cannonSprite);
	registerTouchArea(_club.clubSprite);
	setTouchAreaBindingOnActionMoveEnabled(true);

	resetValues();

	loadScoreCard();
    }

    private void loadBackground() {
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	_backgroundTexture = new BitmapTextureAtlas(new TextureManager(), 1200, 1200, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	_bgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(_backgroundTexture, BaseActivity.getSharedInstance(),
		"background.png", 0, 0);
	_bgSprite = new SpriteBackground(new Sprite(0, 0, _bgTextureRegion, new VertexBufferObjectManager()));
	BaseActivity.getSharedInstance().getTextureManager().loadTexture(_backgroundTexture);
	this.setBackground(_bgSprite);
    }

    private void loadScoreCard() {
	_points = new Text(0, 0, _activity.scoreCardFonts, _activity.getString(R.string.points) + ": " + "0" + "(" + "0" + ")",
		_activity.getVertexBufferObjectManager());
	_attempts = new Text(0, 0, _activity.scoreCardFonts, _activity.getString(R.string.attempts) + ": " + "5" + "/" + "10",
		_activity.getVertexBufferObjectManager());
	_points.setPosition(5, 5);
	_attempts.setPosition(5, _points.getHeight() + 10);
	attachChild(_points);
	attachChild(_attempts);
    }

    // method to reset values and restart the game
    public void resetValues() {
	_missCount = 0;
	_bulletCount = 0;
	TargetLayer.purgeAndRestart();
	clearChildScene();
	registerUpdateHandler(new GameLoopUpdateHandler());
    }

    public void detach() {
	Log.v("Hit It", "GameScene onDetached()");
	clearUpdateHandlers();
	for (BaseBall b : _baseballList) {
	    BaseballPool.sharedBulletPool().recyclePoolItem(b);
	}
	_baseballList.clear();
	detachChildren();
	Cannon.instance = null;
	EnemyPool.instance = null;
	BaseballPool.instance = null;

    }

    public void cleaner() {
	synchronized (this) {
	    // If no enemy is available, show the result view.
	    if (TargetLayer.endGame()) {
		Log.v("Hit It", "GameScene Cleaner() cleared");
		setChildScene(new ResultScene(_camera));
		clearUpdateHandlers();
	    }
	    Iterator<Target> eIt = TargetLayer.getIterator();
	    while (eIt.hasNext()) {
		Target e = eIt.next();
		Iterator<BaseBall> it = _baseballList.iterator();
		while (it.hasNext()) {
		    BaseBall b = it.next();
		    if (b.baseballSprite.getY() <= -b.baseballSprite.getHeight()) {
			BaseballPool.sharedBulletPool().recyclePoolItem(b);
			it.remove();
			_missCount++;
			continue;
		    }

		    if (b.baseballSprite.collidesWith(e.targetSprite)) {
			Log.d("Hit It: ", "HIT");
			if (!e.gotHit()) {
			    createExplosion(e.targetSprite.getX(), e.targetSprite.getY(), e.targetSprite.getParent(),
				    BaseActivity.getSharedInstance());
			    EnemyPool.sharedEnemyPool().recyclePoolItem(e);
			    eIt.remove();
			}
			BaseballPool.sharedBulletPool().recyclePoolItem(b);
			it.remove();
			_club.resetClub();
			break;
		    }
		    // TODO: Reset the bat after collision
		    if (b.baseballSprite.collidesWith(_club.clubSprite)) {
			// Necessary to unregister old entity modifiers
			b.baseballSprite.unregisterEntityModifier(_cannon.mod);
			// Get the ball position at the time of impact.
			float startX = b.baseballSprite.getX();
			float startY = b.baseballSprite.getY();
			// Get the impact point and
			float rotation = _club.clubSprite.getRotation();
			rotation = Math.abs(rotation);
			Log.d("Bat Rotation: ", Float.toString(rotation));
			float deflection = 0.0f;
			// Scaling the vector
			float scale = 5;
			float defAngle = 0.0f;
			if (rotation > 90.0f) {
			    scale *= -1;
			    defAngle = ((2 * rotation) - 180) % 360;
			    deflection = (float) Math.tan(-defAngle * 0.70 * 0.0174532925f);
			    Log.d("DefAngle > 90.0f: ", Float.toString(defAngle));
			    Log.d("Rotation > 90.0f: ", Float.toString(deflection));
			} else {
			    defAngle = (180 - (2 * rotation)) % 360;
			    deflection = (float) Math.tan(defAngle * 0.70 * 0.0174532925f);
			    Log.d("DefAngle < 90.0f: ", Float.toString(defAngle));
			    Log.d("Rotation < 90.0f: ", Float.toString(deflection));
			}
			float endY = startY * scale;
			float endX = (endY - startY + (startX * deflection)) / deflection;
			b.baseballSprite.registerEntityModifier(new MoveModifier(3.5f, startX, endX, startY, endY));

		    }
		}
	    }
	}
    }

    /**
     * Fires upon the enemy is dead.
     * 
     * @param posX
     * @param posY
     * @param target
     * @param activity
     */
    private void createExplosion(final float posX, final float posY, final IEntity target, final SimpleBaseGameActivity activity) {

	int mNumPart = 15;
	int mTimePart = 2;

	PointParticleEmitter particleEmitter = new PointParticleEmitter(posX, posY);
	IEntityFactory<Rectangle> recFact = new IEntityFactory<Rectangle>() {

	    @Override
	    public Rectangle create(float pX, float pY) {
		Rectangle rect = new Rectangle(posX, posY, 10, 10, activity.getVertexBufferObjectManager());
		rect.setColor(Color.GREEN);
		return rect;
	    }

	};
	final ParticleSystem<Rectangle> particleSystem = new ParticleSystem<Rectangle>(recFact, particleEmitter, 500, 500, mNumPart);

	particleSystem.addParticleInitializer(new VelocityParticleInitializer<Rectangle>(-50, 50, -50, 50));

	particleSystem.addParticleModifier(new AlphaParticleModifier<Rectangle>(0, 0.6f * mTimePart, 1, 0));
	particleSystem.addParticleModifier(new RotationParticleModifier<Rectangle>(0, mTimePart, 0, 360));

	target.attachChild(particleSystem);
	target.registerUpdateHandler(new TimerHandler(mTimePart, new ITimerCallback() {
	    @Override
	    public void onTimePassed(final TimerHandler pTimerHandler) {
		particleSystem.detachSelf();
		target.sortChildren();
		target.unregisterUpdateHandler(pTimerHandler);
	    }
	}));
    }

}
