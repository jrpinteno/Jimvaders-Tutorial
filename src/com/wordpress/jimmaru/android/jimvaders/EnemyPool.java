//source: https://c0deattack.wordpress.com/2011/01/06/andengine-using-the-object-pool/
package com.wordpress.jimmaru.android.jimvaders;

import org.anddev.andengine.util.pool.GenericPool;

public class EnemyPool extends GenericPool<Enemy> {

	public static EnemyPool instance;

	public static EnemyPool sharedEnemyPool() {

		if (instance == null)
			instance = new EnemyPool();
		return instance;

	}

	public EnemyPool() {
		super();
	}

	/** Called when a projectile is required but there isn't one in the pool */
	@Override
	protected Enemy onAllocatePoolItem() {
		return new Enemy();
	}

	@Override
	protected void onHandleObtainItem(Enemy pItem) {
		pItem.init();
	}

	/** Called when a projectile is sent to the pool */
	protected void onHandleRecycleItem(final Enemy e) {
		e.sprite.setVisible(false);
		e.sprite.detachSelf();
		e.clean();
	}
}