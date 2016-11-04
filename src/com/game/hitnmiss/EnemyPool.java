//source: https://c0deattack.wordpress.com/2011/01/06/andengine-using-the-object-pool/
package com.game.hitnmiss;

import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;

public class EnemyPool extends GenericPool<Target> {

	public static EnemyPool instance;

	public static EnemyPool sharedEnemyPool() {

		if (instance == null)
			instance = new EnemyPool();
		return instance;

	}

	private EnemyPool() {
		super();
	}

	@Override
	protected Target onAllocatePoolItem() {
		return new Target();
	}

	@Override
	protected void onHandleObtainItem(Target pItem) {
		pItem.init();
	}

	protected void onHandleRecycleItem(final Target e) {
		e.targetSprite.setVisible(false);
		e.targetSprite.detachSelf();
		e.clean();
		Log.v("Jimvaders", "EnemyPool onHandleRecycleItem()");
	}
}