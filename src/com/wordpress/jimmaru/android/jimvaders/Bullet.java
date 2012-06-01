package com.wordpress.jimmaru.android.jimvaders;

import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.shape.Shape;

public class Bullet {
	public Shape sprite;

	public Bullet() {
		// the sprite is shape now but can be changed to a real sprite at any
		// time without modifying the rest of the code
		sprite = new Rectangle(0, 0, 10, 10);
		sprite.setColor(0.09904f, 0f, 0.1786f);
	}
}
