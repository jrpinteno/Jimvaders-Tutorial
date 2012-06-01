//source: https://c0deattack.wordpress.com/2011/01/06/andengine-using-the-object-pool/
package com.wordpress.jimmaru.android.jimvaders;

import org.anddev.andengine.util.pool.GenericPool;

public class BulletPool extends GenericPool<Bullet> {

	public static BulletPool instance;

	public static BulletPool sharedBulletPool() {

		if (instance == null)
			instance = new BulletPool();
		return instance;

	}

	public BulletPool() {
		super();
	}

	/** Called when a projectile is required but there isn't one in the pool */
	@Override
	protected Bullet onAllocatePoolItem() {
		return new Bullet();
	}

	/** Called when a projectile is sent to the pool */
	protected void onHandleRecycleItem(final Bullet b) {
		b.sprite.clearEntityModifiers();
		b.sprite.clearUpdateHandlers();
		b.sprite.setVisible(false);
		b.sprite.detachSelf();
	}
}