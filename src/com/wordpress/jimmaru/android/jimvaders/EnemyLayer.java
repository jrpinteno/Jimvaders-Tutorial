package com.wordpress.jimmaru.android.jimvaders;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.MoveXModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;

import android.util.Log;

public class EnemyLayer extends Entity {

	private LinkedList<Enemy> enemies;

	public static EnemyLayer instance;

	public int enemyCount;

	public static EnemyLayer getSharedInstance() {
		return instance;
	}

	public void purge() {
		for (Enemy e : enemies) {
			e.sprite.detachSelf();
		}
	}

	public EnemyLayer(int x) {
		enemies = new LinkedList<Enemy>();
		instance = this;
		enemyCount = x;
		restart();
	}

	public static boolean isEmpty() {
		if (instance.enemies.size() == 0)
			return true;
		return false;
	}

	public void restart() {
		Log.v("jimvaders", "restarted");
		enemies.clear();
		clearEntityModifiers();
		clearUpdateHandlers();

		for (int i = 0; i < enemyCount; i++) {
			Enemy e = EnemyPool.sharedEnemyPool().obtainPoolItem();
			float finalPosX = (i % 6) * 4 * e.sprite.getWidth();
			float finalPosY = ((int) (i / 6)) * e.sprite.getHeight() * 2;

			Random r = new Random();
			e.sprite.setPosition(r.nextInt(2) == 0 ? -e.sprite.getWidth() * 3
					: MainActivity.CAMERA_WIDTH + e.sprite.getWidth() * 3, (r
					.nextInt(5) + 1)
					* e.sprite.getHeight());
			e.sprite.setVisible(true);

			attachChild(e.sprite);
			e.sprite.registerEntityModifier(new MoveModifier(2,
					e.sprite.getX(), finalPosX, e.sprite.getY(), finalPosY));

			enemies.add(e);

		}

		setVisible(true);
		setPosition(50, 30);

		MoveXModifier movRight = new MoveXModifier(1, 50, 120);
		MoveXModifier movLeft = new MoveXModifier(1, 120, 50);
		MoveYModifier moveDown = new MoveYModifier(1, 30, 100);
		MoveYModifier moveUp = new MoveYModifier(1, 100, 30);

		registerEntityModifier(new LoopEntityModifier(
				new SequenceEntityModifier(movRight, moveDown, movLeft, moveUp)));

	}

	public static void purgeAndRestart() {
		instance.purge();
		instance.restart();
	}

	public static Iterator<Enemy> getIterator() {
		return instance.enemies.iterator();
	}
}
