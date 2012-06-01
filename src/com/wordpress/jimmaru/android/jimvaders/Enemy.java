package com.wordpress.jimmaru.android.jimvaders;

import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.Shape;

public class Enemy {
	public Shape sprite;
	public int hp;
	protected final int MAX_HEALTH = 2;

	public Enemy() {
		// the sprite is shape now but can be changed to a real sprite at any
		// time without modifying the rest of the code
		sprite = new Rectangle(0, 0, 30, 30);
		sprite.setColor(0.09904f, 0.8574f, 0.1786f);
		init();
	}

	// method for initializing the Enemy object , used by the constructor and
	// the EnemyPool class
	public void init() {
		hp = MAX_HEALTH;
		sprite.registerEntityModifier(new LoopEntityModifier(
				new RotationModifier(5, 0, 360)));
	}

	public void clean() {
		sprite.clearEntityModifiers();
		sprite.clearUpdateHandlers();
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
