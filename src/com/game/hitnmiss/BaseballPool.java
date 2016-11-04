//source: https://c0deattack.wordpress.com/2011/01/06/andengine-using-the-object-pool/
package com.game.hitnmiss;

import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;

public class BaseballPool extends GenericPool<BaseBall> {

    public static BaseballPool instance;

    public static BaseballPool sharedBulletPool() {
	if (instance == null)
	    instance = new BaseballPool();
	return instance;

    }

    private BaseballPool() {
	super();
    }

    @Override
    protected BaseBall onAllocatePoolItem() {
	return new BaseBall();
    }

    protected void onHandleRecycleItem(final BaseBall b) {
	b.baseballSprite.clearEntityModifiers();
	b.baseballSprite.clearUpdateHandlers();
	b.baseballSprite.setVisible(false);
	b.baseballSprite.detachSelf();
	Log.v("HitHard", "BaseballPool onHandleRecycleItem()");
    }
}