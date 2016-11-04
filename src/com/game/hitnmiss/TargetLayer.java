package com.game.hitnmiss;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;

import android.util.Log;

public class TargetLayer extends Entity {

    private LinkedList<Target> _enemies;
    public static TargetLayer _instance;
    public int _enemycount;
    private Club _club;
    private Camera _camera;
    private static GameLevels _gamelevels;

    public static TargetLayer getSharedInstance() {
	return _instance;
    }

    /**
     * Check if the game has ended.
     * 
     * @return
     */
    public static boolean endGame() {
	if (_gamelevels.getCurrentLevel().equalsIgnoreCase(GameLevels.LEVEL_NAMES.LEVEL_FIVE.toString())) {
	    return true;
	}
	return false;
    }

    /**
     * 
     * @return
     */
    public static Iterator<Target> getIterator() {
	return _instance._enemies.iterator();
    }

    /**
     * 
     */
    public void purge() {
	detachChildren();
	for (Target e : _enemies) {
	    EnemyPool.sharedEnemyPool().recyclePoolItem(e);
	}
	_enemies.clear();
    }

    /**
     * 
     * @param gameLevels
     */
    public TargetLayer(GameLevels gameLevels) {
	_enemies = new LinkedList<Target>();
	_instance = this;
	_club = Club.getSharedInstance();
	_camera = BaseActivity.getSharedInstance().camera;
	_gamelevels = gameLevels;
    }

    /**
     * 
     */
    public void restart() {
	Log.v("Hit it", "EnemyLayer restarted");
	_enemies.clear();
	clearEntityModifiers();
	clearUpdateHandlers();

	Target e = EnemyPool.sharedEnemyPool().obtainPoolItem();
	float[] cor = getTargetPosition();
	if (cor[0] + e.targetSprite.getWidth() > _camera.getWidth()) {
	    cor[0] -= e.targetSprite.getWidth();
	}
	if (cor[1] + e.targetSprite.getHeight() > _camera.getHeight()) {
	    cor[1] -= e.targetSprite.getHeight();
	}
	e.targetSprite.setPosition(cor[0], cor[1]);

	// TODO: Move it in such a way that, we make the next target visible if
	// previous is destroyed.
	e.targetSprite.setVisible(true);

	attachChild(e.targetSprite);
	_enemies.add(e);

	setVisible(true);
	setPosition(0, 0);

    }

    /**
     * 
     */
    public static void purgeAndRestart() {
	_instance.purge();
	_instance.restart();
    }

    @Override
    public void onDetached() {
	purge();
	clearUpdateHandlers();
	super.onDetached();
    }

    /**
     * Get target position.
     * 
     * @return x & y position in float of target
     */
    private float[] getTargetPosition() {
	float[] cor = new float[2];

	Random rX = new Random();
	Random rY = new Random();

	while (true) {
	    cor[0] = (float) rX.nextInt() % _camera.getWidth();
	    cor[1] = (float) rY.nextInt() % _camera.getHeight();
	    Log.d("Initial X: " + Float.toString(cor[0]), "Initial Y: " + Float.toString(cor[1]));
	    if (cor[0] < 0.0f || cor[1] < 0.0f) {
		continue;
	    } else if (cor[0] < _club.clubSprite.getWidth() + 5) {
		continue;
	    } else if (cor[1] < (_camera.getHeight() / 2 + _club.clubSprite.getWidth() - 60)
		    && cor[1] > (_camera.getHeight() / 2 - _club.clubSprite.getWidth() - 60)) {
		continue;
	    } else {
		break;
	    }
	}
	Log.d("Target X: " + Float.toString(cor[0]), "Target Y: " + Float.toString(cor[1]));
	return cor;
    }
}